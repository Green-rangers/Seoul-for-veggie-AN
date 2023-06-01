package com.greenranger.seoulforveggi.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.data.model.response.MyReviewReponse

class MyreviewViewAdapter(
    private val clickListener: (MyReviewReponse.Restaurant) -> Unit
) : RecyclerView.Adapter<MyreviewViewAdapter.MyViewHolder>() {
    private val restaurants: MutableList<MyReviewReponse.Restaurant> = mutableListOf()

    fun updateRestaurants(newPlaces: List<MyReviewReponse.Restaurant>) {
        restaurants.clear()
        restaurants.addAll(newPlaces)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyreviewViewAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.my_review_item, parent, false)
        return MyViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant, clickListener)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.tv_name)
        private val category: TextView = view.findViewById(R.id.tv_category)
        private val content: TextView = view.findViewById(R.id.tv_content)
        private val imageLink: ImageView = view.findViewById(R.id.restaurant_image)
        private val star1: ImageView = view.findViewById(R.id.star1)
        private val star2: ImageView = view.findViewById(R.id.star2)
        private val star3: ImageView = view.findViewById(R.id.star3)
        private val star4: ImageView = view.findViewById(R.id.star4)
        private val star5: ImageView = view.findViewById(R.id.star5)


        fun bind(restaurant: MyReviewReponse.Restaurant, clickListener: (MyReviewReponse.Restaurant) -> Unit) {
            // UI elements setup

            name.text = restaurant.name ?: ""
            category.text = restaurant.category ?: ""
            val formattedContent = restaurant.content.chunked(40).joinToString("\n")
            content.text = formattedContent

            if (!restaurant.imageLink.isNullOrEmpty()) {
                Glide.with(view)
                    .load(restaurant.imageLink)
                    .into(imageLink)
            }

            if(restaurant.rating.toInt() == 1){
                star1.setImageResource(R.drawable.ic_star_on)
            }else if(restaurant.rating.toInt() == 2){
                star1.setImageResource(R.drawable.ic_star_on)
                star2.setImageResource(R.drawable.ic_star_on)
            }else if(restaurant.rating.toInt() == 3){
                star1.setImageResource(R.drawable.ic_star_on)
                star2.setImageResource(R.drawable.ic_star_on)
                star3.setImageResource(R.drawable.ic_star_on)
            }else if(restaurant.rating.toInt() == 4){
                star1.setImageResource(R.drawable.ic_star_on)
                star2.setImageResource(R.drawable.ic_star_on)
                star3.setImageResource(R.drawable.ic_star_on)
                star4.setImageResource(R.drawable.ic_star_on)
            } else if(restaurant.rating.toInt() == 5){
                star1.setImageResource(R.drawable.ic_star_on)
                star2.setImageResource(R.drawable.ic_star_on)
                star3.setImageResource(R.drawable.ic_star_on)
                star4.setImageResource(R.drawable.ic_star_on)
                star5.setImageResource(R.drawable.ic_star_on)
            } else {
                // 0점일때
            }


            view.setOnClickListener {
                clickListener(restaurant)
            }
        }
    }
}