package com.example.myat
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class sign_up: AppCompatActivity() {
    var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        auth = Firebase.auth

        var signUp = findViewById<Button>(R.id.btnRegister)
        var alreadyRegistered = findViewById<TextView>(R.id.alreadyHaveAccount)


        signUp.setOnClickListener {
            registerUser()

        }
        alreadyRegistered.setOnClickListener {
            var intent = Intent(this, log_in::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser() {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()


        val emp_n = findViewById<EditText>(R.id.input_emp_id)
        val name = findViewById<EditText>(R.id.inputname)
        val psswrd = findViewById<EditText>(R.id.inputPassword)
        val re_psswrd = findViewById<EditText>(R.id.inputConfirmPassword)
        val mbl_num = findViewById<EditText>(R.id.input_number)
        val mail = findViewById<EditText>(R.id.inputEmail)
        val desg = findViewById<EditText>(R.id.input_designation)

        val input_emp_id = emp_n.text.toString()
        val input_name = name.text.toString()
        val input_password = psswrd.text.toString()
        val input_re_password = re_psswrd.text.toString()
        val mobile = mbl_num.text.toString()
        val m = mail.text.toString()
        val designation = desg.text.toString()



        if (input_password == input_re_password) {
            auth.createUserWithEmailAndPassword(m, input_password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        //After Authentication adding user details to database
                        val user = auth.currentUser
                        if (user != null) {
                            // User registration successful, save employee details to Firestore
                            val userDetails = hashMapOf(
                                "emp_id" to input_emp_id,
                                "name" to input_name,
                                "email" to m,
                                "mobile" to mobile,
                                "designation" to designation
                            )
                            db.collection("users").document(user.uid).set(userDetails)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this,
                                        "User registered Successfully!",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    var intent = Intent(this, log_in::class.java)
                                    startActivity(intent)
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(
                                        this,
                                        "Failed to store user details",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Failed to Register: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
        }
    }
}

