package com.example.myat.fragments
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myat.R
import com.example.myat.log_in
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class EditFragment : AppCompatActivity() {


    val db = Firebase.firestore
    val user_id = FirebaseAuth.getInstance().currentUser!!.uid
    val us = Firebase.auth.currentUser


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_edit)

        val mbl = findViewById<EditText>(R.id.et_mobile)
        val ml = findViewById<EditText>(R.id.et_mail)
        val des = findViewById<EditText>(R.id.et_designtn)
        val nam = findViewById<EditText>(R.id.et_name)
        val updt = findViewById<Button>(R.id.btnupdate)
        val eid = findViewById<TextView>(R.id.et_id)

        val ref = db.collection("users").document(user_id)

        eid.setOnClickListener {
            Toast.makeText(this,"Employee ID can't be changed",Toast.LENGTH_SHORT).show()
        }

        updt.setOnClickListener {
            Update(nam.text.toString(), mbl.text.toString(),ml.text.toString(),des.text.toString())
        }


        ref.get().addOnSuccessListener {
            if (it != null) {
                val employee_id = it.data?.get("emp_id")?.toString()
                eid.text = employee_id
            }
        }
                .addOnFailureListener {
                    Toast.makeText(this, "Error Loading Employee ID", Toast.LENGTH_SHORT).show()
                }

        }

    private fun Update( nam:String, mbl:String, ml:String, des:String){

        try {

            val enam = nam
            val embl = mbl
            val eml = ml
            val edes = des

            val updateMap = mapOf(
                "name" to enam,
                "mobile" to embl,
                "email" to eml,
                "designation" to edes
            )
            val db = Firebase.firestore
            val user_id = FirebaseAuth.getInstance().currentUser!!.uid
            us?.updateEmail(eml)?.addOnSuccessListener {
                Log.e("Email","Email is: $eml")
            }
            db.collection("users").document(user_id).update(updateMap)
            var intent = Intent(this,log_in::class.java)
            startActivity(intent)
            finish()
            Toast.makeText(this,"Successfully Edited the Information",Toast.LENGTH_SHORT).show()

        }catch (e:Exception){
            Log.e("Error:",e.toString())
        }

    }


     /*   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }*/

}