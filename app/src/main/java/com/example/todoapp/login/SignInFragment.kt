package com.example.todoapp.login

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.NoteDatabase
import com.example.todoapp.data.dao.UserDao
import com.example.todoapp.databinding.FragmentSignInBinding
import com.example.todoapp.model.User
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private var isPasswordVisible = false
    private lateinit var userDao: UserDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        // Set password visibility to hidden
        binding.editpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = NoteDatabase.getDatabase(requireContext())
        userDao = database.userDao()

        binding.textsignup.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        binding.textforgetpassword.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_forgetPasswordFragment)
        }

        binding.buttonsignin.setOnClickListener {
            val email = binding.editemail.text.toString().trim()
            val password = binding.editpassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                checkUser(email, password)
            } else {
                Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageViewEye.setOnClickListener {
            togglePasswordVisibility()
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide password
            binding.editpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.imageViewEye.setImageResource(R.drawable.eye_off)
        } else {
            // Show password
            binding.editpassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.imageViewEye.setImageResource(R.drawable.eye_on)
        }
        // Move cursor to the end of the text
        binding.editpassword.setSelection(binding.editpassword.text.length)
        isPasswordVisible = !isPasswordVisible
    }

    private fun checkUser(email: String, password: String) {
        lifecycleScope.launch {
            val user: User? = userDao.getUser(email, password)
            if (user != null) {
                // Tạo Bundle để truyền dữ liệu
                val bundle = Bundle().apply {
                    putString("FULL_NAME", user.fullname)
                    putString("EMAIL", user.email)
                }
                // Chuyển đến HomeScreenFragment với bundle
                findNavController().navigate(R.id.action_signInFragment_to_homeScreenFragment, bundle)
            } else {
                Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
