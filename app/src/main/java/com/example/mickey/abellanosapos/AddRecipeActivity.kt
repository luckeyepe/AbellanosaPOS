package com.example.mickey.abellanosapos

import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mickey.abellanosapos.models.RecipeClass
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_recipe.*
import java.util.*

class AddRecipeActivity : AppCompatActivity() {
    var GALLERY_REQUEST = 1
    var IMAGE_URL: Uri ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_recipe)
//        editText_addRecipeActivityCookTime.isFocusable = false
//        editText_addRecipeActivityCookTime.isClickable = true

        imageView_addRecipeActivityRecipeImage.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            galleryIntent.type = "image/*"
            startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), GALLERY_REQUEST)
        }

        //popup time picker
//        editText_addRecipeActivityCookTime.setOnClickListener {
//            var currentTime = Calendar.getInstance()
//            var hour = currentTime.get(Calendar.HOUR_OF_DAY)
//            var min = currentTime.get(Calendar.MINUTE)
//
//            var timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
//                var cookTime = "$hourOfDay:$minute"
//                editText_addRecipeActivityCookTime.setText(cookTime)
//            }, hour, min, false)
//
//            timePickerDialog.setTitle("Cook Time")
//            timePickerDialog.show()
//        }


        button_addRecipeActivityAdd.setOnClickListener {
            //todo save data
            if(IMAGE_URL != null){
                if(isCompleteData()){
                    val db = FirebaseFirestore.getInstance().collection("Recipes")

                    var cookTime = editText_addRecipeActivityCookTime.text.toString().trim()
                    var ingredients = editText_addRecipeActivityIngredients.text.toString().trim()
                    var instructions = editText_addRecipeActivityInstructions.text.toString().trim()
                    var mealType = editText_addRecipeActivityMealType.text.toString().trim()
                    var numberOfServing = editText_addRecipeActivityNumberOfServing.text.toString().trim().toInt()
                    var recipeName = editText_addRecipeActivityRecipeName.text.toString().trim()

                    var recipe = RecipeClass(recipeName, numberOfServing, mealType, cookTime, ingredients, instructions,
                        null, null)

                    val progress = ProgressDialog(this)
                    progress.setTitle("Uploading")
                    progress.setMessage("Please wait while recipe is being uploaded...")
                    progress.show()

                    db.add(recipe).addOnCompleteListener {
                        task: Task<DocumentReference> ->
                        if (task.isSuccessful){
                            recipe.recipe_id = task.result!!.id
                            db.document(taskId.toString()).update("recipe_id", recipe.recipe_id)

                            val storage = FirebaseStorage.getInstance().reference
                                .child("recipe_pictures")
                                .child("$taskId.jpg")

                            val filePath = storage.putFile(IMAGE_URL!!)

                            var urlTask = filePath.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                                if (!task.isSuccessful) {
                                    task.exception?.let {
                                        throw it
                                    }
                                }
                                return@Continuation storage.downloadUrl
                            }).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val downloadUri = task.result

                                    db.document(recipe.recipe_id!!)
                                        .update("recipe_imageUrl", downloadUri.toString())
                                        .addOnCompleteListener {
                                            task: Task<Void> ->
                                            if (task.isSuccessful){
                                                progress.dismiss()
                                                Log.d("AddRecipe", "Upload Success")
                                                successDialog("Recipe has been uploaded", "SUCCESS")
                                            }else{
                                                progress.dismiss()
                                                Log.e("AddRecipe", task.exception.toString())
                                                errorDialog("Check your internet connection", "NO INTERNET")
                                            }
                                        }
                                }
                            }
                        }else{
                            progress.dismiss()
                            Log.e("AddRecipe", task.exception.toString())
                            errorDialog("Check your internet connection", "NO INTERNET")
                        }
                    }
                }else{
                    errorDialog("Please fill up all the necessary details", "MISSING DATA")
                }
            }else{
                errorDialog("Please select an image for the recipe", "NO IMAGE")
            }
        }


    }


    private fun isCompleteData(): Boolean {
        return (!editText_addRecipeActivityCookTime.text.toString().trim().isEmpty()
                && !editText_addRecipeActivityIngredients.text.toString().trim().isEmpty()
                && !editText_addRecipeActivityInstructions.text.toString().trim().isEmpty()
                && !editText_addRecipeActivityMealType.text.toString().trim().isEmpty()
                && !editText_addRecipeActivityNumberOfServing.text.toString().trim().isEmpty()
                && !editText_addRecipeActivityRecipeName.text.toString().trim().isEmpty())
    }

    private fun errorDialog(message: String, title: String) {
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setIcon(R.drawable.ic_error_outline_accent)
        alertDialog.setMessage(message)
        alertDialog.setTitle(title)
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()
    }

    private fun successDialog(message: String, title: String) {
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setIcon(R.drawable.ic_info_outline_accent)
        alertDialog.setMessage(message)
        alertDialog.setTitle(title)
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        alertDialog.show()
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
