package com.example.todoapp.login

import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.viewmodel.UserViewModel
import com.example.todoapp.databinding.FragmentSignUpBinding
import com.example.todoapp.model.User

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val userViewModel: UserViewModel by viewModels()

    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textlogin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        binding.imageViewEye.setOnClickListener {
            togglePasswordVisibility(binding.editpassword, binding.imageViewEye, isPasswordVisible)
            isPasswordVisible = !isPasswordVisible
        }

        binding.imageViewEye1.setOnClickListener {
            togglePasswordVisibility(
                binding.editrpassword,
                binding.imageViewEye1,
                isConfirmPasswordVisible
            )
            isConfirmPasswordVisible = !isConfirmPasswordVisible
        }

        binding.buttonsignup.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val email = binding.editemail.text.toString().trim()
        val fullname = binding.editfullname.text.toString().trim()
        val password = binding.editpassword.text.toString().trim()
        val confirmPassword = binding.editrpassword.text.toString().trim()

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(fullname) || TextUtils.isEmpty(password)) {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        val user = User(email = email, fullname = fullname, password = password)
        userViewModel.insert(user)

        Toast.makeText(requireContext(), "User registered successfully", Toast.LENGTH_SHORT).show()

        findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
    }

    private fun togglePasswordVisibility(editText: EditText, imageView: ImageView, isVisible: Boolean) {
        if (isVisible) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            imageView.setImageResource(R.drawable.eye_off)
        } else {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            imageView.setImageResource(R.drawable.eye_on)
        }
        editText.setSelection(editText.text.length)
    }
}
