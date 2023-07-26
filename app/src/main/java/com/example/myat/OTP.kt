package com.example.myat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import org.checkerframework.checker.units.qual.s
import java.util.Arrays.toString
import java.util.Objects.toString

class OTP : AppCompatActivity() {

    val bs = findViewById<Button>(R.id.btnsmt)
    val dn = findViewById<TextView>(R.id.dispn)
    val rt = findViewById<TextView>(R.id.Reotp)
    val t1 = findViewById<EditText>(R.id.otp1)
    val t2 = findViewById<EditText>(R.id.otp2)
    val t3 = findViewById<EditText>(R.id.otp3)
    val t4 = findViewById<EditText>(R.id.otp4)
    val t5 = findViewById<EditText>(R.id.otp5)
    val t6 = findViewById<EditText>(R.id.otp6)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        dn.setText(String.format(
            "%s",intent.getStringExtra("mobile")
        ))

        bs.setOnClickListener {

        }

    }
    private fun validate(){
        if(!t1.text.toString().trim().isEmpty() && !t2.text.toString().trim().isEmpty() &&
            !t3.text.toString().trim().isEmpty() && !t4.text.toString().trim().isEmpty() &&
            !t5.text.toString().trim().isEmpty() && !t6.text.toString().trim().isEmpty()){
            Toast.makeText(this,"OTP Verified.",Toast.LENGTH_SHORT).show()

        }
        else{
            Toast.makeText(this,"Please Fill All Blanks.",Toast.LENGTH_SHORT).show()

        }
    }

    private fun move() {
        t1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used in this case
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) {
                    t2.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Not used in this case
            }
        })

        t2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used in this case
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) {
                    t3.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Not used in this case
            }
        })

        t3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used in this case
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) {
                    t4.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Not used in this case
            }
        })

        t4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used in this case
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) {
                    t5.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Not used in this case
            }
        })

        t5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used in this case
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) {
                    t6.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Not used in this case
            }
        })
    }
}