package com.example.iat366individualassignment

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.*
import android.widget.*
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
        var input  = findViewById<TextInputEditText>(R.id.enterText)
        var myScrollView = findViewById<ScrollView>(R.id.myScrollView)

        deleteItem = false


        //RecyclerView Adapter
        myRv.layoutManager = LinearLayoutManager (this)
        myRv.adapter = MyAdapter(checkList, this)

        //Function to add item into the arraylist
        fun addItem () {

            //If the input isn't empty then add the contents to the arraylist
            if (input.text.toString() != "") checkList.add(input.text.toString())

            //Notify the adapter
            myRv.adapter!! .notifyDataSetChanged()

            //Clear the input
            input.setText("")
        }

        //If the user hits the Enter Key, they can add the item
        input.setOnKeyListener { v, keyCode, event ->

            when {

                ((keyCode == KeyEvent.KEYCODE_ENTER) && (event.action == KeyEvent.ACTION_DOWN)) -> {

                    addItem()
                    return@setOnKeyListener true
                }
                else -> false
            }
        }

        //If the user clicks the add button, they can also add the item
        addButton.setOnClickListener {
            addItem()
        }

        //The delete button is a toggle. When you turn it on, anything you click on the list will be deleted whether it's checked or not.
        deleteButton.setOnClickListener {
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
        }

        clearList.setOnClickListener {

            //Clear everything and notify the adapter
            checkList.clear()
            myRv.adapter!! .notifyDataSetChanged()
        }
    }
}





