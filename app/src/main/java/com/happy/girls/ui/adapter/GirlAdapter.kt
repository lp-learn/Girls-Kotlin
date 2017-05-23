package com.happy.girls.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.happy.girls.R
import com.happy.girls.bean.Girl
import com.happy.girls.displayUrl
import kotlinx.android.synthetic.main.item_girl.view.*

/**
 * Created by ChangQin on 2017/5/22 0022.
 */
class GirlAdapter(var data: List<Girl> = ArrayList(), var itemClick: (View, Int) -> Unit)
    : RecyclerView.Adapter<GirlAdapter.GirlViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): GirlViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_girl, parent, false)
        return GirlViewHolder(view)
    }

    override fun onBindViewHolder(holder: GirlViewHolder, position: Int) {
        bindView(holder.itemView, position)
    }

    private fun bindView(itemView: View, position: Int) {
        val girl = data[position]
        val imageView = itemView.img_girl
        imageView.displayUrl(girl.url)
        imageView.setOnClickListener { itemClick(itemView, position) }
    }

    override fun getItemCount(): Int = data.size

    class GirlViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){}
}