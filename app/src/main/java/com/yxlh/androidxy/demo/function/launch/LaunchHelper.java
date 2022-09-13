package com.yxlh.androidxy.demo.function.launch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zwl
 */
public class LaunchHelper {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Context context;
        private Class<?> to;
        private LaunchCallback callback;
        private Intent intent;
        private Map<String, Object> params;
        private Bundle bundle;

        /**
         * 当前Contenxt
         *
         * @param ctx
         * @return Builder
         */
        public Builder with(Context ctx) {
            this.context = ctx;
            return this;
        }

        /**
         * 需要跳转到的界面Class
         *
         * @param to
         * @return Builder
         */
        public Builder to(Class<?> to) {
            this.to = to;
            return this;
        }


        /**
         * 添加参数
         *
         * @param params Map添加
         * @return Builder
         */
        public Builder params(Map<String, Object> params) {
            if (this.params == null) {
                this.params = new HashMap<>();
            }
            if (params != null && !params.isEmpty()) {
                this.params.putAll(params);
            }
            return this;
        }

        /**
         * 添加参数
         *
         * @param key
         * @param value
         * @return Builder
         */
        public Builder params(String key, Object value) {
            if (this.params == null) {
                this.params = new HashMap<>();
            }
            this.params.put(key, value);
            return this;
        }


        /**
         * 添加参数
         *
         * @param bundle
         * @return Builder
         */
        public Builder params(Bundle bundle) {
            this.bundle = bundle;
            return this;
        }

        /**
         * 自定义Intent
         *
         * @param intent
         * @return Builder
         */
        public Builder intent(Intent intent) {
            this.intent = intent;
            return this;
        }


        /**
         * 监听 startActivityForResult 回调
         *
         * @param callback 如果添加回调 要求必须是 Activity的context
         *                 如果不添加回调 默认正常启动 Activity
         * @return Builder
         */
        public Builder callback(LaunchCallback callback) {
            this.callback = callback;
            return this;
        }

        /**
         * 启动界面
         */
        public void start() {
            //context不能为空，为空抛异常
            if (this.context == null) {
                throw new RuntimeException("context is null");
            }
            Intent intent;
            //如果设置Intent就使用设置的Intent
            if (this.intent != null) {
                intent = this.intent;
            }
            //没有设置intent就必须设置to
            else {
                if (this.to == null) {
                    throw new RuntimeException("to is null，where are you going？");
                }
                intent = new Intent(context, to);
            }

            //intent添加bundle数据
            if (this.bundle != null) {
                intent.putExtras(this.bundle);
            }

            //intent添加参数
            if (null != params && params.size() > 0) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (key == null || value == null) continue;
                    if (value instanceof Boolean)
                        intent.putExtra(key, (Boolean) value);
                    else if (value instanceof boolean[])
                        intent.putExtra(key, (boolean[]) value);
                    else if (value instanceof Byte)
                        intent.putExtra(key, (Byte) value);
                    else if (value instanceof byte[])
                        intent.putExtra(key, (byte[]) value);
                    else if (value instanceof Character)
                        intent.putExtra(key, (Character) value);
                    else if (value instanceof Character[])
                        intent.putExtra(key, (Character[]) value);
                    else if (value instanceof Short)
                        intent.putExtra(key, (Short) value);
                    else if (value instanceof short[])
                        intent.putExtra(key, (short[]) value);
                    else if (value instanceof Integer)
                        intent.putExtra(key, (Integer) value);
                    else if (value instanceof int[])
                        intent.putExtra(key, (int[]) value);
                    else if (value instanceof Long)
                        intent.putExtra(key, (Long) value);
                    else if (value instanceof long[])
                        intent.putExtra(key, (long[]) value);
                    else if (value instanceof Float)
                        intent.putExtra(key, (Float) value);
                    else if (value instanceof float[])
                        intent.putExtra(key, (float[]) value);
                    else if (value instanceof Double)
                        intent.putExtra(key, (Double) value);
                    else if (value instanceof double[])
                        intent.putExtra(key, (double[]) value);
                    else if (value instanceof String)
                        intent.putExtra(key, (String) value);
                    else if (value instanceof String[])
                        intent.putExtra(key, (String[]) value);
                    else if (value instanceof CharSequence)
                        intent.putExtra(key, (CharSequence) value);
                    else if (value instanceof CharSequence[])
                        intent.putExtra(key, (CharSequence[]) value);
                    else if (value instanceof Parcelable)
                        intent.putExtra(key, (Parcelable) value);
                    else if (value instanceof Parcelable[])
                        intent.putExtra(key, (Parcelable[]) value);
                    else if (value instanceof Serializable)
                        intent.putExtra(key, (Serializable) value);
                    else if (value instanceof ArrayList) {
                        ArrayList arrayList = (ArrayList) value;
                        if (arrayList.size() > 0) {
                            Object obj = arrayList.get(0);
                            if (obj instanceof Integer) {
                                intent.putIntegerArrayListExtra(key, (ArrayList<Integer>) value);
                            } else if (obj instanceof String) {
                                intent.putStringArrayListExtra(key, (ArrayList<String>) value);
                            } else if (obj instanceof CharSequence) {
                                intent.putCharSequenceArrayListExtra(key, (ArrayList<CharSequence>) value);
                            } else if (obj instanceof Parcelable) {
                                intent.putParcelableArrayListExtra(key, (ArrayList<Parcelable>) value);
                            }
                        }
                    } else {
                        throw new RuntimeException("Unsupported type " + value.getClass());
                    }
                }
            }
            //是否是Activity的Context
            boolean isActivityContext = context instanceof Activity;

            //有监听返回值-必须Activity
            if (this.callback != null) {
                if (!isActivityContext) {
                    throw new RuntimeException("if you want listening callback，the context must be  activity context");
                }
                Activity activity = (Activity) context;
                LaunchResult launchResult = new LaunchResult(activity);
                launchResult.startForResult(intent, callback);
            }
            //没有监听返回值-直接跳转
            else {
                if (!isActivityContext) {
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(intent);
            }
        }
    }
}
