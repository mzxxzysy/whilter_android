package com.example.myproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproject.databinding.FragmentToiletBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ToiletFragment : Fragment() {
    private var _binding: FragmentToiletBinding? = null
    private val binding get() = _binding!!

    private lateinit var toiletAdapter: ToiletAdapter
    private val toiletList = mutableListOf<ToiletInfo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentToiletBinding.inflate(inflater, container, false)
        setupRecyclerView()
        loadToiletData()
        return binding.root
    }

    private fun setupRecyclerView() {
        toiletAdapter = ToiletAdapter(toiletList){ toiletInfo ->
            val lat = toiletInfo.latitude.toDoubleOrNull()
            val lon = toiletInfo.longitude.toDoubleOrNull()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:"+lat+","+lon))
            startActivity(intent)
        }
        binding.toiletRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = toiletAdapter
        }
    }

    private fun loadToiletData() {
        val call = RetrofitConnection.jsonNetworkService.getToiletList(
            1, 100, "uILS6e2OzD9dWBHBQjww27shkb3lYXFQRVRyhHzibSH+VHKa63b+pXduMj/neEe3SFv17VmJg2teyWSWzPvWrw==", "json"
        )

        call.enqueue(object : Callback<ToiletResponse> {
            override fun onResponse(
                call: Call<ToiletResponse>,
                response: Response<ToiletResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { toiletResponse ->
                        toiletList.clear()
                        toiletList.addAll(toiletResponse.data)
                        toiletAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<ToiletResponse>, t: Throwable) {
                Log.e("TAG", "${t.message}", t)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
