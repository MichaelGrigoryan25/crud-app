package com.michaelgrigoryan.app.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.michaelgrigoryan.app.R
import com.michaelgrigoryan.app.db.User
import com.michaelgrigoryan.app.helper.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MakeFragment : Fragment(R.layout.fragment_make) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fields
        val submit = view.findViewById<MaterialButton>(R.id.btn_create)
        val nameFi = view.findViewById<EditText>(R.id.ip_nameFirst)
        val nameLa = view.findViewById<EditText>(R.id.ip_nameLast)

        submit.setOnClickListener {
            if (nameFi.text.isEmpty() && nameLa.text.isNotEmpty()) {
                nameFi.error = "Please enter the first name"
            }
            else if (nameLa.text.isEmpty() && nameFi.text.isNotEmpty()) {
                nameLa.error = "Please enter the last name"
            }
            else if (nameFi.text.isEmpty() && nameLa.text.isEmpty()) {
                nameFi.error = "Please enter the first name"
                nameLa.error = "Please enter the last name"
            }
            else {
                GlobalScope.launch(Dispatchers.IO) {
                    val db = DatabaseHelper(requireContext())
                    val size = db.getAll().size + 1
                    val input = User(
                        size,
                        nameFi.text.toString(),
                        nameLa.text.toString()
                    )

                    db.insertAll(input)

                    withContext(Dispatchers.Main) {
                        findNavController().navigate(R.id.action_makeFragment_to_homeFragment)
                    }
                }
            }
        }
    }
}
