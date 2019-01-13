package com.example.mickey.abellanosapos.viewholders

import com.example.mickey.abellanosapos.R
import com.example.mickey.abellanosapos.models.RecipeClass
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_recipe_layout.view.*

class BasicRecipeRowViewHolder(var recipe: RecipeClass):Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.row_recipe_layout
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val foodPicture = viewHolder.itemView.imageView_recipeLayoutRowImage
        val recipeName = viewHolder.itemView.textView_recipeLayoutRowRecipeName
        val recipeServing = viewHolder.itemView.textView_recipeLayoutRowServingSize

        if (recipe.recipe_imageUrl == null){
            Picasso.get().load(R.drawable.ic_error_outline_accent).into(foodPicture)
        }else{
            Picasso.get().load(recipe.recipe_imageUrl).into(foodPicture)
        }

        recipeName.text = recipe.recipe_name
        recipeServing.text = "${recipe.recipe_number_of_serving} Servings"
    }
}