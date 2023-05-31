package com.greenranger.seoulforveggi.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.data.model.response.DetailResponse

class DetailViewAdapter(
    private val clickListener: (DetailResponse.Review) -> Unit
) : RecyclerView.Adapter<DetailViewAdapter.MyViewHolder>() {
    private val reviews: MutableList<DetailResponse.Review> = mutableListOf()

    fun updateReviews(newPlaces: List<DetailResponse.Review>) {
        reviews.clear()
        reviews.addAll(newPlaces)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.review_detail_item, parent, false)
        return MyViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = reviews[position]
        holder.bind(review, clickListener)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val writerName: TextView = view.findViewById(R.id.tv_nickname)
        private val content: TextView = view.findViewById(R.id.tv_content)
        private val writerImage: ImageView = view.findViewById(R.id.iv_profile)


        fun bind(review: DetailResponse.Review, clickListener: (DetailResponse.Review) -> Unit) {
            // UI elements setup
            writerName.text = review.writerName ?: ""
            val formattedContent = review.content.chunked(30).joinToString("\n")
            content.text = formattedContent

            if (!review.writerImage.isNullOrEmpty()) {
                Glide.with(view)
                    .load(review.writerImage)
                    .into(writerImage)
            }


            view.setOnClickListener {
                clickListener(review)
            }
        }
    }
}