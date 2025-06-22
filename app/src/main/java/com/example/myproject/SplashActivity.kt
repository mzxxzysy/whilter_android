package com.example.myproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_anim)
        val translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_anim)

        scaleAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                imageView.startAnimation(scaleAnimation)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        translateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })

        imageView.startAnimation(scaleAnimation)
        imageView.startAnimation(translateAnimation)

        val backgroundExecutor: ScheduledExecutorService =
            Executors.newSingleThreadScheduledExecutor()
        val mainExecutor: Executor = ContextCompat.getMainExecutor(this)
        backgroundExecutor.schedule (
            {
                mainExecutor.execute {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }, 3, TimeUnit.SECONDS)
        }
    }
