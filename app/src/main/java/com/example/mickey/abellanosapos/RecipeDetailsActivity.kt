package com.example.mickey.abellanosapos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mickey.abellanosapos.models.RecipeClass
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_recipe_details.*

class RecipeDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        val recipeUid = intent.getStringExtra("item_uid")

        FirebaseFirestore.getInstance()
            .collection("Recipes")
            .document(recipeUid)
            .get().addOnCompleteListener {
                task: Task<DocumentSnapshot> ->
                if (task.isSuccessful){
                    val result = task.result!!.toObject(RecipeClass::class.java)!!

                    textView_addRecipeActivityMealType.text = result.recipe_meal_type
                    textView_addRecipeActivityRecipeName.text = result.recipe_name
                    textView_addRecipeActivityCookTimeAndPortions.text = "${result.recipe_cook_time} hour. Serves ${result.recipe_number_of_serving}"
                    textView_addRecipeActivityTotalCookTime.text = "Total cook time: ${result.recipe_cook_time}"
                    textView_addRecipeActivityIngredients.text = result.recipe_ingredients


                    Picasso.get().load(result.recipe_imageUrl).into(imageView_recipeDetailsActivityRecipeImage2)
                }
            }
    }
}
