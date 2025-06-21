package com.example.myproject

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myproject.databinding.ActivityAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 인증 상태 확인 및 UI 설정
        if(MyApplication.checkAuth()){
            changeVisibility("login")
        } else{
            changeVisibility("logout")
        }

        binding.logoutBtn.setOnClickListener {
            // 로그아웃 처리
            MyApplication.getAuth().signOut()
            MyApplication.email = null
            changeVisibility("logout")
            Toast.makeText(this, "로그아웃되었습니다.", Toast.LENGTH_SHORT).show()
        }

        binding.goSignInBtn.setOnClickListener{
            changeVisibility("signin")
        }

        val requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            //구글 로그인 결과 처리...........................
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try{
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                MyApplication.getAuth().signInWithCredential(credential)
                    .addOnCompleteListener(this){task ->
                        if(task.isSuccessful){ //login & credential 성공
                            MyApplication.email = account.email
//                            changeVisibility("login")
                            finish()
                        }else {
                            changeVisibility("logout")
                        }
                    }
            }catch (e: ApiException){
                changeVisibility("logout")
            }
        }

        binding.googleLoginBtn.setOnClickListener {
            //구글 로그인....................
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
            requestLauncher.launch(signInIntent)
        }

        binding.signBtn.setOnClickListener {
            // 이메일, 비밀번호 회원가입
            val email = binding.authEmailEditView.text.toString().trim()
            val password = binding.authPasswordEditView.text.toString().trim()

            if(email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(password.length < 6) {
                Toast.makeText(this, "비밀번호는 6자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            MyApplication.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                    if(task.isSuccessful){
                        // 회원가입 성공 - 이메일 인증 전송
                        MyApplication.getAuth().currentUser?.sendEmailVerification()
                            ?.addOnCompleteListener { sendTask ->
                                if(sendTask.isSuccessful){
                                    Toast.makeText(baseContext, "회원가입에 성공하였습니다. 전송된 메일을 확인해 주세요", Toast.LENGTH_SHORT).show()
                                    changeVisibility("logout")
                                } else {
                                    Toast.makeText(baseContext, "메일 전송 실패", Toast.LENGTH_SHORT).show()
                                    changeVisibility("logout")
                                }
                            }
                    } else {
                        Toast.makeText(baseContext, "회원 가입 실패: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        changeVisibility("logout")
                    }
                }
        }

        binding.loginBtn.setOnClickListener {
            //이메일, 비밀번호 로그인.......................
            val email = binding.authEmailEditView.text.toString()
            val password = binding.authPasswordEditView.text.toString()
            MyApplication.getAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task ->
                    binding.authEmailEditView.text.clear()
                    binding.authPasswordEditView.text.clear()
                    if(task.isSuccessful){ //signin 성공
                        if(MyApplication.checkAuth()){
                            MyApplication.email = email
//                            changeVisibility("login")
                            finish()
                        }else {
                            Toast.makeText(baseContext, "전송된 메일로 이메일 인증이 되지 않았습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()

                    }
                }
        }
    }


    fun changeVisibility(mode: String){
        when(mode) {
            "login" -> {
                binding.run {
                    authMainTextView.text = "반갑습니다."
                    logoutBtn.visibility = View.VISIBLE
                    goSignInBtn.visibility = View.GONE
                    googleLoginBtn.visibility = View.GONE
                    authEmailEditView.visibility = View.GONE
                    authPasswordEditView.visibility = View.GONE
                    signBtn.visibility = View.GONE
                    loginBtn.visibility = View.GONE
                }
            }
            "logout" -> {
                binding.run {
                    authMainTextView.text = "로그인 하거나 회원가입 해주세요."
                    logoutBtn.visibility = View.GONE
                    goSignInBtn.visibility = View.VISIBLE
                    googleLoginBtn.visibility = View.VISIBLE
                    authEmailEditView.visibility = View.VISIBLE
                    authPasswordEditView.visibility = View.VISIBLE
                    signBtn.visibility = View.GONE
                    loginBtn.visibility = View.VISIBLE
                }
            }
            "signin" -> {
                binding.run {
                    authMainTextView.text = "회원가입을 진행해주세요."
                    logoutBtn.visibility = View.GONE
                    goSignInBtn.visibility = View.GONE
                    googleLoginBtn.visibility = View.GONE
                    authEmailEditView.visibility = View.VISIBLE
                    authPasswordEditView.visibility = View.VISIBLE
                    signBtn.visibility = View.VISIBLE
                    loginBtn.visibility = View.GONE
                }
            }
        }
    }
}