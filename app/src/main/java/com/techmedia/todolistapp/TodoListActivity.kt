package com.techmedia.todolistapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.techmedia.todolistapp.adapter.TodoAdapter
import com.techmedia.todolistapp.databinding.ActivityTodoListBinding

class TodoListActivity : AppCompatActivity() {
    lateinit var binding: ActivityTodoListBinding
    /**
     * Create a variable with a type of TodoAdapter that i created for the recyclerview
     */
    private lateinit var todoAdapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoListBinding.inflate(layoutInflater)
        setContentView(binding.root)



        /**
         * Assign the todoAdapter to the todoRecyclerview
         */
        binding.todoRecyclerView.adapter = todoAdapter

        /**
         * Add a click event on the button to navigate to the next activity.
         */
        binding.fabAdd.setOnClickListener {
            Intent(this, AddTodoActivity::class.java).also { startActivity(it) }
        }
        /**
         * For the
         */
        setSupportActionBar(binding.toolbar)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        /**
         *   Inflate the menu; this adds items to the action bar if it is present.
         */
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


}

