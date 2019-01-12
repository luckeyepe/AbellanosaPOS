package com.example.mickey.abellanosapos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        button_homeGoToAddRecipe.setOnClickListener {
//            startActivity(Intent(this, AddRecipeActivity::class.java))
//        }
    }
}
