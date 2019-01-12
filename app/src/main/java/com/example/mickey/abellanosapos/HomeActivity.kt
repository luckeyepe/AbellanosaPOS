package com.example.mickey.abellanosapos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mickey.abellanosapos.viewholders.BasicRecipeRowViewHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        button_homeGoToAddRecipe.setOnClickListener {
//            startActivity(Intent(this, AddRecipeActivity::class.java))
//        }

        setUpDummy()
    }

    private fun setUpDummy() {
        var adapter = GroupAdapter<ViewHolder>()

        adapter.add(BasicRecipeRowViewHolder())
        adapter.add(BasicRecipeRowViewHolder())
        adapter.add(BasicRecipeRowViewHolder())
        adapter.add(BasicRecipeRowViewHolder())
        adapter.add(BasicRecipeRowViewHolder())
        adapter.add(BasicRecipeRowViewHolder())

        recyclerView_homeActivityRecyler.layoutManager = LinearLayoutManager(this)
        recyclerView_homeActivityRecyler.adapter = adapter
    }
}
