package com.example.todoapp.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentProfileScreenBinding

class ProfileScreenFragment : Fragment() {

    private lateinit var binding: FragmentProfileScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileScreenBinding.inflate(inflater, container, false)

        val fullName = arguments?.getString("fullName")
        val email = arguments?.getString("email")

        binding.fullname.text = fullName ?: "No name available"
        binding.email.text = email ?: "No email available"
        binding.password.setOnClickListener {
            findNavController().navigate(R.id.action_profileScreenFragment_to_forgetPasswordFragment)
        }
        binding.home.setOnClickListener {
            findNavController().navigate(R.id.action_profileScreenFragment_to_homeScreenFragment)
        }
        binding.buttonlogout.setOnClickListener {
            findNavController().navigate(R.id.action_profileScreenFragment_to_signInFragment)
        }


        return binding.root
    }
}
