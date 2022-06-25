package com.project.edukasidiabetesmilitus.testimony

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.project.edukasidiabetesmilitus.R
import com.project.edukasidiabetesmilitus.databinding.ActivityTestimonyAddBinding
import java.util.*

class TestimonyAddActivity : AppCompatActivity() {

    private var _binding : ActivityTestimonyAddBinding? = null
    private val binding get() = _binding!!
    private var status = ""
    private var image = ""
    private var avatar = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTestimonyAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showDropdownStatus()

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.imageHint.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .compress(1024)
                .start(REQUEST_AVATAR_GALLERY)
        }


        binding.imageHintImage.setOnClickListener {
            ImagePicker.with(this)
                .galleryOnly()
                .compress(1024)
                .start(REQUEST_IMAGE_GALLERY)
        }


        binding.saveBtn.setOnClickListener {
            formValidation()
        }
    }

    private fun formValidation() {

        val title = binding.title.text.toString().trim()
        val description = binding.description.text.toString().trim()
        val name = binding.name.text.toString().trim()

        when {
            title.isEmpty() -> {
                Toast.makeText(this, "Judul postingan tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            }
            description.isEmpty() -> {
                Toast.makeText(this, "Deskripsi postingan tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            }
            name.isEmpty() -> {
                Toast.makeText(this, "Nama lengkap anda tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            }
            status == "" -> {
                Toast.makeText(this, "Status diabetesi tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                binding.progressBar.visibility = View.VISIBLE

                val uid = System.currentTimeMillis().toString()
                val data = mapOf(
                    "uid" to uid,
                    "title" to title,
                    "titleTemp" to title.lowercase(Locale.getDefault()),
                    "description" to description,
                    "name" to name,
                    "avatar" to avatar,
                    "image" to image,
                    "status" to status,
                    "commentCount" to "0",
                )

                FirebaseFirestore
                    .getInstance()
                    .collection("testimony")
                    .document(uid)
                    .set(data)
                    .addOnCompleteListener {
                        binding.progressBar.visibility = View.GONE
                        if (it.isSuccessful) {
                            showSuccessDialog()
                        } else {
                            showFailureDialog()
                        }
                    }
            }
        }
    }


    private fun showFailureDialog() {
        AlertDialog.Builder(this)
            .setTitle("Gagal Mengunggah Postingan")
            .setMessage("Ups, sepertinya koneksi internet anda tidak stabil, silahkan coba lagi nanti")
            .setIcon(R.drawable.ic_baseline_clear_24)
            .setPositiveButton("OKE") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .show()
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(this)
            .setTitle("Berhasil Mengunggah Postingan")
            .setMessage("Postingan anda akan segera terbit!")
            .setIcon(R.drawable.ic_baseline_check_circle_outline_24)
            .setPositiveButton("OKE") { dialogInterface, _ ->
                dialogInterface.dismiss()
                onBackPressed()
            }
            .show()
    }

    private fun showDropdownStatus() {
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.status, android.R.layout.simple_list_item_1
        )
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        binding.status.setAdapter(adapter)
        binding.status.setOnItemClickListener { _, _, _, _ ->
            status = binding.status.text.toString()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_GALLERY) {
                uploadArticleDp(data?.data, "image")
            } else if (requestCode == REQUEST_AVATAR_GALLERY) {
                uploadArticleDp(data?.data, "avatar")
            }
        }
    }


    /// fungsi untuk mengupload foto kedalam cloud storage
    private fun uploadArticleDp(data: Uri?, option: String) {
        val mStorageRef = FirebaseStorage.getInstance().reference
        val mProgressDialog = ProgressDialog(this)
        mProgressDialog.setMessage("Mohon tunggu hingga proses selesai...")
        mProgressDialog.setCanceledOnTouchOutside(false)
        mProgressDialog.show()
        val imageFileName = "$option/image_" + System.currentTimeMillis() + ".png"
        mStorageRef.child(imageFileName).putFile(data!!)
            .addOnSuccessListener {
                mStorageRef.child(imageFileName).downloadUrl
                    .addOnSuccessListener { uri: Uri ->
                        mProgressDialog.dismiss()
                        if(option == "image") {
                            image = uri.toString()
                            Glide.with(this)
                                .load(image)
                                .into(binding.image)
                        } else {
                            avatar = uri.toString()
                            Glide.with(this)
                                .load(avatar)
                                .into(binding.avatar)
                        }

                    }
                    .addOnFailureListener { e: Exception ->
                        mProgressDialog.dismiss()
                        Toast.makeText(
                            this,
                            "Gagal mengunggah gambar",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("imageDp: ", e.toString())
                    }
            }
            .addOnFailureListener { e: Exception ->
                mProgressDialog.dismiss()
                Toast.makeText(
                    this,
                    "Gagal mengunggah gambar",
                    Toast.LENGTH_SHORT
                )
                    .show()
                Log.d("imageDp: ", e.toString())
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val REQUEST_IMAGE_GALLERY = 1001
        const val REQUEST_AVATAR_GALLERY = 1002
    }
}