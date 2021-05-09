package com.techmedia.todolistapp.adapter


import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techmedia.todolistapp.databinding.TodoItemBinding
import com.techmedia.todolistapp.model.Todo

class TodoAdapter(
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    /**
     * Using viewBinding
     */
    class TodoViewHolder(val itemViewBinding: TodoItemBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)

    /**
     * Create new views (invoked by the layout manager)
     */
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

    /**
     * function for adding todo items
     */
    fun addTodoItem(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }


    /**
     * function for Deleting todo items
     */
    fun deleteTodoItem() {
        todos.removeAll { todo ->
            todo.isChecked

        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currentTodo = todos[position]
        holder.itemViewBinding.apply {

            todoTitle.text = currentTodo.title
            todoDone.isChecked = currentTodo.isChecked
            toggleStrikeThrough(todoTitle, currentTodo.isChecked)
            /**
             * calls the toggleStrikeThrough function when the checkbox is clicked
             */
            todoDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(todoTitle, isChecked)
                currentTodo.isChecked = !currentTodo.isChecked
            }

        }

    }

    /**
     * Return the size of the Todo
     */
    override fun getItemCount(): Int {
        return todos.size
    }

}

