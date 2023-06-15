package com.hera.giziwise.home.artikel

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hera.giziwise.R
import com.bumptech.glide.Glide

class ArticleAdapter(private val articleClickListener: ArticleClickListener) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private val articles: MutableList<Article> = mutableListOf()

    fun updateData(newArticles: List<Article>) {
        articles.clear()
        articles.addAll(newArticles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article_kesehatan, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
        holder.itemView.setOnClickListener {
            articleClickListener.onArticleClick(article)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val articleImage: ImageView = itemView.findViewById(R.id.article_image)
        private val articleTitle: TextView = itemView.findViewById(R.id.article_title)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val article = articles[position]
                    articleClickListener.onArticleClick(article)
                }
            }
        }

        fun bind(article: Article) {
            articleTitle.text = article.title
            articleTitle.maxLines = 2
            articleTitle.ellipsize = TextUtils.TruncateAt.END

            Glide.with(itemView)
                .load(article.image)
                .into(articleImage)
        }
    }

    interface ArticleClickListener {
        fun onArticleClick(article: Article)
    }
}







