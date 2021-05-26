package com.techmedia.todolistapp.adapter


import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techmedia.todolistapp.TodoListActivity
import com.techmedia.todolistapp.databinding.TodoItemBinding
import com.techmedia.todolistapp.model.TodoModel

class TodoAdapter(private val context: Context, private val todos: ArrayList<TodoModel>) :
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    /**
     * Using viewBinding
     */
    class TodoViewHolder(val itemViewBinding: TodoItemBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)

    /**
     * Inflates the todo item views which is designed in the XML layout file
     **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            TodoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    /**
     * Strike Through the textView if toggle and vise versa
     */
    private fun toggleStrikeThrough(todoTitle: TextView, isChecked: Boolean) {
        /**
         * Checking if the isChecked is true, and if it is true strike through the textView
         */
        if (isChecked) {
            todoTitle.paintFlags = todoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            todoTitle.paintFlags = todoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }


    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        val currentTodo = todos[position]
        holder.itemViewBinding.apply {
            todoTitle.text = currentTodo.title
            todoDone.isChecked = currentTodo.isChecked
            toggleStrikeThrough(todoTitle, currentTodo.isChecked)

//             call the toggleStrikeThrough function when the checkbox is clicked
            todoDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(todoTitle, isChecked)
                currentTodo.isChecked = !currentTodo.isChecked
            }
            ivEdit.setOnClickListener {
                if (context is TodoListActivity) {
                    context.updateTodoDialog(currentTodo)
                }
            }

            ivDelete.setOnClickListener {
                if (context is TodoListActivity) {
                    context.deleteTodoDialog(currentTodo)
                }
            }

        }
    }


    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return todos.size
    }


}

