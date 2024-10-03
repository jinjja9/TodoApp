package com.example.todoapp.login

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentForgetPasswordBinding


class ForgetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgetPasswordBinding
    private var isPasswordVisible = false
    private var isConfirmPasswordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentForgetPasswordBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.left.setOnClickListener {
            findNavController().navigate(R.id.action_forgetPasswordFragment_to_signInFragment)
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

    }
    private fun togglePasswordVisibility(editText: EditText, imageView: ImageView, isVisible: Boolean)
    {
        if(isVisible)
        {
            editText.inputType=InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            imageView.setImageResource(R.drawable.eye_off)
        }
        else {
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            imageView.setImageResource(R.drawable.eye_on)
        }
        editText.setSelection(editText.text.length)
    }
}
