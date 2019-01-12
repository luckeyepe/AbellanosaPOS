package com.example.mickey.abellanosapos

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_recipe.*

class AddRecipeActivity : AppCompatActivity() {
    var GALLERY_REQUEST = 1
    var IMAGE_URL: Uri ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)

        imageView_addRecipeActivityRecipeImage.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            galleryIntent.type = "image/*"
            startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), GALLERY_REQUEST)
        }

        button_addRecipeActivityAdd.setOnClickListener {
            //todo save data
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            val imageURI: Uri = data!!.data
            IMAGE_URL = imageURI

            Picasso.get().load(imageURI).into(imageView_addRecipeActivityRecipeImage)
        }


    }
}
