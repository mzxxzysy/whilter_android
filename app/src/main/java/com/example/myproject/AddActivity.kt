package com.example.myproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myproject.databinding.ActivityAddBinding
import java.text.SimpleDateFormat
import java.util.Date

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding

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
        val content = binding.addEditView.text.toString().trim()

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
            "date" to dateFormat.format(Date())
        )

        Log.d("AddActivity", "Attempting to save data: $data")

        MyApplication.db.collection("review")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d("AddActivity", "Document added with ID: ${documentReference.id}")
                Toast.makeText(this, "저장되었습니다.", Toast.LENGTH_SHORT).show()

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
}