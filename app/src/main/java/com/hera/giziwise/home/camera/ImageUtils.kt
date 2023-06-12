package com.hera.giziwise.home.camera

import android.graphics.Bitmap
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import java.io.ByteArrayOutputStream
import okhttp3.RequestBody.Companion.toRequestBody
import okio.ByteString.Companion.toByteString

object ImageUtils {
    private const val IMAGE_UPLOAD_KEY = "image"

    fun createImageRequestBody(bitmap: Bitmap): MultipartBody.Part {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBody = byteArrayOutputStream.toByteArray()

        val requestBody = imageBody.toByteString().toRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(IMAGE_UPLOAD_KEY, "image.jpg", requestBody)
    }
}

