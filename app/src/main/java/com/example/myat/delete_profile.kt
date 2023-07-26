package com.example.myat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class delete_profile:AppCompatActivity() {

    private val db = Firebase.firestore
    private lateinit var builder: AlertDialog.Builder
    val user_id = FirebaseAuth.getInstance().currentUser!!.uid
    val ref = db.collection("users").document(user_id)
    val us = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.delete_account)

        val del_acc = findViewById<Button>(R.id.btndelete)

        del_acc.setOnClickListener {
            alert()
        }

    }

    private fun delete_account() {
        us?.delete()?.addOnSuccessListener {
            //Toast.makeText(this,"Authetication ID Deleted.",Toast.LENGTH_SHORT).show()
        }
        ref.delete().addOnSuccessListener {
            Toast.makeText(this,"Profile deleted Successfully.",Toast.LENGTH_SHORT).show()
            var intent = Intent(this, log_in::class.java)
            startActivity(intent)
        }
            .addOnFailureListener {
                Toast.makeText(this,"Profile deletion Failed.",Toast.LENGTH_SHORT).show()

            }
    }
    private fun alert(){
        builder = AlertDialog.Builder(this)
        builder.setTitle("Delete?").
        setMessage("Are You Sure, You Want To delete the Profile...")
            .setCancelable(true)
            .setPositiveButton("Yes"){
                dailogInterface, it->
                delete_account()
            }
            .setNegativeButton("No"){
                dailogInterface, it->
                dailogInterface.cancel()
            }
            .show()
    }
}