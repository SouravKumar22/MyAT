package com.example.myat.fragments
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditFragment : AppCompatActivity() {

    private val db = Firebase.firestore
    val user_id = FirebaseAuth.getInstance().currentUser!!.uid
    val nam = findViewById<EditText>(R.id.et_name)
    val mbl = findViewById<EditText>(R.id.et_mobile)
    val ml = findViewById<EditText>(R.id.et_mail)
    val des = findViewById<EditText>(R.id.et_designtn)
    val updt = findViewById<Button>(R.id.btnupdate)
    val eid = findViewById<TextView>(R.id.et_id)
    val ref = db.collection("users").document(user_id)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_edit)
        eid.setOnClickListener {
            Toast.makeText(this,"Employee ID can't be changed",Toast.LENGTH_SHORT).show()
        }

        updt.setOnClickListener {
            Udpate()
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

    private fun Udpate(){


        val enam = nam.text.toString()
        val embl = mbl.text.toString()
        val eml = ml.text.toString()
        val edes = des.text.toString()

        val updateMap = mapOf(
            "name" to enam,
            "mobile" to embl,
            "email" to eml,
            "designation" to edes
        )
        db.collection("users").document(user_id).update(updateMap)

        Toast.makeText(this,"Successfully Edited the Information",Toast.LENGTH_SHORT).show()

    }


     /*   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }*/

}