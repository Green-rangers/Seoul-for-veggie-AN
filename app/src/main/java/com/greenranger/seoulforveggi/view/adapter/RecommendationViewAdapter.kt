package com.greenranger.seoulforveggi.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greenranger.seoulforveggi.R
import com.greenranger.seoulforveggi.data.model.response.RecommendationResponse

class RecommendationViewAdapter(
    private val clickListener: (RecommendationResponse.Restaurant) -> Unit
) : RecyclerView.Adapter<RecommendationViewAdapter.MyViewHolder>() {
    private val places: MutableList<RecommendationResponse.Restaurant> = mutableListOf()

    fun updatePlaces(newPlaces: List<RecommendationResponse.Restaurant>) {
        places.clear()
        places.addAll(newPlaces)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.recommendation_item, parent, false)
        return MyViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place, clickListener)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.tv_name)
        private val category: TextView = view.findViewById(R.id.tv_category)
        private val address: TextView = view.findViewById(R.id.tv_address)
        private val distance: TextView = view.findViewById(R.id.distance)
        private val placeImg: ImageView = view.findViewById(R.id.iv_restaurant_image)
        private val heart1: ImageView = view.findViewById(R.id.heart1)

        fun bind(place: RecommendationResponse.Restaurant, clickListener: (RecommendationResponse.Restaurant) -> Unit) {
            // UI elements setup
            name.text = place.name
            category.text = place.category
            address.text = place.address
            distance.text = place.distance.toString()

            if (place.imageLink.isNotEmpty()) {
                Glide.with(view)
                    .load(place.imageLink)
                    .into(placeImg)
            }

            if(place.isBookmarked) {
                heart1.setImageResource(R.drawable.ic_heart_on)
            } else {
                heart1.setImageResource(R.drawable.ic_heart)
            }


            view.setOnClickListener {
                clickListener(place)
            }
        }
    }
}