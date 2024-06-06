package com.yxlh.androidxy.demo.ui.textview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;

import com.yxlh.androidxy.R;


/**
 * 可展开的TextView
 */
@SuppressLint("AppCompatCustomView")
public class ExpandableTextNewView extends TextView {
    private static final String TAG = "Expand";
    private static final String ELLIPSE = "... ";
    private static final String SHRINK_TXT = " 收起";
    private static final String SPACE = " ";
    private static final int EXPAND = 0;
    private static final int SHRINK = 1;
    private String expandTxt = "展开";
    private String shrinkTxt = SHRINK_TXT;
    private String shrinkExtra = ELLIPSE;
    private int[] CLICK_TXT_COLOR = {0xff007AFF, 0xff007AFF};
    private int mWidth;
    private CharSequence mOriginText;
    private int mStatus = SHRINK;
    private int mMaxLines;
    private BufferType mBufferType;
    private Layout mTextLayout;
    private boolean isSpanClick;//span click是否被执行
    private float spaceWidth;
    private float shrinkExtraWidth;
    private int expandTextWidth;
    private boolean needExpanedClick = true;
    private boolean enableExpand = true;
    private float mLineSpacingMultiplier = 1f;//行倍数
    private float mLineSpacingExtra = 0f;//行间距
    private boolean isSpanRight = true; //span 是否显示到最右
    private boolean isLinkClickable = true;
    private SvOnExpandStateChangeListener mOnExpandStateChangeListener = null;
    private Drawable expandIcon;
    private Drawable shrinkIcon;
    private int STYPE_TEXT = 1;
    private int STYPE_IMAGE = 2;
    private int expandStyle = STYPE_TEXT; // 1文字  2 图标

    public ExpandableTextNewView(Context context) {
        this(context, null);
    }

    public ExpandableTextNewView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // 为整个控件设置点击事件
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断当前状态
                if (mStatus == EXPAND) {
                    // 如果是展开状态，则执行收缩操作
                    closeText();
                    if (mOnExpandStateChangeListener != null) {
                        mOnExpandStateChangeListener.onShrink(ExpandableTextNewView.this);
                    }
                } else {
                    // 如果是收缩状态，则执行展开操作
                    expandText();
                    if (mOnExpandStateChangeListener != null) {
                        mOnExpandStateChangeListener.onExpand(ExpandableTextNewView.this);
                    }
                }
            }
        });
    }


    public ExpandableTextNewView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public boolean isSpanRight() {
        return isSpanRight;
    }

    public void setSpanRight(boolean spanRight) {
        isSpanRight = spanRight;
    }

    private void init(Context context, AttributeSet attrs) {
        setAutoLinkMask(0);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SvExpandableTextView);
        String text = ta.getString(R.styleable.SvExpandableTextView_sv_expand_text);
        if (!TextUtils.isEmpty(text)) {
            expandTxt = text;
        }
        String shrinkText = ta.getString(R.styleable.SvExpandableTextView_sv_shrink_text);
        if (!TextUtils.isEmpty(shrinkText)) {
            shrinkTxt = shrinkText;
        }
        boolean isShowShrink = ta.getBoolean(R.styleable.SvExpandableTextView_sv_expand_show_shrink, true);
        if (!isShowShrink) {
            shrinkTxt = "";
        }
        expandIcon = ta.getDrawable(R.styleable.SvExpandableTextView_sv_expand_icon);
        if (expandIcon == null) {
            expandIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_down);
        }
        shrinkIcon = ta.getDrawable(R.styleable.SvExpandableTextView_sv_shrink_icon);
        if (isShowShrink) {
            if (shrinkIcon == null) {
                shrinkIcon = ContextCompat.getDrawable(context, R.drawable.ic_arrow_up);
            }
        } else {
            shrinkIcon = null;
        }

        expandStyle = ta.getInt(R.styleable.SvExpandableTextView_sv_expand_style, STYPE_TEXT);
        int shrinkTextColor = ta.getColor(R.styleable.SvExpandableTextView_sv_expand_color_shrink, 0);
        if (shrinkTextColor != 0) {
            CLICK_TXT_COLOR[0] = shrinkTextColor;
        }
        int expandTextColor = ta.getColor(R.styleable.SvExpandableTextView_sv_expand_color_expand, 0);
        if (expandTextColor != 0) {
            CLICK_TXT_COLOR[1] = expandTextColor;
        }
        mLineSpacingMultiplier = ta.getFloat(R.styleable.SvExpandableTextView_sv_expand_lineSpacingMultiplier, 1);
        mLineSpacingExtra = ta.getDimensionPixelSize(R.styleable.SvExpandableTextView_sv_expand_lineSpacingExtra, 0);
        isLinkClickable = ta.getBoolean(R.styleable.SvExpandableTextView_sv_expand_link_clickable, true);
        ta.recycle();
        if (isLinkClickable) {
            setMovementMethod(MyLinkMovementMethod.getInstance());//支持ClickSpan
        }
        mOriginText = getText();
        spaceWidth = (int) (getPaint().measureText(SPACE) + 0.5f);
        expandTextWidth = expandTxt.length();
        if (expandStyle == STYPE_TEXT) {
            shrinkExtra = shrinkExtra + expandTxt;
            shrinkExtraWidth = getPaint().measureText(shrinkExtra);
        } else {
            shrinkExtraWidth = getPaint().measureText(shrinkExtra) + ExpandableTextExt.getImageSpanWidth(expandIcon);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mWidth == 0) {
            mWidth = getMeasuredWidth();
            if (enableExpand && mWidth > 0) {
                mOriginText = CommonTextViewExtKt.getNotBreakByWordText(this, mWidth);
                internalSetText();
            }
        }
        if (enableExpand && getExpandLineCount() > 2) {
            if (getMeasuredHeight() > 0 && getMeasuredWidth() > 0) {
                int height = fixHeight();
                setMeasuredDimension(getMeasuredWidth(), height);
            }
        }
    }

    public void setNeedExpanedClick(boolean needExpanedClick) {
        this.needExpanedClick = needExpanedClick;
    }

    @Override
    public void setTextColor(@ColorInt int color) {
        getPaint().setColor(color);
        invalidate();
    }

    private int fixHeight() {
        Layout layout = getTextLayout(getText());
        return Math.max(layout.getHeight(), getSuggestedMinimumHeight());
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if (!enableExpand) {
            superSetText(text, type);
            return;
        }
        if (mOriginText != null && !mOriginText.equals(text)) {
            mStatus = SHRINK;
        }
        mOriginText = text;
        mBufferType = type;
        mWidth = 0;
        if (mMaxLines > 0) {
            setMaxLines(mMaxLines);
        }
        internalSetText();
    }

    private void superSetText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    public boolean isEnableExpand() {
        return enableExpand;
    }

    public void setEnableExpand(boolean enableExpand) {
        this.enableExpand = enableExpand;
        if (!enableExpand) {
            setMaxLines(Integer.MAX_VALUE);
        }
    }

    public void expandText() {
        if (needExpanedClick && getExpandLineCount() > 2) {
            setMaxLines(Integer.MAX_VALUE);
            mStatus = EXPAND;
            internalSetText();
        }
    }

    private int getExpandLineCount() {
        Layout layout = new StaticLayout(mOriginText, getPaint(), mWidth, Layout.Alignment.ALIGN_NORMAL, mLineSpacingMultiplier, mLineSpacingExtra, false);
        return layout.getLineCount();
    }

    public void closeText() {
        setMaxLines(mMaxLines);
        mStatus = SHRINK;
        internalSetText();
    }

    /**
     * 内部调用父类的setText方法
     */
    public void internalSetText() {
        super.setText(getReplaceText(), mBufferType);
        //从收缩到展开时，可能会导致内部滚动,这里强制将ScrollY的值设置为0
        int scrollY = getScrollY();
        if (scrollY != 0) {
            scrollTo(0, 0);
        }
    }

    /**
     * 获取替换的文本
     *
     * @return
     */
    private CharSequence getReplaceText() {
        if (mOriginText == null) {
            return null;
        }
        final int maxLines = TextViewCompat.getMaxLines(this);
        if (mMaxLines <= 0) {
            mMaxLines = maxLines;
        }
        Layout layout = new StaticLayout(mOriginText, getPaint(), mWidth, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        int lineCount = layout.getLineCount();
        if (mWidth <= 0 || lineCount <= mMaxLines) {
            return mOriginText;
        }
        CharSequence charSequence = null;
        if (mStatus == EXPAND) {
            charSequence = generateExpandText(maxLines, layout);
        } else {
            charSequence = generateShrinkText(layout);
        }
        return charSequence;
    }

    /**
     * 获取收缩状态的文字
     *
     * @param layout
     * @return
     */
    @NonNull
    private CharSequence generateShrinkText(Layout layout) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(mOriginText);
        try {
            int lineStart = layout.getLineStart(mMaxLines - 1);
            int offset = layout.getLineEnd(mMaxLines - 1);

            int start = offset;
            int end = offset;
            if (getPaint().measureText(mOriginText, lineStart, start) > shrinkExtraWidth) {
                while (getPaint().measureText(mOriginText, start, end) < shrinkExtraWidth) {
                    start--;
                }
                stringBuilder.replace(start, stringBuilder.length(), shrinkExtra);
            } else {
                stringBuilder.replace(lineStart, stringBuilder.length(), shrinkExtra);
            }
//            if (isSpanRight) {
//                fixShrinkEndSpace(stringBuilder, lineStart);
//            }

            if (expandStyle == STYPE_IMAGE) {
                if (isLinkClickable) {
                    ExpandableTextExt.addImageSpan(stringBuilder, expandIcon);
                } else {
                    ExpandableTextExt.addImageSpan(stringBuilder, expandIcon, () -> {
                        if (needExpanedClick) {
                            setMaxLines(Integer.MAX_VALUE);
                            mStatus = EXPAND;
                            if (mOnExpandStateChangeListener != null) {
                                mOnExpandStateChangeListener.onExpand(ExpandableTextNewView.this);
                            }
                            internalSetText();
                        }
                    });
                }

            } else {
                if (isLinkClickable) {
                    buildClickableSpan(stringBuilder);
                } else {
                    buildForegroundSpan(stringBuilder);
                }
            }

            return stringBuilder;
        } catch (Exception e) {
            return stringBuilder;
        }
    }

    private void buildForegroundSpan(SpannableStringBuilder stringBuilder) {
        stringBuilder.setSpan(new ForegroundColorSpan(CLICK_TXT_COLOR[0]), stringBuilder.length() - expandTextWidth, stringBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private void buildClickableSpan(SpannableStringBuilder stringBuilder) {
        stringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                if (needExpanedClick) {
                    setMaxLines(Integer.MAX_VALUE);
                    mStatus = EXPAND;
                    if (mOnExpandStateChangeListener != null) {
                        mOnExpandStateChangeListener.onExpand(ExpandableTextNewView.this);
                    }
                    internalSetText();
                }
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(CLICK_TXT_COLOR[0]);
                ds.setUnderlineText(false);
            }
        }, stringBuilder.length() - expandTextWidth, stringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    /**
     * 获取展开状态的文字
     *
     * @param maxLines
     * @return
     */
    @NonNull
    private CharSequence generateExpandText(final int maxLines, Layout layout) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(mOriginText);
        fillExpandSpace(stringBuilder, layout);
        if (expandStyle == STYPE_IMAGE) {
            ExpandableTextExt.addImageSpan(stringBuilder, shrinkIcon, () -> {
                setMaxLines(maxLines);
                mStatus = SHRINK;
                if (mOnExpandStateChangeListener != null) {
                    mOnExpandStateChangeListener.onShrink(ExpandableTextNewView.this);
                }
                internalSetText();
            });
        } else {
            stringBuilder.append(shrinkTxt);
            stringBuilder.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    setMaxLines(maxLines);
                    mStatus = SHRINK;
                    if (mOnExpandStateChangeListener != null) {
                        mOnExpandStateChangeListener.onShrink(ExpandableTextNewView.this);
                    }
                    internalSetText();
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(CLICK_TXT_COLOR[1]);
                    ds.setUnderlineText(false);
                }
            }, stringBuilder.length() - shrinkTxt.length(), stringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return stringBuilder;
    }

    @NonNull
    public CharSequence generateOutExpandText(String text) {
        Layout layout = new StaticLayout(text, getPaint(), mWidth, Layout.Alignment.ALIGN_NORMAL, 1, 0, false);
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(text);
        fillExpandSpace(stringBuilder, layout);

        stringBuilder.append(shrinkTxt);

        stringBuilder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                setMaxLines(mMaxLines);
                mStatus = SHRINK;
                if (mOnExpandStateChangeListener != null) {
                    mOnExpandStateChangeListener.onShrink(ExpandableTextNewView.this);
                }
                internalSetText();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(CLICK_TXT_COLOR[1]);
                ds.setUnderlineText(false);
            }
        }, stringBuilder.length() - shrinkTxt.length(), stringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return stringBuilder;
    }


    /**
     * 填充展开状态空间
     *
     * @param stringBuilder
     */
    private void fillExpandSpace(SpannableStringBuilder stringBuilder, Layout layout) {
        try {
            int shrinkWidth = 0;
            if (expandStyle == STYPE_IMAGE) {
                shrinkWidth = ExpandableTextExt.getImageSpanWidth(shrinkIcon);
            } else {
                shrinkWidth = (int) (getPaint().measureText(shrinkTxt) + 0.5f);
            }

            int lineCount = layout.getLineCount();
            int lastLineStart = layout.getLineStart(lineCount - 1);
            int lastLineWidth = (int) (getPaint().measureText(stringBuilder.subSequence(lastLineStart, stringBuilder.length()).toString()) + 0.5f);
            //计算最后一行是剩余空间是否大于收起，如果不够则添加一行
            if (lastLineWidth > mWidth - shrinkWidth) {
                stringBuilder.append("\n");
            }
        } catch (Exception e) {
        }
    }


    /**
     * 获取Layout对象，用于绘制
     *
     * @param charSequence
     * @return
     */
    private Layout getTextLayout(CharSequence charSequence) {
        if (mTextLayout != null) {
            CharSequence oldCharSequence = mTextLayout.getText();
            if (oldCharSequence != null && oldCharSequence.equals(charSequence)) {
                return mTextLayout;
            }
        }
        int realWidth = getWidth();
        if (realWidth > 0) {
            mWidth = realWidth;
        }
        mTextLayout = new StaticLayout(charSequence, getPaint(), mWidth, Layout.Alignment.ALIGN_NORMAL, mLineSpacingMultiplier, mLineSpacingExtra, false);
        return mTextLayout;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!enableExpand) {
            super.onDraw(canvas);
            return;
        }
        CharSequence charSequence = getText();
        if (charSequence == null || charSequence.length() == 0) {
            return;
        }
        Layout layout = getTextLayout(charSequence); //使用新创建的Layout绘制文本解决默认中文和英文排版问题
        layout.draw(canvas);

    }


    @Override
    public boolean performClick() {

        if (isSpanClick && needExpanedClick) {
            return true;
        }
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isSpanClick = false;
        if (isClickable()) {
            if (isLinkClickable) {
                setMovementMethod(MyLinkMovementMethod.getInstance());
            }
            return super.onTouchEvent(event);
        } else {
            return false;
        }
    }

    /**
     * 填充空格填满一行
     *
     * @param stringBuilder
     * @param start
     */
    private void fixShrinkEndSpace(SpannableStringBuilder stringBuilder, int start) {
        try {
            float lastLineWidth = (getPaint().measureText(stringBuilder, start, stringBuilder.length()) + 0.5f);
            while (mWidth - lastLineWidth > spaceWidth) {
                stringBuilder.insert(stringBuilder.length() - expandTextWidth, SPACE);
                lastLineWidth = (getPaint().measureText(stringBuilder, start, stringBuilder.length()));
            }
        } catch (Exception e) {
        }
    }

    public void setOnExpandStateChangeListener(SvOnExpandStateChangeListener listener) {
        mOnExpandStateChangeListener = listener;
    }

    public interface SvOnExpandStateChangeListener {
        void onExpand(ExpandableTextNewView v);

        void onShrink(ExpandableTextNewView v);
    }

    /**
     * 修改默认ClickSpan点击效果
     */
    private static class MyLinkMovementMethod extends LinkMovementMethod {
        private static MyLinkMovementMethod myLinkMovementMethod;

        public static LinkMovementMethod getInstance() {
            if (myLinkMovementMethod == null) {
                myLinkMovementMethod = new MyLinkMovementMethod();
            }
            return myLinkMovementMethod;
        }

        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            int action = event.getAction();

            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = null;
                if (widget instanceof ExpandableTextNewView) {
                    layout = ((ExpandableTextNewView) widget).mTextLayout; //使用内部创建的Layout对象处理点击，否则会出现错位
                }
                if (layout == null) {
                    layout = widget.getLayout();
                }
                int line = layout.getLineForVertical(y);
                int off = layout.getOffsetForHorizontal(line, x);

                ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

                if (link.length != 0) {
                    if (action == MotionEvent.ACTION_UP) {
                        link[0].onClick(widget);
                        buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        Selection.removeSelection(buffer);
                    } else if (action == MotionEvent.ACTION_DOWN) {
                        buffer.setSpan(new BackgroundColorSpan(Color.GRAY), buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
                    } else if (action == MotionEvent.ACTION_MOVE) {
                        buffer.setSpan(new BackgroundColorSpan(Color.TRANSPARENT), buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        Selection.removeSelection(buffer);
                    }

                    if (widget instanceof ExpandableTextNewView) {
                        ((ExpandableTextNewView) widget).isSpanClick = true;
                    }
                    return true;
                } else {
                    Selection.removeSelection(buffer);
                }
            }
            return super.onTouchEvent(widget, buffer, event);
        }

    }
}