package com.example.myproject

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.preference.PreferenceManager
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
        val binding = holder.binding

        binding.station.text = lift.stationName
        binding.num.text = lift.line
        binding.location.text = lift.location

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(binding.root.context)

        val fontSize = sharedPreferences.getInt("fontSize", 16)
        val fontStyle = sharedPreferences.getString("fontStyle", "normal")
        val styleValue = when (fontStyle) {
            "bold" -> Typeface.BOLD
            else -> Typeface.NORMAL
        }

        holder.binding.station.textSize = fontSize + 1f
        binding.num.textSize = fontSize + 1f
        binding.location.textSize = fontSize + 1f

        binding.station.setTypeface(null, styleValue)
        binding.num.setTypeface(null, styleValue)
        binding.location.setTypeface(null, styleValue)
    }
}