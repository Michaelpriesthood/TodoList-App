package com.techmedia.todolistapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.techmedia.todolistapp.adapter.TodoAdapter
import com.techmedia.todolistapp.databinding.ActivityTodoListBinding
import com.techmedia.todolistapp.databinding.DialogUpdateBinding
import com.techmedia.todolistapp.model.DatabaseHandler
import com.techmedia.todolistapp.model.TodoModel


class TodoListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

//         For the toolbar menu
        setSupportActionBar(binding.toolbar)

//         Adding a click listener on my button
        binding.fabAdd.setOnClickListener { addTodo() }

        setupListOfDataIntoRecyclerView()


    }

    /**
     * Function is used to show the list of inserted data.
     */
    private fun setupListOfDataIntoRecyclerView() {

        if (getItemsList().size > 0) {

            binding.todoRecyclerView.visibility = View.VISIBLE
            binding.noTaskAvailable.visibility = View.GONE

            // Set the LayoutManager that this RecyclerView will use.
            binding.todoRecyclerView.layoutManager = LinearLayoutManager(this)
            // Adapter class is initialized and list is passed in the param.
            val todoAdapter = TodoAdapter(this, getItemsList())
            // adapter instance is set to the recyclerview to inflate the items.
            binding.todoRecyclerView.adapter = todoAdapter

        } else {
            binding.todoRecyclerView.visibility = View.GONE
            binding.noTaskAvailable.visibility = View.VISIBLE
        }
    }

    //Method for saving Todo in database
    private fun addTodo() {
        //  Assign the editText to a variable and convert it to a String
        val todoEditText = binding.addTodoEditText.text.toString()
        val databaseHandler = DatabaseHandler(this)

//        Check to see if the todoTitle have any value
        if (todoEditText.isNotEmpty()) {
            val status = databaseHandler.addTodoItem(TodoModel(0, todoEditText))
            if (status > -1) {
                Toast.makeText(applicationContext, "Task saved!!!", Toast.LENGTH_LONG).show()
                binding.addTodoEditText.text?.clear()
            }
        } else {
            binding.addTodoEditText.error = "Your field cannot be blank"
            return
        }
        //             Clear the Focus
        binding.addTodoEditText.clearFocus()
    }

    /**
     * Function is used to get the Items List from the database table.
     */
    private fun getItemsList(): ArrayList<TodoModel> {
        //creating the instance of DatabaseHandler class
        val databaseHandler = DatabaseHandler(this)
        //calling the viewTodoItem method of DatabaseHandler class to read the records

        return databaseHandler.viewTodoItem()
    }


    /**
     * Method is used to show the custom update dialog.
     */
    fun updateTodoDialog(todoModel: TodoModel) {
        val updateDialog = AlertDialog.Builder(this, R.style.ThemeDialog)
        val inflateLayout = LayoutInflater.from(this)
        /*Set the screen content from a layout resource.
 The resource will be inflated, adding all top-level views to the screen.*/
        val dialogBinding = DialogUpdateBinding.inflate(inflateLayout)
        updateDialog.setView(dialogBinding.root)
        updateDialog.setCancelable(false)
        dialogBinding.updateTodoEditText.setText(todoModel.title)

        dialogBinding.updateButton.setOnClickListener {
            val updateTask = dialogBinding.updateTodoEditText.text.toString()
            val databaseHandler = DatabaseHandler(this)
            if (updateTask.isNotEmpty()) {
                val status =
                    databaseHandler.updateTodo(TodoModel(todoModel.id, updateTask))
                if (status > -1) {
                    Toast.makeText(applicationContext, "Task Updated.", Toast.LENGTH_LONG).show()
                    setupListOfDataIntoRecyclerView()

                }


            } else {
                dialogBinding.updateTodoEditText.error = "Your field cannot be blank"
            }
        }

        dialogBinding.cancelButton.setOnClickListener {
            Toast.makeText(applicationContext, "Task Update dismissed.", Toast.LENGTH_LONG).show()
        }
        //Start the dialog and display it on screen.
        updateDialog.show()
    }

    /**
     * Method is used to show the delete alert dialog.
     */
    fun deleteTodoDialog(todoModel: TodoModel) {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Confirm Delete")
        //set message for alert dialog
        builder.setMessage("Are you sure you wants to delete ${todoModel.title}.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, _ ->

            //creating the instance of DatabaseHandler class
            val databaseHandler = DatabaseHandler(this)
            //calling the deleteTodo method of DatabaseHandler class to delete todo
            val status = databaseHandler.deleteTodo(TodoModel(todoModel.id, ""))
            if (status > -1) {
                Toast.makeText(
                    applicationContext,
                    "Task deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()

                setupListOfDataIntoRecyclerView()
            }

            dialogInterface.dismiss() // Dialog will be dismissed
        }
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()  // show the dialog to UI
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}






