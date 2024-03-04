package com.raven.home.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.raven.home.R
import com.raven.home.presentation.model.Article
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class ArticleAdapter(val onClick: (Article) -> Unit) :
    ListAdapter<Article, ArticleAdapter.ViewHolder>(callback) {

    companion object {
        val callback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Article, newItem: Article) =
                oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)

        fun bind(article: Article) {
            titleTextView.text = article.title
            dateTextView.text = article.date
            if (article.media.isNotEmpty()) Picasso
                .get()
                .load(article.media)
                .placeholder(R.drawable.nyt_icon)
                .error(R.drawable.nyt_icon)
                .into(imageView)

            itemView.setOnClickListener {
                onClick(article)
            }
        }
    }
}