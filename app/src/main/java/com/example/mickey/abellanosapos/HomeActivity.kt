package com.example.mickey.abellanosapos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mickey.abellanosapos.models.RecipeClass
import com.example.mickey.abellanosapos.viewholders.BasicRecipeRowViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        FirebaseAuth.getInstance().signInAnonymously()

//        button_homeGoToAddRecipe.setOnClickListener {
//            startActivity(Intent(this, AddRecipeActivity::class.java))
//        }

//        setUpDummy()

        setUpData()
    }

    private fun setUpData() {
        var adapter = GroupAdapter<ViewHolder>()
        val db = FirebaseFirestore.getInstance().collection("Recipes")

        db.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (querySnapshot != null){
                adapter.clear()

                for (document in querySnapshot){
                    var recipe = document.toObject(RecipeClass::class.java)
                    adapter.add(BasicRecipeRowViewHolder(recipe))
                }

                recyclerView_homeActivityRecyler.layoutManager = LinearLayoutManager(this)
                recyclerView_homeActivityRecyler.adapter = adapter
            }else{
                Log.e("Home", firebaseFirestoreException.toString())
            }
        }

        adapter.setOnItemClickListener { item, view ->
            val recipeItem = item as BasicRecipeRowViewHolder
            val intent = Intent(view.context, RecipeDetailsActivity::class.java)
            val recipeItemUid = recipeItem.recipe.recipe_id

            intent.putExtra("item_uid", recipeItemUid)
            startActivity(intent)
        }
    }

//    private fun setUpDummy() {
//        var adapter = GroupAdapter<ViewHolder>()
//
//        adapter.add(BasicRecipeRowViewHolder())
//        adapter.add(BasicRecipeRowViewHolder())
//        adapter.add(BasicRecipeRowViewHolder())
//        adapter.add(BasicRecipeRowViewHolder())
//        adapter.add(BasicRecipeRowViewHolder())
//        adapter.add(BasicRecipeRowViewHolder())
//
//        recyclerView_homeActivityRecyler.layoutManager = LinearLayoutManager(this)
//        recyclerView_homeActivityRecyler.adapter = adapter
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_recipe, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menuItem_menuAddRecipeAdd ->{
                startActivity(Intent(this, AddRecipeActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
