package com.example.myproject

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.databinding.ItemStationBinding

class ToiletViewHolder(val binding: ItemStationBinding): RecyclerView.ViewHolder(binding.root)

class ToiletAdapter(
    private val datas: MutableList<ToiletInfo>,
    private val onItemClick: (ToiletInfo) -> Unit
) : RecyclerView.Adapter<ToiletViewHolder>() {

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToiletViewHolder {
        val binding = ItemStationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToiletViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToiletViewHolder, position: Int) {
        val toilet = datas[position]
        val binding = holder.binding

        binding.station.text = toilet.stationName
        binding.num.text = toilet.line
        binding.location.text = toilet.location

        binding.root.setOnClickListener {
            onItemClick(toilet)
        }

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