package com.example.myproject

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyApplication : MultiDexApplication() {
    companion object{
        private var auth: FirebaseAuth? = null
        lateinit var db: FirebaseFirestore

        var email: String? = null

        fun getAuth(): FirebaseAuth {
            if (auth == null) {
                auth = FirebaseAuth.getInstance()
            }
            return auth!!
        }

        fun checkAuth(): Boolean {
            return try {
                val currentUser = getAuth().currentUser
                currentUser?.let {
                    email = currentUser.email
                    if(currentUser.isEmailVerified) {
                        true
                    } else {
                        false
                    }
                } ?: false
            } catch (e: Exception) {
                false
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        // Firebase Auth 초기화
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
    }
}