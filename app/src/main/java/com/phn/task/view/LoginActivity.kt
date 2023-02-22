package com.phn.task.view

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.phn.task.api.RetrofitService
import com.phn.task.databinding.ActivityLoginBinding
import com.phn.task.repository.MainRepository
import com.phn.task.utils.PreferenceUtils
import com.phn.task.viewmodel.MainViewModel
import com.phn.task.viewmodel.MyViewModelFactory

class LoginActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val retrofitService = RetrofitService.getInstance()
        val mainRepository = MainRepository(retrofitService)
        //------
        if (PreferenceUtils(this).getString("isUserLogin") == "true") {
            startActivity(Intent(this, MainActivity::class.java))
        }
        val progressDialog = ProgressDialog(this@LoginActivity)
        progressDialog.setTitle("Loading")
        progressDialog.setMessage("please wait ...")
        //----
        viewModel = ViewModelProvider(
            this, MyViewModelFactory(mainRepository)
        )[MainViewModel::class.java]

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.userRegistered.observe(this) {
            PreferenceUtils(this).putString("isUserLogin", "true")
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }

        viewModel.loading.observe(this, Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })
        binding.btnLogin.setOnClickListener {
            if (binding.etEmail.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.etPassword.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.loginUser(
                binding.etEmail.text.toString(), binding.etPassword.text.toString(), 102
            )
        }

        binding.tvNewMember.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }
}