package com.example.petcare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentTransaction


class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        // Inflate the layout for this fragment
        val LoginFragment = LoginFragment()
        val RegisterFragment = RegisterFragment()
        val loginButton = view.findViewById<Button> (R.id.buttonLogin)
        val registerButton = view.findViewById<Button> (R.id.buttonRegister)


        loginButton.setOnClickListener{
            val fragmentTransaction:FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.StartFragment, LoginFragment)
            fragmentTransaction?.commit()
        }

        registerButton.setOnClickListener{
            val fragmentTransaction:FragmentTransaction? =
                activity?.supportFragmentManager?.beginTransaction()
            fragmentTransaction?.replace(R.id.StartFragment, RegisterFragment)
            fragmentTransaction?.commit()
        }
        return view
    }
}