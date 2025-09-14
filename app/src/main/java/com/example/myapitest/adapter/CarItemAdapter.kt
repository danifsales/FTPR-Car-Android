package com.example.myapitest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapitest.R
import com.example.myapitest.model.Car
import com.example.myapitest.ui.loadUrl

class CarItemAdapter(
    private val cars: List<Car>,
    private val onCarClick: (Car) -> Unit
) : RecyclerView.Adapter<CarItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
        val model: TextView = view.findViewById(R.id.model)
        val year: TextView = view.findViewById(R.id.year)
        val license: TextView = view.findViewById(R.id.license)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_car_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount() = cars.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val car = cars[position]
        holder.itemView.setOnClickListener { onCarClick(car) }
        holder.image.loadUrl(car.imageUrl)
        holder.model.text = car.name
        holder.year.text = car.year
        holder.license.text = car.licence
    }
}
