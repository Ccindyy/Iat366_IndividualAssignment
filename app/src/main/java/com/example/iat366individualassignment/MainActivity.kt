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

public var deleteItem: Boolean = false



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


        //RecyclerView Adapter
        myRv.layoutManager = LinearLayoutManager (this)
        myRv.adapter = MyAdapter(checkList, this)



        addButton.setOnClickListener {
            var input  = findViewById<TextInputEditText>(R.id.enterText).text.toString()
            var newText = findViewById<TextInputEditText>(R.id.enterText)
            //Toast.makeText(this, input, Toast.LENGTH_SHORT).show()

            if (input != "")checkList.add(input)

            myRv.adapter!! .notifyDataSetChanged()
             newText.setText("")

        }

        //The delete button is a toggle. When you turn it on, anything you click on the list will be deleted whether it's checked or not.
        //This is in case the user wants to delete the items they didn't check anyway
        deleteButton.setOnTouchListener { view, motionEvent ->

            view.performClick()

            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    deleteItem = !deleteItem
//                    checkList.clear()
//                    myRv.adapter!! .notifyDataSetChanged()

                    if (deleteItem) {
                        deleteButton.setBackgroundColor(Color.argb(255, 255, 0, 0))
                        deleteButton.setText("Delete: On")
                    }
                    if (!deleteItem) {
                        deleteButton.setBackgroundColor(Color.argb(50, 255, 0, 0))
                        deleteButton.setText("Delete: Off")
                    }
                    //Toast.makeText(this, "$deleteItem", Toast.LENGTH_SHORT).show()

                    true
                }
                MotionEvent.ACTION_UP -> {
                    //deleteItem = false
                    //Toast.makeText(this, "$deleteItem", Toast.LENGTH_SHORT).show()

                    true
                }
                else -> true
            }


        }

        clearList.setOnClickListener {
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
        (holder.itemView as CheckedTextView).text = array[position]
        var currentText = (holder.itemView).text.toString()


        holder.itemView.isChecked = false
        holder.itemView.setCheckMarkDrawable(android.R.drawable.
        checkbox_off_background)




        holder.itemView.setOnClickListener{
        //Toast.makeText(context, currentText, Toast.LENGTH_SHORT).show()
        holder.itemView.isChecked = !holder.itemView.isChecked
            holder.itemView.setCheckMarkDrawable(if(holder.itemView.isChecked)
                android.R.drawable.checkbox_on_background
                else android.R.drawable.checkbox_off_background)

            if (deleteItem && array != null) {
                Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show()
                array.removeAt(position)
                adapter!! .notifyItemRemoved(position)

            }


            }



    }



    override fun getItemCount() = array.size


}