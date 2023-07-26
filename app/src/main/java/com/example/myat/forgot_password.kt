package com.example.myat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class forgot_password:AppCompatActivity() {

    val edtxt = findViewById<EditText>(R.id.t_number)
    val btn = findViewById<Button>(R.id.btnotp)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password)

        btn.setOnClickListener {
            if (!edtxt.text.toString().trim().isEmpty()) {
                if (edtxt.text.toString().trim().length == 10) {
                    val intent = Intent(this, OTP::class.java)
                    intent.putExtra("mobile", edtxt.text.toString())
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Please Enter Correct Number", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show()
            }
        }
    }
}