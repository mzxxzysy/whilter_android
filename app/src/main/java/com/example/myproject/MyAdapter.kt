package com.example.myproject

import android.content.SharedPreferences
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myproject.databinding.ItemTwoBinding

/**
 * A simple [Fragment] subclass.
 * Use the [OneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class MyViewHolder(val binding: ItemTwoBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(
    private val datas: MutableList<ItemTwo>,
    private val fontSize: Int
) : RecyclerView.Adapter<MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(ItemTwoBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding

        val model = datas!![position]

        binding.itemEmailView.text = model.email
        binding.itemDateView.text = model.date
        binding.itemContentView.text = model.content
        binding.itemTitleView.text = model.title

        binding.itemTitleView.textSize = (fontSize + 5).toFloat()
        binding.itemContentView.textSize = fontSize.toFloat()
        binding.itemEmailView.textSize = (fontSize - 7).toFloat()
        binding.itemDateView.textSize = (fontSize - 7).toFloat()
    }

}