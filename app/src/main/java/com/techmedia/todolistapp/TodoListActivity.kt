package com.techmedia.todolistapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.techmedia.todolistapp.adapter.TodoAdapter
import com.techmedia.todolistapp.databinding.ActivityTodoListBinding
import com.techmedia.todolistapp.model.Todo

class TodoListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoListBinding

    /**
     * A variable with a type of TodoAdapter created for the recyclerview
     */
    private lateinit var todoAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoAdapter = TodoAdapter(mutableListOf())
        /**
         * Assign the todoAdapter to the todoRecyclerview
         */
        binding.todoRecyclerView.adapter = todoAdapter
        /**
         * Handles how the list will be displayed on screen
         */
        binding.todoRecyclerView.layoutManager = LinearLayoutManager(this)

        /**
         * Adding a click listener on my button
         */
        binding.fabAdd.setOnClickListener {
            /**
             * Assign the editText to a variable and convert it to a String
             * And Check to see if the todoTitle have any value
             */
            val todoTitle = binding.addTodoEditText.text.toString()

            if (todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle)
                todoAdapter.addTodoItem(todo)
                binding.addTodoEditText.text?.clear()
            }
        }


        /**
         * For the toolbar menu
         */
        setSupportActionBar(binding.toolbar)
    }

    /**
     *   Inflate the menu; this adds items to the action bar if it is present.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}

