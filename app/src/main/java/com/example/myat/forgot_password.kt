package com.example.myat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class forgot_password:AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var edtxt: EditText
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password)

        auth = FirebaseAuth.getInstance()

        btn = findViewById(R.id.btnsmt)

        btn.setOnClickListener {
            reset_password()
            val intent = Intent(this,log_in::class.java)
            startActivity(intent)
        }
    }

        private fun reset_password(){

            edtxt = findViewById(R.id.t_mail)

            val n_passwrd = edtxt.text.toString()

            auth.sendPasswordResetEmail(n_passwrd)
                .addOnSuccessListener{
                    Toast.makeText(this,"Please Check Your Email Spam Folder.",Toast.LENGTH_SHORT).show()

            }

                .addOnFailureListener {
                    Toast.makeText(this,it.toString(),Toast.LENGTH_SHORT).show()
                }

        }

}