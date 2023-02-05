package com.example.petcare


import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
import android.widget.ImageButton
import android.widget.Toast
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_register, container, false)
        // Inflate the layout for this fragment

        val returnButton=view.findViewById<Button>(R.id.returnButton2)
        val startFragment=StartFragment()
        val registerButton = view.findViewById<Button>(R.id.buttonRegister2)
        val loginFragment = LoginFragment()

        val db = Firebase.firestore

        val guestNameTxt=view.findViewById<EditText>(R.id.editIme)
        val guestLastNameTxt=view.findViewById<EditText>(R.id.editPrezime)
        val guestUsernameTxt=view.findViewById<EditText>(R.id.editUsername)
        val guestPasswordTxt=view.findViewById<EditText>(R.id.editPswrd)



        returnButton.setOnClickListener {
            val fragmentTransaction:FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.RegisterFragment, startFragment)
            fragmentTransaction?.commit()
        }

        registerButton.setOnClickListener {

            var guestName=guestNameTxt.text.toString().trim()
            var guestLastName=guestLastNameTxt.text.toString().trim()
            var guestUsername=guestUsernameTxt.text.toString().trim()
            var guestPassword= guestPasswordTxt.text.toString().trim()



            if(guestPassword.length<8){
                Toast.makeText(activity, "Lozinka mora biti dulja od 8 slova", Toast.LENGTH_SHORT).show()
                guestPassword=""
            }


            if(guestName.isNotEmpty() && guestLastName.isNotEmpty() && guestUsername.isNotEmpty() && guestPassword.isNotEmpty()){

                val items= hashMapOf<String, Any>()
                items.put("name", guestName)
                items.put("lastName", guestLastName)
                items.put("username", guestUsername)
                items.put("password", guestPassword)


                db.collection("Guests").document(guestUsername).set(items).addOnSuccessListener {
                    Toast.makeText(activity, "Registracija uspješna! Molimo Vas da se još jednom prijavite!", Toast.LENGTH_SHORT).show()


                    val fragmentTransaction: FragmentTransaction? =
                        activity?.supportFragmentManager?.beginTransaction()
                    fragmentTransaction?.replace(R.id.RegisterFragment, loginFragment)
                    fragmentTransaction?.commit()

                }.addOnFailureListener {
                        exeption: java.lang.Exception -> Toast.makeText(activity,"Registracija nije uspješna, pokušajte ponovno!", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(activity, "Molimo popunite sva polja", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }
}