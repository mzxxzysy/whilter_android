package com.example.myproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.databinding.ItemStationBinding

class ElevatorViewHolder(val binding: ItemStationBinding): RecyclerView.ViewHolder(binding.root)

class ElevatorAdapter(private val datas: MutableList<ElevatorInfo>): RecyclerView.Adapter<ElevatorViewHolder>() {

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElevatorViewHolder {
        val binding = ItemStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ElevatorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ElevatorViewHolder, position: Int) {
        val elevator = datas[position]

        holder.binding.apply {
            station.text = elevator.stationName
            num.text = elevator.line
            location.text = elevator.location
        }
    }
}