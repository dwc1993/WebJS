package com.example.administrator.webjsapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.loadUrl ->
                startActivity(Intent(this, MainActivity::class.java))

            R.id.jsInterface ->
                startActivity(Intent(this, MainActivity2::class.java))

            R.id.shouldOverrideUrlLoading ->
                startActivity(Intent(this, MainActivity3::class.java))

            R.id.onJsPrompt ->
                startActivity(Intent(this, MainActivity4::class.java))

        }

    }
}
