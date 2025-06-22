package com.example.myproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproject.databinding.FragmentElevatorBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ElevatorFragment : Fragment() {
    private var _binding: FragmentElevatorBinding? = null
    private val binding get() = _binding!!

    private lateinit var elevatorAdapter: ElevatorAdapter
    private val elevatorList = mutableListOf<ElevatorInfo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentElevatorBinding.inflate(inflater, container, false)
        setupRecyclerView()
        loadElevatorData()
        return binding.root
    }

    private fun setupRecyclerView() {
        elevatorAdapter = ElevatorAdapter(elevatorList)
        binding.elevatorRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = elevatorAdapter
        }
    }

    private fun loadElevatorData() {
        val call = RetrofitConnection.jsonNetworkService.getElevatorList(
            1, 900, "uILS6e2OzD9dWBHBQjww27shkb3lYXFQRVRyhHzibSH+VHKa63b+pXduMj/neEe3SFv17VmJg2teyWSWzPvWrw==", "json"
        )

        call.enqueue(object : Callback<ElevatorResponse> {
            override fun onResponse(
                call: Call<ElevatorResponse>,
                response: Response<ElevatorResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { elevatorResponse ->
                        elevatorList.clear()
                        elevatorList.addAll(elevatorResponse.data)
                        elevatorAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<ElevatorResponse>, t: Throwable) {
                Log.e("TAG", "${t.message}", t)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
