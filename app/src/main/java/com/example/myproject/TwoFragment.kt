package com.example.myproject

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproject.databinding.FragmentTwoBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class TwoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentTwoBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTwoBinding.inflate(inflater, container, false)

//        Firebase.messaging.token.addOnSuccessListener {
//            Log.d("TAG", it)
//        }


        binding.addFab.setOnClickListener{
            if(MyApplication.checkAuth()){
                val intent = Intent(requireContext(), AddActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(requireContext(), "로그인 후 이용 가능한 서비스입니다.",Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if(MyApplication.checkAuth()) {
            binding.mainRecyclerView.visibility = View.VISIBLE

            MyApplication.db.collection("review")
                .get()
                .addOnSuccessListener { result ->
                    var itemList = mutableListOf<ItemTwo>()
                    for( document in result){
                        val item = document.toObject(ItemTwo::class.java)
                        item.docId = document.id
                        itemList.add(item)
                    }
                    binding.mainRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    binding.mainRecyclerView.adapter = MyAdapter(itemList)
                }
                .addOnFailureListener{
                    Toast.makeText(requireContext(), "데이터 획득 실패", Toast.LENGTH_SHORT).show()
                }
        } else {
            binding.mainRecyclerView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TwoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}