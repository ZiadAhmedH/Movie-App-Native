package com.ziadahmed.logintask

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class InfoActivity : AppCompatActivity() {



    lateinit var receiver_msg: TextView

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var imageView: ImageView




    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri? = data.data
            // Set the selected image to ImageView
            imageUri?.let {
                val bitmap = ImageUtil.getBitmapFromUri(this, it)
                bitmap?.let { bmp ->
                    val circularBitmap = ImageUtil.getCircularBitmap(bmp)
                    imageView.setImageBitmap(circularBitmap)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.information_activity)

        imageView = findViewById(R.id.imageView)
        val buttonSelectImage: Button = findViewById(R.id.buttonSelectImage)

        buttonSelectImage.setOnClickListener {
            // Launch the image picker
            openImagePicker()
        }
    }

}