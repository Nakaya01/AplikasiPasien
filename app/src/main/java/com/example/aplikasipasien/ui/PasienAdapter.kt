package com.example.aplikasipasien.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikasipasien.databinding.ItemPasienBinding
import com.example.aplikasipasien.model.Pasien

class PasienAdapter(private val listPasien: List<Pasien>) :
    RecyclerView.Adapter<PasienAdapter.PasienViewHolder>() {

    inner class PasienViewHolder(val binding: ItemPasienBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasienViewHolder {
        val binding = ItemPasienBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PasienViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PasienViewHolder, position: Int) {
        val pasien = listPasien[position]

        with(holder.binding) {
            tvNamaPasien.text = pasien.nama
            tvTanggalLahir.text = "Tanggal Lahir :\n${pasien.tanggalLahir}"

            val jkLengkap = if (pasien.jenisKelamin == "L") "Laki-laki" else "Perempuan"
            tvJenisKelamin.text = "Jenis Kelamin :\n$jkLengkap"

            tvNoTelepon.text = "No. Telepon :\n${pasien.noTelepon}"
            tvAlamat.text = "Alamat :\n${pasien.alamat}"
        }
    }

    override fun getItemCount(): Int = listPasien.size
}