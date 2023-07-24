package com.example.myat

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class log_in:AppCompatActivity() {

    companion object {
        // Replace these values with the latitude and longitude of your primary location
        private const val PRIMARY_LOCATION_LATITUDE = 26.865644
        private const val PRIMARY_LOCATION_LONGITUDE = 81.001442
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
        private const val ALLOWED_RADIUS = 10.0 // 10 meters


        // Set the allowed radius threshold in meters
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.log_in)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermissions()

        auth = Firebase.auth

        var log_in = findViewById<Button>(R.id.btnlogin)
        var sgn_up = findViewById<TextView>(R.id.textViewSignUp)

        log_in.setOnClickListener {
            loginUserWithLocationCheck()
        }

        sgn_up.setOnClickListener {
            var intent = Intent(this, sign_up::class.java)
            startActivity(intent)
        }

    }
    private fun checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Location permissions granted, proceed with location check
            loginUserWithLocationCheck()
        }
    }

    private fun loginUserWithLocationCheck() {
        val primaryLocation = Location("Primary Location")

        primaryLocation.latitude = PRIMARY_LOCATION_LATITUDE // Set the latitude of the primary location
        primaryLocation.longitude = PRIMARY_LOCATION_LONGITUDE // Set the longitude of the primary location

        // Check for location permissions and get the user's current location
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    // Calculate the distance between the user's location and the primary location
                    val distance = calculateDistance(it, primaryLocation)
                    val latitude = location.latitude
                    val longitude = location.longitude
                    Log.d("Location", "Latitude: $latitude, Longitude: $longitude")

                    if (distance <= ALLOWED_RADIUS) {
                        // User's location is within the allowed radius, proceed with login
                        loginUser()
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        // User's location is outside the allowed radius, show error message
                        showError("You are outside the allowed radius for login.")
                    }
                } ?: run {
                    // Location is null, handle the case where user location is not available
                    showError("Unable to get your current location.")
                }
            }
                .addOnFailureListener { e ->
                    // Handle the failure case, such as location service error
                    showError("Failed to get location: ${e.message}")
                }
        }catch (e: SecurityException){
            showError("Location Permission Not Granted.")
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
                                    Toast.makeText(this, "Login Successful!", Toast.LENGTH_LONG)
                                        .show()
                                    // Redirect to the home screen or perform other actions
                                } else {
                                    // Login failed, handle the error
                                    Toast.makeText(
                                        this,
                                        "Login Failed: ${task.exception?.message}",
                                        Toast.LENGTH_LONG
                                    ).show()
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
                Toast.makeText(this, "Failed to query Firestore: ${e.message}", Toast.LENGTH_LONG)
                    .show()
            }
    }

    private fun showError(message: String) {
        Toast.makeText(this,"$message",Toast.LENGTH_LONG).show()
    }

    private fun calculateDistance(location1: Location, location2: Location): Float {
        // Use the Haversine formula to calculate the distance between two locations
        val earthRadius = 6371000 // Earth's radius in meters
        val dLat = Math.toRadians(location2.latitude - location1.latitude)
        val dLon = Math.toRadians(location2.longitude - location1.longitude)
        val a = sin(dLat / 2) * sin(dLat / 2) + cos(Math.toRadians(location1.latitude)) *
                cos(Math.toRadians(location2.latitude)) * sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return (earthRadius * c).toFloat()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permissions granted, proceed with location check
                loginUserWithLocationCheck()
            } else {
                // Location permissions not granted, handle accordingly (e.g., show an error message)
                showError("Location permissions not granted.")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults) // Add this line
    }
}

