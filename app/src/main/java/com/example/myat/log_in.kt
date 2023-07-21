package com.example.myat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class log_in:AppCompatActivity() {

    var login: Button? = null
    var user: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)

        login = findViewById(R.id.lgn_btn)
        user = findViewById(R.id.new_user_lgn)

        login!!.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        user!!.setOnClickListener{
            val intent = Intent(this,sign_up::class.java)
            startActivity(intent)
        }
    }
}