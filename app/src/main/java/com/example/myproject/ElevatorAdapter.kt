package com.example.myproject

import android.content.SharedPreferences
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.databinding.ItemStationBinding

class ElevatorViewHolder(val binding: ItemStationBinding): RecyclerView.ViewHolder(binding.root)

class ElevatorAdapter(private val datas: MutableList<ElevatorInfo>): RecyclerView.Adapter<ElevatorViewHolder>() {

    lateinit var sharedPreferences: SharedPreferences
    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElevatorViewHolder {
        val binding = ItemStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ElevatorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ElevatorViewHolder, position: Int) {
        val elevator = datas[position]
        val binding = holder.binding

        binding.station.text = elevator.stationName
        binding.num.text = elevator.line
        binding.location.text = elevator.location

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