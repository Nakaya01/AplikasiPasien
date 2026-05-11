package com.example.aplikasipasien.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.aplikasipasien.api.RetrofitClient
import com.example.aplikasipasien.databinding.ActivityLoginBinding
import com.example.aplikasipasien.model.LoginRequest
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                prosesLogin(email, password)
            } else {
                Toast.makeText(this, "Email dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun prosesLogin(email: String, pass: String) {
        binding.progressBar.visibility = View.VISIBLE // Tampilkan loading
        binding.btnLogin.isEnabled = false // Matikan tombol sementara

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.loginUser(LoginRequest(email, pass))

                if (response.isSuccessful && response.body() != null) {
                    val token = response.body()!!.data?.token
                    val namaUser = response.body()!!.data?.user?.name

                    if (token != null) {
                        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                        sharedPref.edit()
                            .putString("TOKEN", token)
                            .putString("USER_NAME", namaUser)
                            .commit()

                        Toast.makeText(this@LoginActivity, "Login Berhasil", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Login Gagal. Periksa email/password.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE // Sembunyikan loading
                binding.btnLogin.isEnabled = true // Nyalakan tombol
            }
        }
    }
}