package com.example.petcare


import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_login, container, false)


        val returnButton = view.findViewById<Button>(R.id.returnButton1)
        val loginButton = view.findViewById<Button>(R.id.buttonLogin2)
        val startFragment = StartFragment()
        val homeFragment = HomeFragment()

        val db=Firebase.firestore

        val usernameTxt = view.findViewById<EditText>(R.id.editUsername)
        val passwordTxt = view.findViewById<EditText>(R.id.editPswrd)

        var username: String = ""
        var password: String = ""


        returnButton.setOnClickListener {
            val fragmentTransaction:FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.LoginFragment, startFragment)
            fragmentTransaction?.commit()
        }

        loginButton.setOnClickListener {

            username = usernameTxt.text.toString().trim()
            password = passwordTxt.text.toString().trim()

            if(username.isNotEmpty()&&password.isNotEmpty()) {

                val docRef = db.collection("Guests").document(username)
                docRef.get().addOnSuccessListener { document ->
                        if (document != null) {

                            Log.d(TAG, "DocumentSnapshot data: ${document.data}")

                            val data = document.data as Map<String, String>

                            if (password == data["password"]) {

                                //prebacivanje u HomeFragment

                                val fragmentTransaction: FragmentTransaction? =
                                    activity?.supportFragmentManager?.beginTransaction()
                                fragmentTransaction?.replace(R.id.LoginFragment, homeFragment)
                                fragmentTransaction?.commit()

                                val bundle = Bundle()
                                bundle.putString("username", username)
                                homeFragment.arguments = bundle

                                Toast.makeText(activity, "Prijava uspješna", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(
                                    activity,
                                    "Prijava neuspješna! Molimo provjerite unesene podatke te probajte opet!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        } else {
                            Log.d(TAG, "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "get failed with ", exception)
                    }
            }
            else{
                Toast.makeText(activity, "Molimo popunite sva polja", Toast.LENGTH_SHORT).show()
            }

        }


        return view
    }


}