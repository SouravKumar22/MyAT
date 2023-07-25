package com.example.myat

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class delete_profile:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_account)

        val del_acc = findViewById<Button>(R.id.btndelete)

        del_acc.setOnClickListener {
            delete_account()
        }
    }

    private fun delete_account() {

    }
}