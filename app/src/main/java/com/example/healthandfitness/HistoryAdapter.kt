package com.example.healthandfitness

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_item_look.view.*

class HistoryAdapter(val context: Context,val items:ArrayList<String>)
    : RecyclerView.Adapter<HistoryAdapter.ViewHolder>()
{
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val llItem = view.linearLayoutItem
        val tvPosition = view.textViewPosition
        val tvItem = view.textViewDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.history_item_look,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = items[position]
        holder.tvPosition.text = (position+1).toString()
        holder.tvItem.text = date
    }
}