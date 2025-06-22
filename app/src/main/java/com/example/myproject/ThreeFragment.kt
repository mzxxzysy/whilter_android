package com.example.myproject

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import com.example.myproject.databinding.FragmentThreeBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThreeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThreeFragment : Fragment() {
    lateinit var sharedPreferences: SharedPreferences
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentThreeBinding

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
        binding = FragmentThreeBinding.inflate(inflater, container, false)

        binding.setting.setOnClickListener {
            val intent = Intent(requireContext(), SettingActivity::class.java)
            startActivity(intent)
        }

        val status = binding.login
        status.setOnClickListener {
            val intent = Intent(requireContext(), AuthActivity::class.java)

            if (status.text.equals("로그인")) {
                intent.putExtra("status", "logout")
                binding.setting.visibility = View.VISIBLE
            } else if (status.text.equals("로그아웃")) {
                intent.putExtra("status", "login")
                binding.setting.visibility = View.GONE
            }

            startActivity(intent)
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ThreeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThreeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onStart() {
        super.onStart()

        val status = binding.login
        val user = binding.user

        if(MyApplication.checkAuth()) {
            status.text = "로그아웃"
            user.text = "${MyApplication.email}님\n반갑습니다"
            binding.setting.visibility = View.VISIBLE  // 로그인 상태일 때 설정 버튼 보이기
        } else {
            status.text = "로그인"
            user.text = "로그인을 해주세요"
            binding.setting.visibility = View.GONE     // 로그아웃 상태일 때 설정 버튼 숨기기
        }
    }

    override fun onResume() {
        super.onResume()

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val fontSize = sharedPreferences.getInt("fontSize", 16)
        binding.setting.textSize = fontSize + 1f
        binding.user.textSize = fontSize + 1f
        binding.login.textSize = fontSize + 1f

        val fontStyle = sharedPreferences.getString("fontStyle", "normal")

        val styleValue = when(fontStyle) {
            "bold" -> Typeface.BOLD
            else -> Typeface.NORMAL
        }

        binding.setting.setTypeface(null, styleValue)
        binding.user.setTypeface(null, styleValue)
        binding.login.setTypeface(null, styleValue)


        val idPre = sharedPreferences.getString ("nickname","")
        binding.user.text = idPre
    }
}
