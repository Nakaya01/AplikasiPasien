package com.example.aplikasipasien.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aplikasipasien.api.RetrofitClient
import com.example.aplikasipasien.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PasienAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvPasien.layoutManager = LinearLayoutManager(this)

        val sharedPref = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val token = sharedPref.getString("TOKEN", null)
        val userName = sharedPref.getString("USER_NAME", "User")

        binding.tvWelcome.text = "Selamat datang, $userName!"

        if (token != null) {
            ambilDataPasien(token)
        } else {
            Toast.makeText(this, "Sesi habis, silakan login", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun ambilDataPasien(token: String) {
        binding.progressBar.visibility = View.VISIBLE // Tampilkan loading

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getPasien("Bearer $token")

                if (response.isSuccessful && response.body() != null) {
                    val daftarPasien = response.body()!!.data
                    adapter = PasienAdapter(daftarPasien)
                    binding.rvPasien.adapter = adapter
                } else {
                    Toast.makeText(this@MainActivity, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error koneksi: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE // Sembunyikan loading
            }
        }
    }
}