package com.hera.giziwise.home.recipe

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hera.giziwise.R

class RecipeAdapter(
    private var recipeList: List<Recipe>,
    private val recipeClickListener: RecipeClickListener
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    private var isRecipeActivity: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_resep_kesehatan, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]
        holder.bind(recipe)
        holder.itemView.setOnClickListener {
            recipeClickListener.onRecipeClick(recipe)
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }

    fun updateData(newRecipeList: List<Recipe>) {
        recipeList = newRecipeList
        notifyDataSetChanged()
    }

    fun setRecipeActivity(isRecipeActivity: Boolean) {
        this.isRecipeActivity = isRecipeActivity
    }

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recipeImage: ImageView = itemView.findViewById(R.id.resep_image)
        private val recipeTitle: TextView = itemView.findViewById(R.id.resep_title)

        fun bind(recipe: Recipe) {
            recipeTitle.text = recipe.title

            if (isRecipeActivity) {
                recipeTitle.maxLines = 1
                recipeTitle.ellipsize = TextUtils.TruncateAt.END
            } else {
                recipeTitle.maxLines = 2
                recipeTitle.ellipsize = null
            }

            Glide.with(itemView)
                .load(recipe.image)
                .into(recipeImage)
        }
    }

    interface RecipeClickListener {
        fun onRecipeClick(recipe: Recipe)
    }
}





