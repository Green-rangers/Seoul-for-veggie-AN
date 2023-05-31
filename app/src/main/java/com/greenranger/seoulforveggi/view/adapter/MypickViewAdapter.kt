package com.greenranger.seoulforveggi.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.data.model.response.MyPickResponse

class MypickViewAdapter(
    private val clickListener: (MyPickResponse.Restaurant) -> Unit
) : RecyclerView.Adapter<MypickViewAdapter.MyViewHolder>() {
    private val restaurants: MutableList<MyPickResponse.Restaurant> = mutableListOf()

    fun updateRestaurants(newPlaces: List<MyPickResponse.Restaurant>) {
        restaurants.clear()
        restaurants.addAll(newPlaces)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MypickViewAdapter.MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.review_item, parent, false)
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
        private val address: TextView = view.findViewById(R.id.tv_address)
        private val imageLink: ImageView = view.findViewById(R.id.restaurant_image)


        fun bind(restaurant: MyPickResponse.Restaurant, clickListener: (MyPickResponse.Restaurant) -> Unit) {
            // UI elements setup

            name.text = restaurant.name ?: ""
            category.text = restaurant.category ?: ""
            val formattedContent = restaurant.address.chunked(30).joinToString("\n")
            address.text = formattedContent

            if (!restaurant.imageLink.isNullOrEmpty()) {
                Glide.with(view)
                    .load(restaurant.imageLink)
                    .into(imageLink)
            }


            view.setOnClickListener {
                clickListener(restaurant)
            }
        }
    }
}