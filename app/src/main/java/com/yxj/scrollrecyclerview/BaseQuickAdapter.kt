package com.yxj.scrollrecyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by yxj (yanxujun@dxy.cn)
 * Date: 2019-07-29 17:46
 */

class BaseQuickAdapter(data:MutableList<String>) : RecyclerView.Adapter<BaseQuickAdapter.VH>() {

    var data = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.tv.text = data[position]
    }

    class VH(view: View):RecyclerView.ViewHolder(view){

        lateinit var tv:TextView

        init {
            tv = view.findViewById<TextView>(R.id.tv)
        }
    }

}