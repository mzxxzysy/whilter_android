package com.example.myproject

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.myproject.databinding.ActivityAddBinding
import java.text.SimpleDateFormat
import java.util.Date

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.addToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val date = intent.getStringExtra("today")
        binding.date.text = date

        binding.btnSave.setOnClickListener {
            saveData()
        }
    }

    private fun saveData() {

        val title = binding.addTitle.text.toString().trim()
        val content = binding.addEditView.text.toString().trim()

        // 빈 내용 체크
        if (title.isEmpty()) {
            Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        // 빈 내용 체크
        if (content.isEmpty()) {
            Toast.makeText(this, "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        // 이메일 체크
        if (MyApplication.email.isNullOrEmpty()) {
            Toast.makeText(this, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val data = mapOf(
            "email" to MyApplication.email,
            "content" to content,
            "title" to title,
            "date" to dateFormat.format(Date())
        )

        Log.d("AddActivity", "Attempting to save data: $data")

        MyApplication.db.collection("review")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d("AddActivity", "Document added with ID: ${documentReference.id}")
                Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()
                val helper = MyNotificationHelper(this)
                helper.showNotification("firestore", "리뷰가 추가되었습니다.")

                // 저장 성공 후 액티비티 종료
                val intent = Intent()
                intent.putExtra("result", "saved")
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            .addOnFailureListener { exception ->
                Log.e("AddActivity", "Error adding document", exception)
                Toast.makeText(this, "저장 실패: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent()
        intent.putExtra("result", "cancelled")
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
        return true
    }

    override fun onResume() {
        super.onResume()

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val colorPre = sharedPreferences.getString("color", "#D9D9D9")
        binding.addToolbar.setBackgroundColor(Color.parseColor(colorPre))
        binding.btnSave.setBackgroundColor(Color.parseColor(colorPre))

        val fontSize = sharedPreferences.getInt("fontSize", 16)
        binding.btnSave.textSize = fontSize + 1f

        val fontStyle = sharedPreferences.getString("fontStyle", "normal")
        val styleValue = when(fontStyle) {
            "bold" -> Typeface.BOLD
            else -> Typeface.NORMAL
        }
        binding.btnSave.setTypeface(null, styleValue)
    }
}