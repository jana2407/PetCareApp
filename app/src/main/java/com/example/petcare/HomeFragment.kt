package com.example.petcare

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.widget.EditText
import android.widget.Button


class HomeFragment : Fragment() {

    @SuppressLint("SetTextI18n", "MissingInflatedId")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val logout = view.findViewById<ImageButton>(R.id.logoutButton)
        val startFragment = StartFragment()
        val spremiBtn = view.findViewById<Button>(R.id.buttonSpremi)
        val dohvatiBtn = view.findViewById<Button>(R.id.dohvatiButton)

        val db = Firebase.firestore

        val lijekoviTxt = view.findViewById<EditText>(R.id.editLijekovi)
        val kupanjeTxt = view.findViewById<EditText>(R.id.editKupanje)
        val sisanjeTxt = view.findViewById<EditText>(R.id.editŠišanje)
        val cesljanjeTxt = view.findViewById<EditText>(R.id.editČešljanje)

        val username: String = arguments?.getString("username").toString()


        spremiBtn.setOnClickListener {
            val lijekovi = lijekoviTxt.text.toString()
            val kupanje = kupanjeTxt.text.toString()
            val sisanje = sisanjeTxt.text.toString()
            val cesljanje = cesljanjeTxt.text.toString()

            val db = FirebaseFirestore.getInstance()
            val Zadaci: MutableMap<String, Any> = HashMap()

            Zadaci["Lijekovi"] = lijekovi
            Zadaci["Kupanje"] = kupanje
            Zadaci["Šišanje"] = sisanje
            Zadaci["Češljanje"] = cesljanje

            db.collection("Zadaci").document(username).set(Zadaci).addOnSuccessListener {
                Toast.makeText(activity, "Uspjesno uneseno", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener {
                    Toast.makeText(activity, "Neuspjesno uneseno", Toast.LENGTH_SHORT).show()
                }

        }




        dohvatiBtn.setOnClickListener {

            var firestore: FirebaseFirestore
            firestore = FirebaseFirestore.getInstance()
            firestore.collection("Zadaci").document(username).get().addOnSuccessListener {

                Toast.makeText(
                    activity,
                    "Lijekovi: ${it.get("Lijekovi")}, Kupanje: ${it.get("Kupanje")}, Šišanje: ${it.get("Šišanje")}, Češljanje: ${it.get("Češljanje")}", Toast.LENGTH_LONG).show()
            }
                .addOnFailureListener {
                    it.printStackTrace()
                    Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
                }

        }

        logout.setOnClickListener {
            val fragmentTransaction: FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.HomeFragment, startFragment)
            fragmentTransaction?.commit()
        }





        return view
    }
}















