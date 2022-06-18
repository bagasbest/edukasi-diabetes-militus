package com.project.edukasidiabetesmilitus.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.project.edukasidiabetesmilitus.R
import com.project.edukasidiabetesmilitus.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private var _binding : ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.loginBtn.setOnClickListener {
            formValidation()
        }
    }

    private fun formValidation() {
        val email = binding.email.text.toString().trim()
        val password = binding.password.text.toString().trim()

        if(email.isEmpty()) {
            Toast.makeText(this, "Email tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Kata sandi tidak boleh kosong!", Toast.LENGTH_SHORT).show()
        } else {
            binding.progressBar.visibility = View.VISIBLE

            FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    binding.progressBar.visibility = View.GONE
                    if(it.isSuccessful) {
                        showSuccessDialog()
                    } else {
                        Toast.makeText(this, "Email atau Kata sandi anda salah!", Toast.LENGTH_SHORT).show()
                    }

                }
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this)
            .setTitle("Sukses Login Admin")
            .setMessage("Selamat menjalankan tugas, silahkan berdiskusi pada postingan konsultasi yang di unggah pengguna!")
            .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
            .setPositiveButton("OKE") { dialogInterface, _ ->
                dialogInterface.dismiss()
                onBackPressed()
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}