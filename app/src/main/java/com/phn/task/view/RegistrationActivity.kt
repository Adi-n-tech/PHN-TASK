package com.phn.task.view

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.phn.task.api.RetrofitService
import com.phn.task.databinding.ActivityRegistrationBinding
import com.phn.task.repository.MainRepository
import com.phn.task.viewmodel.MainViewModel
import com.phn.task.viewmodel.MyViewModelFactory

class RegistrationActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        //------
        val progressDialog = ProgressDialog(this@RegistrationActivity)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("please wait ...")
        //----
        viewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        )[MainViewModel::class.java]

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.userRegistered.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        viewModel.loading.observe(this, Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })
        binding.btnLogin.setOnClickListener {
            if (binding.etUsername.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.etEmail.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.etPassword.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.etConfirmPassword.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter confirm password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.etPassword.text.toString() != binding.etConfirmPassword.text.toString()) {
                Toast.makeText(this, "Password and confirm password should be same", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val user = hashMapOf(
                "username" to binding.etUsername.text.toString(),
                "email" to binding.etEmail.text.toString(),
                "password" to binding.etPassword.text.toString(),
            )
            viewModel.registerUser(user, 101)
        }

        binding.tvNewMember.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}