package com.example.healthandfitness

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_item_look.view.*

class RecyclerViewAdapter(val items:ArrayList<ExerciseModel>,
                          val context:Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>()
{

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val tvItem = view.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.single_item_look,parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model:ExerciseModel = items[position]
        holder.tvItem.text = model.id.toString()
        if(model.isSelected)
        {
            holder.tvItem.background =
                ContextCompat.getDrawable(context,R.drawable.selected_background)
        }
        else if (model.isCompleted)
        {
            holder.tvItem.background =
                ContextCompat.getDrawable(context,R.drawable.completed_background)
        }
        else
            holder.tvItem.background =
                ContextCompat.getDrawable(context,R.drawable.single_item_background)
    }


}