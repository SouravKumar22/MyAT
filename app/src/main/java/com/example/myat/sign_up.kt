package com.example.myat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class sign_up: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        auth = Firebase.auth

        var signUp = findViewById<Button>(R.id.sgn_up_btn)
        var alreadyRegistered = findViewById<TextView>(R.id.already_user_sgn_up)


        signUp.setOnClickListener{
            var intent = Intent(this,log_in::class.java)
            startActivity(intent)
        }
        alreadyRegistered.setOnClickListener{
            var intent = Intent(this,log_in::class.java)
            startActivity(intent)
        }

        registerUser()
    }

    private fun registerUser(){
        val emp_n = findViewById<EditText>(R.id.et_emp_id_sgn_up)
        val name = findViewById<EditText>(R.id.et_name_sgn_up)
        val psswrd = findViewById<EditText>(R.id.et_pass_sgn_up)
        val re_psswrd = findViewById<EditText>(R.id.et_pass_sgn_up1)
        val mbl_num = findViewById<EditText>(R.id.et_phn_sgn_up)
        val mail = findViewById<EditText>(R.id.et_mail_sgn_up)
        val desg = findViewById<EditText>(R.id.et_desgntn_sgn_up)

        val input_emp_id = emp_n.id
        val input_name = name.text.toString()
        val input_password = psswrd.text.toString()
        val input_re_password = re_psswrd.text.toString()
        val mobile = mbl_num.id
        val m = mail.text.toString()
        val designation = desg.text.toString()
    }

}
