package com.example.iat366individualassignment

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

//User can toggle a mode where they can delete things
 var deleteItem: Boolean = false



class MainActivity : AppCompatActivity() {

    var checkList = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //variables
        val addButton = findViewById<ImageButton>(R.id.addButton)
        val deleteButton = findViewById<Button>(R.id.deleteTask)
        val clearList = findViewById<Button>(R.id.clearList)
        val myRv = findViewById<RecyclerView>(R.id.myRv)
        deleteItem = false


        //RecyclerView Adapter
        myRv.layoutManager = LinearLayoutManager (this)
        myRv.adapter = MyAdapter(checkList, this)



        addButton.setOnClickListener {
            var input  = findViewById<TextInputEditText>(R.id.enterText)
            //var newText = findViewById<TextInputEditText>(R.id.enterText)

            //If the input isn't empty, then add the content to the array
            if (input.text.toString() != "") checkList.add(input.text.toString())

            //Notify the adapter
            myRv.adapter!! .notifyDataSetChanged()

            //Clear the input
             input.setText("")

        }

        //The delete button is a toggle. When you turn it on, anything you click on the list will be deleted whether it's checked or not.
        //This is in case the user wants to delete the items they didn't check anyway
        deleteButton.setOnTouchListener { view, motionEvent ->

            view.performClick()

            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    deleteItem = !deleteItem

                    //if delete: on, make the button fully colored and change the button text
                    if (deleteItem) {
                        deleteButton.setBackgroundColor(Color.argb(255, 255, 0, 0))
                        deleteButton.setText("Delete: On")
                    }
                    //if delete: off, make button lighter color and change the button text
                    if (!deleteItem) {
                        deleteButton.setBackgroundColor(Color.argb(50, 255, 0, 0))
                        deleteButton.setText("Delete: Off")
                    }


                    true
                }

                else -> true
            }


        }

        clearList.setOnClickListener {

            //Clear everything and notify the adapter
            checkList.clear()
            myRv.adapter!! .notifyDataSetChanged()
        }


    }




}





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