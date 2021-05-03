package com.techmedia.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.techmedia.todolistapp.databinding.ActivityAddTodoBinding
import com.techmedia.todolistapp.model.Todo

class AddTodoActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddTodoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /**
         * Adding a click listener on my button
         */
        binding.fabCheckBtn.setOnClickListener {
            /**
             * Assign the editText to a variable and convert it to a String
             */
            val todoTitle = binding.addTodoEditText.text.toString()

            /**
             * Start Intent.
             * Send data with Intent
             */
            var intent = Intent(this, TodoListActivity::class.java).also {
                it.putExtra(
                    "todo_KEY",
                    todoTitle
                ) // todo_KEY is the key to get string on next activity
                startActivity(it) // Start the Intent and display the next activity
            }
            /**
             * Check to see if the editText is not empty.
             * And if it is not empty assign the value of the editText into our Todo
             */


        }
    }
}