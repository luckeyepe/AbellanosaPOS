package com.example.mickey.abellanosapos.viewholders

import com.example.mickey.abellanosapos.R
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.row_recipe_layout.view.*

class BasicRecipeRowViewHolder:Item<ViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.row_recipe_layout
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        val foodPicture = viewHolder.itemView.imageView_recipeLayoutRowImage
        val recipeName = viewHolder.itemView.textView_recipeLayoutRowRecipeName
        val recipeServing = viewHolder.itemView.textView_recipeLayoutRowServingSize


    }
}