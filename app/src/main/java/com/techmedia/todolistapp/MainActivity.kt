package com.techmedia.todolistapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            // we used the postDelayed(Runnable, time) method
            // to send a message with a delayed time.
            @Suppress("DEPRECATION")
            Handler().postDelayed({
                Intent(this, TodoListActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }, 2000) // 3000 is the delayed time in milliseconds.


        }
    }



