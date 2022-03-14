package com.example.iat366individualassignment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter (private val array: ArrayList<String>, private val context: Context): RecyclerView.Adapter<MyAdapter.MyViewHolder> () {
    class MyViewHolder (val checkedTextView: CheckedTextView) :
        RecyclerView.ViewHolder (checkedTextView)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_list_content, parent, false) as CheckedTextView

        return MyViewHolder (inflater)

    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val adapter: MyAdapter = this

        //Makes the arraylist item appear in the holder in the same order as the arraylist
        (holder.itemView as CheckedTextView).text = array[position]


        //The item is initially unchecked
        holder.itemView.isChecked = false
        holder.itemView.setCheckMarkDrawable(android.R.drawable.
        checkbox_off_background)

        holder.itemView.setOnClickListener{

            //When the item's clicked, it toggles the checkmark
            holder.itemView.isChecked = !holder.itemView.isChecked
            holder.itemView.setCheckMarkDrawable(if(holder.itemView.isChecked)
                android.R.drawable.checkbox_on_background
            else android.R.drawable.checkbox_off_background)

            //if delete: on and the arraylist is not empty, remove the item that's been clicked
            if (deleteItem && array != null) {
                array.removeAt(position)
                adapter!! .notifyItemRemoved(position)

            }


        }



    }

    override fun getItemCount() = array.size
}