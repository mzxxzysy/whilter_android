package com.example.myproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.databinding.ItemStationBinding

class ToiletViewHolder(val binding: ItemStationBinding): RecyclerView.ViewHolder(binding.root)

class ToiletAdapter(private val datas: MutableList<ToiletInfo>): RecyclerView.Adapter<ToiletViewHolder>() {

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToiletViewHolder {
        val binding = ItemStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToiletViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToiletViewHolder, position: Int) {
        val toilet = datas[position]

        holder.binding.apply {
            station.text = toilet.stationName
            num.text = toilet.line
            location.text = toilet.location
        }
    }
}