package com.example.myat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.myat.fragments.EditFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressedMethod()
        }

    }

    private fun onBackPressedMethod() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            finish()
        }
    }

    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawerLayout)

        val navigationView = findViewById<NavigationView>(R.id.navView)
        val header = navigationView.getHeaderView(0)
        //val userNameTxt = findViewById<TextView>(R.id.usernameText)
        val profileImg = findViewById<ImageView>(R.id.profileImg)

        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        updateNavHeader()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_edit -> {
                title = "Edit"
                var intent = Intent(this, edit::class.java)
                Toast.makeText(this, "Not Worked", Toast.LENGTH_LONG).show()
                startActivity(intent)
            }
            R.id.nav_logout -> {
                Log.e("Logout", "Logged out")
                var intent = Intent(this, log_in::class.java)
                Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show()
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun updateNavHeader() {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val navView = findViewById<NavigationView>(R.id.navView)

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid

            db.collection("users").document(uid)
                .get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val username = documentSnapshot.getString("name")
                        // Update the navigation header UI with the retrieved username
                        val navHeaderView = navView.getHeaderView(0)
                        val navWelcome = navHeaderView.findViewById<TextView>(R.id.nav_welcome)
                        navWelcome.text = "Welcome,\n\n$username"
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error Loading Name", Toast.LENGTH_LONG).show()
                }
        }
    }
}

