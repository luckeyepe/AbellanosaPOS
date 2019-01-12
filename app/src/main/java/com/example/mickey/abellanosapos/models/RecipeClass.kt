package com.example.mickey.abellanosapos.models

class RecipeClass {
    var recipe_name: String ?= null
    var recipe_number_of_serving: Int ?= null
    var recipe_meal_type: String ?= null
    var recipe_cook_time: String ?= null
    var recipe_ingredients: String ?= null
    var recipe_instructions: String ?= null
    var recipe_imageUrl: String ?= null
    var recipe_id: String ?= null

    constructor()
    
    constructor(
        recipe_name: String?,
        recipe_number_of_serving: Int?,
        recipe_meal_type: String?,
        recipe_cook_time: String?,
        recipe_ingredients: String?,
        recipe_instructions: String?,
        recipe_imageUrl: String?,
        recipe_id: String?
    ) {
        this.recipe_name = recipe_name
        this.recipe_number_of_serving = recipe_number_of_serving
        this.recipe_meal_type = recipe_meal_type
        this.recipe_cook_time = recipe_cook_time
        this.recipe_ingredients = recipe_ingredients
        this.recipe_instructions = recipe_instructions
        this.recipe_imageUrl = recipe_imageUrl
        this.recipe_id = recipe_id
    }


}