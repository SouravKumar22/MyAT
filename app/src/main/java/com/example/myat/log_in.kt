package com.example.myat

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class log_in:AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)

        auth = Firebase.auth

        var log_in = findViewById<Button>(R.id.btnlogin)
        var sgn_up = findViewById<TextView>(R.id.textViewSignUp)

        log_in.setOnClickListener {
            loginUser()


        }

        sgn_up.setOnClickListener {
            var intent = Intent(this, sign_up::class.java)
            startActivity(intent)
        }

    }

    private fun loginUser() {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val empID = findViewById<EditText>(R.id.input_emp_id).text.toString()
        val password = findViewById<EditText>(R.id.inputPassword).text.toString()
        // Query Firestore to get the user's email associated with the employee ID
        db.collection("users")
            .whereEqualTo("emp_id", empID)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val userDocument = querySnapshot.documents[0]
                    val userEmail = userDocument.getString("email")

                    if (userEmail != null) {
                        // Authenticate the user with the retrieved email and the password entered
                        auth.signInWithEmailAndPassword(userEmail, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    // Login success, user is authenticated
                                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_LONG).show()
                                    var intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    // Redirect to the home screen or perform other actions
                                } else {
                                    // Login failed, handle the error
                                    Toast.makeText(this, "Login Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                    } else {
                        // Error: User email not found in Firestore
                        Toast.makeText(this, "Employee ID not found!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    // Error: Employee ID not found in Firestore
                    Toast.makeText(this, "Employee ID not found!", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { e ->
                // Error: Failed to query Firestore
                Toast.makeText(this, "Failed to query Firestore: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
