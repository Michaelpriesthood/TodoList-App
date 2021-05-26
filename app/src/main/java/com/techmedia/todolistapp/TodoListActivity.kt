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
        binding.fabAdd.setOnClickListener {
            addTodo()
        }

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
                Toast.makeText(this, "Task saved!!!", Toast.LENGTH_LONG).show()
                binding.addTodoEditText.text?.clear()
            }
        } else {
            binding.addTodoEditText.error = "This field cannot be blank"
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
        //calling the viewTodoItem method of DatabaseHandler class to read the todos
        return databaseHandler.viewTodoItem()
    }


    /**
     * Method is used to show the custom update dialog.
     */

    fun updateTodoDialog(todoModel: TodoModel) {
        val updateDialog = AlertDialog.Builder(this)
        val layoutInflater = LayoutInflater.from(this)
        val dialogBinding = DialogUpdateBinding.inflate(layoutInflater)
        /*Set the screen content from a layout resource.
         The resource will be inflated, adding all top-level views to the screen.*/
        updateDialog.setView(dialogBinding.root)
        updateDialog.setCancelable(false)

        dialogBinding.updateTodoEditText.setText(todoModel.title)

        updateDialog.setPositiveButton("Update") { dialogInterface, _ ->
            val updateTask = dialogBinding.updateTodoEditText.text.toString()
            val databaseHandler = DatabaseHandler(this)
            if (updateTask.isNotEmpty()) {
                val status =
                    databaseHandler.updateTodo(TodoModel(todoModel.id, updateTask))
                if (status > -1) {
                    Toast.makeText(this, "Task Updated.", Toast.LENGTH_LONG).show()
                    setupListOfDataIntoRecyclerView()
                    dialogInterface.dismiss()
                }
            } else {
                dialogBinding.updateTodoEditText.error = "Your field cannot be blank"
            }
        }

        updateDialog.setNegativeButton("Cancel") { dialogInterface, _ ->
            Toast.makeText(this, "Nothing Changed.", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss() // Dialog will be dismissed
        }

        updateDialog.show()  // show the dialog to UI
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
                    this,
                    "Task deleted successfully.",
                    Toast.LENGTH_LONG
                ).show()

                setupListOfDataIntoRecyclerView()
                dialogInterface.dismiss() // Dialog will be dismissed
            }
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


}






