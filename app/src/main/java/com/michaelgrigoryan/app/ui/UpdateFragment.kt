package com.michaelgrigoryan.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.michaelgrigoryan.app.R
import com.michaelgrigoryan.app.db.User
import com.michaelgrigoryan.app.helper.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        val nameFi = view.findViewById<EditText>(R.id.ip_up_nameFirst)
        val nameLa =  view.findViewById<EditText>(R.id.ip_up_nameLast)

        nameFi?.setText(args.currentUser.lastName)
        nameLa?.setText(args.currentUser.lastName)

        view.findViewById<MaterialButton>(R.id.btn_update)?.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val updatedUser = User(
                        args.currentUser.id,
                        nameFi.text.toString(),
                        nameLa.text.toString()
                )
                val db = DatabaseHelper(requireContext())
                db.update(updatedUser)
                withContext(Dispatchers.Main) {
                    findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
                }
            }
        }

        return view
    }
}