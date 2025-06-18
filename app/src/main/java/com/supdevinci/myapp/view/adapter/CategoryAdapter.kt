package com.supdevinci.myapp.view.adapter

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.supdevinci.myapp.R
import com.supdevinci.myapp.data.VarProvider

class CategoryAdapter(
    private val categories: List<Pair<String, String>>,
    private val onCategoryClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryButton: Button = itemView.findViewById(R.id.categoryRandom)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val (key, label) = categories[position]
        holder.categoryButton.text = label

        holder.categoryButton.setOnClickListener { view ->
            holder.categoryButton.background = AppCompatResources.getDrawable(
                view.context,
                R.drawable.button_background_green
            )

            VarProvider.category = key

            Handler(Looper.getMainLooper()).postDelayed({
                val context = view.context
                val intent = Intent(context, com.supdevinci.myapp.view.QuestionActivity::class.java)
                context.startActivity(intent)
            }, 1000)
        }

    }

    override fun getItemCount(): Int = categories.size
}
