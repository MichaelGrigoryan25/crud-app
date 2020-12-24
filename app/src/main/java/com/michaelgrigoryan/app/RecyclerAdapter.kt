package com.michaelgrigoryan.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.michaelgrigoryan.app.db.User
import com.michaelgrigoryan.app.helper.DatabaseHelper
import com.michaelgrigoryan.app.ui.HomeFragmentDirections
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RecyclerAdapter(
    private val users: List<User>
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name)
        var deleteItem: MaterialButton = view.findViewById(R.id.delete_item)
        var updateItem: MaterialButton = view.findViewById(R.id.update_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = (users[position].firstName + " " + users[position].lastName)
        holder.deleteItem.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val db = DatabaseHelper(it.context.applicationContext)
                db.delete(users[position])
            }
        }
        holder.updateItem.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(users[position])
            holder.updateItem.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = users.size
}