package com.techmedia.todolistapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.techmedia.todolistapp.adapter.TodoAdapter
import com.techmedia.todolistapp.databinding.ActivityTodoListBinding

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
         * For the toolbar menu
         */
        setSupportActionBar(binding.toolbar)

        /**
         * Add a click event on the button to navigate to the next activity.
         */
        binding.fabAdd.setOnClickListener {
            Intent(this, AddTodoActivity::class.java).also {
                startActivity(it)
            }
        }

        val display = intent.getStringExtra("todo_KEY")
    }

    /**
     *   Inflate the menu; this adds items to the action bar if it is present.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


}

