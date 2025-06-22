package com.example.myproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.databinding.ItemStationBinding

class LiftViewHolder(val binding: ItemStationBinding): RecyclerView.ViewHolder(binding.root)

class LiftAdapter(private val datas: MutableList<LiftInfo>): RecyclerView.Adapter<LiftViewHolder>() {

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiftViewHolder {
        val binding = ItemStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LiftViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LiftViewHolder, position: Int) {
        val lift = datas[position]

        holder.binding.apply {
            station.text = lift.stationName
            num.text = lift.line
            location.text = lift.location
        }
    }
}