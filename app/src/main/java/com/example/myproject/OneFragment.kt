package com.example.myproject

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myproject.databinding.FragmentOneBinding
import com.google.android.material.tabs.TabLayoutMediator


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OneFragment : Fragment() {
    private var _binding: FragmentOneBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPreferences: SharedPreferences

    // Fragment 내부에서 child fragment를 사용할 때는 Fragment를 매개변수로 받아야 합니다
    class MyFragmentPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment){

        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> ElevatorFragment()
                1 -> LiftFragment()
                2 -> ToiletFragment()
                else -> ElevatorFragment()
            }
        }
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        _binding = FragmentOneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("OneFragment", "onViewCreated 호출됨")

        // Fragment 내부에서 child fragment를 사용할 때는 this를 전달
        val adapter = MyFragmentPagerAdapter(this)
        binding.oneViewpager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.oneViewpager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "엘리베이터"
                    Log.d("OneFragment", "엘리베이터 탭 설정")
                }
                1 -> {
                    tab.text = "리프트"
                    Log.d("OneFragment", "리프트 탭 설정")
                }
                2 -> {
                    tab.text = "장애인 화장실"
                    Log.d("OneFragment", "화장실 탭 설정")
                }
            }
        }.attach()

        Log.d("OneFragment", "ViewPager와 TabLayout 설정 완료")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OneFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OneFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}