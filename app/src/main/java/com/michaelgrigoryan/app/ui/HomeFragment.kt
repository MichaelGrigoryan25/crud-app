package com.michaelgrigoryan.app.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.michaelgrigoryan.app.R
import com.michaelgrigoryan.app.RecyclerAdapter
import com.michaelgrigoryan.app.db.User
import com.michaelgrigoryan.app.helper.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_container)
        val button = view.findViewById<FloatingActionButton>(R.id.add)

        // Attaching recycler view
        recyclerView.layoutManager = LinearLayoutManager(view.context.applicationContext)
        recyclerView.adapter = RecyclerAdapter(mutableListOf())

        // Listener to navigate to the other fragment
        button.setOnClickListener {
            view.findNavController().navigate(R.id.action_homeFragment_to_makeFragment)
        }

        // Getting the data from Room DB and rendering in Recycler View
        GlobalScope.launch(Dispatchers.IO) {
            // Get all users from the DB
            val db = DatabaseHelper(requireContext())
            val usersList: List<User> = db.getAll()

            // Render the Recycler View
            withContext(Dispatchers.Main) {
                if (usersList.isNullOrEmpty()) {
                    val noData = view.findViewById<TextView>(R.id.no_data)
                    noData.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    recyclerView.visibility = View.VISIBLE
                    recyclerView.adapter = RecyclerAdapter(usersList)
                }
            }
        }
    }
}