package com.yxlh.androidxy.demo.ui.card

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yxlh.androidxy.R

class CardAdapter(private val cardList: List<CardData>, private var recyclerView: RecyclerView) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    private var selectedPosition = -1

    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardItem: ViewGroup = view.findViewById(R.id.item_card)
        val cardTitle: TextView = view.findViewById(R.id.cardTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val cardData = cardList[holder.adapterPosition]
        holder.cardTitle.text = cardData.title
        holder.cardItem.setBackgroundResource(cardData.iconResId)
        holder.itemView.animateScale(position == selectedPosition)
        holder.itemView.setOnClickListener {
            if (position == selectedPosition) {
                return@setOnClickListener
            }
            val newViewHolder = recyclerView.findViewHolderForAdapterPosition(position)
            val oldViewHolder = recyclerView.findViewHolderForAdapterPosition(selectedPosition)
            newViewHolder?.itemView?.animateScale(true)
            oldViewHolder?.itemView?.animateScale(false)
            selectedPosition = position
        }
    }


    override fun getItemCount(): Int {
        return cardList.size
    }


    private fun View?.animateScale(isSelect: Boolean) {
        val scale = if (isSelect) 1.1f else 1.0f
        val scaleX = ObjectAnimator.ofFloat(this, View.SCALE_X, scale)
        val scaleY = ObjectAnimator.ofFloat(this, View.SCALE_Y, scale)
        AnimatorSet().apply {
            playTogether(scaleX, scaleY)
            duration = 300
            start()
        }
    }
}
