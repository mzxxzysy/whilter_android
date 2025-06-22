package com.example.myproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproject.databinding.FragmentLiftBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LiftFragment : Fragment() {
    private var _binding: FragmentLiftBinding? = null
    private val binding get() = _binding!!

    private lateinit var liftAdapter: LiftAdapter
    private val liftList = mutableListOf<LiftInfo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLiftBinding.inflate(inflater, container, false)
        setupRecyclerView()
        loadLiftData()
        return binding.root
    }

    private fun setupRecyclerView() {
        liftAdapter = LiftAdapter(liftList)
        binding.liftRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = liftAdapter
        }
    }

    private fun loadLiftData() {
        val call = RetrofitConnection.jsonNetworkService.getLiftList(
            1, 100, "uILS6e2OzD9dWBHBQjww27shkb3lYXFQRVRyhHzibSH+VHKa63b+pXduMj/neEe3SFv17VmJg2teyWSWzPvWrw==", "json"
        )

        call.enqueue(object : Callback<LiftResponse> {
            override fun onResponse(
                call: Call<LiftResponse>,
                response: Response<LiftResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { liftResponse ->
                        liftList.clear()
                        liftList.addAll(liftResponse.data)
                        liftAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<LiftResponse>, t: Throwable) {
                Log.e("TAG", "${t.message}", t)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
