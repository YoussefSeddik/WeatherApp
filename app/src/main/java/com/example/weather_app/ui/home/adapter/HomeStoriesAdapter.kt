package com.example.weather_app.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather_app.R
import com.example.weather_app.base.BaseViewHolder
import com.example.weather_app.databinding.ItemWeatherDetailsBinding
import com.example.weather_app.domain.models.WeatherStoryModel
import com.example.weather_app.utils.loadCircularImage
import com.example.weather_app.utils.loadImage

interface HomeStoriesListener {
    fun onShareStoryWithFacebookClicked(imagePath: String)
    fun onShareStoryWithTwitterClicked(imagePath: String)
    fun onDeleteStoryClicked(storyId: String)
    fun onItemStoryClicked(storyModel: WeatherStoryModel)

}

class HomeStoriesAdapter(val listener: HomeStoriesListener) :
    RecyclerView.Adapter<BaseViewHolder>() {
    var weatherStories = mutableListOf<WeatherStoryModel>()

    inner class ItemWeatherViewHolder(itemView: View) : BaseViewHolder(itemView) {
        var itemWeatherBinding = ItemWeatherDetailsBinding.bind(itemView)

        init {
            itemWeatherBinding.facebookShareImageView.setOnClickListener {
                listener.onShareStoryWithFacebookClicked(weatherStories[adapterPosition].thumbnailPath)
            }
            itemWeatherBinding.twitterShareImageView.setOnClickListener {
                listener.onShareStoryWithTwitterClicked(weatherStories[adapterPosition].thumbnailPath)
            }
            itemWeatherBinding.deleteStatusImageView.setOnClickListener {
                listener.onDeleteStoryClicked(weatherStories[adapterPosition].storyId)
            }
            itemWeatherBinding.weatherFrameLayout.setOnClickListener {
                listener.onItemStoryClicked(weatherStories[adapterPosition])
            }
        }

        fun bindView(weatherStoryModel: WeatherStoryModel) {
            itemWeatherBinding.weatherThumbnailImageView.loadImage(weatherStoryModel.thumbnailPath)
            itemWeatherBinding.tempTextView.text = "${weatherStoryModel.temp} °C"
            itemWeatherBinding.weatherDescTextView.text = weatherStoryModel.tempDescription
            itemWeatherBinding.updatedAtTextView.text = weatherStoryModel.updatedAt
            itemWeatherBinding.locationTextView.text = weatherStoryModel.location
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return ItemWeatherViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_weather_details, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is ItemWeatherViewHolder -> {
                holder.bindView(weatherStories[position])
            }
        }
    }

    override fun getItemCount() = weatherStories.size

    fun swapData(newList: MutableList<WeatherStoryModel>) {
        weatherStories = newList
        notifyDataSetChanged()
    }

    fun addData(weatherStoryModel: WeatherStoryModel) {
        weatherStories.add(weatherStoryModel)
        notifyItemInserted(weatherStories.size)
    }

    fun removeStory(storyId: String) {
        val itemPositionToDelete = weatherStories.indexOfFirst { it.storyId == storyId }
        weatherStories.removeAt(itemPositionToDelete)
        notifyItemRemoved(itemPositionToDelete)
    }
}