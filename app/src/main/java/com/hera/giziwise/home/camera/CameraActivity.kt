package com.hera.giziwise.home.camera

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import com.google.android.material.button.MaterialButton
import com.hera.giziwise.R
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hera.giziwise.api.ApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CameraActivity : AppCompatActivity() {

    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_PICK = 2
    private val REQUEST_CAMERA_PERMISSION = 3

    private lateinit var previewImage: ImageView

    private var selectedImage: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        previewImage = findViewById(R.id.preview_image)

        findViewById<MaterialButton>(R.id.btn_camera).setOnClickListener {
            checkCameraPermission()
        }

        findViewById<MaterialButton>(R.id.btn_gallery).setOnClickListener {
            openGallery()
        }

        findViewById<MaterialButton>(R.id.btn_upload).setOnClickListener {
            uploadImage()
        }
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        } else {
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun openGallery() {
        val pickPhotoIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_PICK)
    }

    private fun uploadImage() {
        if (selectedImage != null) {
            val imageRequestBody = ImageUtils.createImageRequestBody(selectedImage!!)
            val apiService = ApiConfig.getApiClient()

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.searchProductByImage(imageRequestBody)
                    if (response.isSuccessful) {
                        val productResponse = response.body()
                        val statusCode = productResponse?.statusCode
                        val messages = productResponse?.messages
                        val productData = productResponse?.data

                        withContext(Dispatchers.Main) {
                            val intent = Intent(this@CameraActivity, ResultActivity::class.java)

                            if (statusCode == 201) {
                                if (productData != null) {
                                    val productName = productData.name
                                    val productImage = productData.image
                                    val productTkpis = productData.tkpis.joinToString(separator = "\n") { tkpi ->
                                        "${tkpi.name}: ${tkpi.value} ${tkpi.unit}"
                                    }

                                    intent.putExtra("productName", productName)
                                    intent.putExtra("productImage", productImage)
                                    intent.putExtra("productTkpis", productTkpis)

                                    Toast.makeText(applicationContext, "Product found: $productName", Toast.LENGTH_SHORT).show()
                                } else {
                                    intent.putExtra("productName", "")
                                    intent.putExtra("productImage", "")
                                    intent.putExtra("productTkpis", "")

                                    Toast.makeText(applicationContext, "No matching products found", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                intent.putExtra("productName", "")
                                intent.putExtra("productImage", "")
                                intent.putExtra("productTkpis", "")

                                Toast.makeText(applicationContext, "Error: $messages", Toast.LENGTH_SHORT).show()
                            }

                            startActivity(intent)
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(applicationContext, "Upload failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.e("GiziWise", e.message, e)
                        Toast.makeText(applicationContext, "Upload failed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            previewImage.setImageBitmap(imageBitmap)
            selectedImage = imageBitmap
        } else if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK) {
            val imageUri = data?.data
            val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
            previewImage.setImageBitmap(imageBitmap)
            selectedImage = imageBitmap
        }
    }

    fun goBack(view: View) {
        finish()
    }
}


