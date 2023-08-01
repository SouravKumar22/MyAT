package com.example.myat

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myat.fragments.EditFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import getDataFromSharedPreferences
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import saveDataToSharedPreferences
import java.util.UUID


class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener {

    private val db = Firebase.firestore
    private lateinit var profile_Img2: ImageView
    private lateinit var profile_Img: ImageView

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
        display()

        val navigationView = findViewById<NavigationView>(R.id.navView)
        val header = navigationView.getHeaderView(0)
        //val userNameTxt = findViewById<TextView>(R.id.usernameText)
        profile_Img2 = findViewById(R.id.profileImg2)

        navigationView.setNavigationItemSelectedListener(this)


        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        updateNavHeader()

        setProfile()
        profile_Img2.setOnClickListener {
            Log.e("ImageTag", "Reached in setOnClickListener()")
            pickImageFromGallery()

        }
    }

    private fun setProfile() {
        var imgURL = ""
        Log.e("SetProfile","Inside")
        try {

//            val user_id = FirebaseAuth.getInstance().currentUser!!.uid
            var user_id = getDataFromSharedPreferences(this,"emp_id")
            Log.e("userid","$user_id")
            if (!user_id.equals("")&& user_id!=null){
            db.collection("users")
                .whereEqualTo("emp_id", user_id)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    if (!querySnapshot.isEmpty) {
                        Log.e("Reached", "SetProfile")
                        val userDocument = querySnapshot.documents[0]
                        imgURL = userDocument.getString("profileImageUrl").toString()
                        Log.e("IMG URL",imgURL)
                        saveDataToSharedPreferences(this,"imageUrl",imgURL)

                        Picasso.get()
                            .load(imgURL).transform(CropCircleTransformation()).centerCrop()
                            .resize(profile_Img2.getMeasuredWidth(),profile_Img2.getMeasuredHeight())
                            .error(R.drawable.ic_person)
                            .into(profile_Img2)
                    }
                }
            }
        }catch (e:Exception){
            Log.e("Image url ", "Image url not found $e")
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        profile_Img = findViewById(R.id.profileImg)

        val imgURL = getDataFromSharedPreferences(this,"imageUrl")
        Log.e("imgtag",imgURL.toString())
        try{
            Picasso.get()
                .load(imgURL.toString())
                .transform(CropCircleTransformation()).centerCrop()
                .resize(profile_Img2.getMeasuredWidth(),profile_Img2.getMeasuredHeight()).error(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(profile_Img)
        }catch (e:Exception){

            Log.e("tag:",e.message.toString())
        }


        Log.e("Navigation bar:", item.itemId.toString())
        when (item.itemId) {
            R.id.nav_edit -> {
                title = "Edit"
                val intent = Intent(this, EditFragment::class.java)
                startActivity(intent)
            }
            R.id.nav_logout -> {
                Log.e("Logout", "Logged out")
                val intent = Intent(this, log_in::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
                Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show()

            }
            R.id.nav_view_attendance -> {
                Log.e("View Attendance", "View Attendance Function Called.")
                val intent = Intent(this, ViewAttendance::class.java)
                startActivity(intent)
                //finish()
            }
            R.id.nav_delete -> {
                Log.e("Delete", "Deleted Function Called.")
                val intent = Intent(this, delete_profile::class.java)
                startActivity(intent)
                finish()
            }
            R.id.nav_att -> {
                Log.e("Attendance", "Into Attendance Activity.")
                val intent = Intent(this, Attendance::class.java)
                startActivity(intent)
                //finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    @SuppressLint("ResourceType")
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

    private fun display() {

        val id = findViewById<TextView>(R.id.disp_id)
        val nme = findViewById<TextView>(R.id.disp_name)
        val mbl = findViewById<TextView>(R.id.disp_mobile)
        val mal = findViewById<TextView>(R.id.disp_mail)
        val dsgn = findViewById<TextView>(R.id.disp_designtn)
        val user_id = FirebaseAuth.getInstance().currentUser!!.uid
        val ref = db.collection("users").document(user_id)


        ref.get().addOnSuccessListener {
            if (it != null) {
                val employee_id = it.data?.get("emp_id")?.toString()
                val mobile = it.data?.get("mobile")?.toString()
                val name = it.data?.get("name")?.toString()
                val desig = it.data?.get("designation")?.toString()
                val mil = it.data?.get("email")?.toString()

                id.text = employee_id
                mbl.text = mobile
                nme.text = name
                dsgn.text = desig
                mal.text = mil

            }

        }
            .addOnFailureListener {
                Toast.makeText(this, "Error Loading Details", Toast.LENGTH_SHORT).show()
            }
    }

    private val PICK_IMAGE_REQUEST = 1

    private fun pickImageFromGallery() {
        Log.e("ImageTag", "Reached in pickImageFromGallery()")
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            // Now you have the image URI, proceed to upload it to Firebase Storage and store the URL in Firestore.
            val imageUri = data.data
            if (imageUri != null) {
                uploadImageToFirebase(imageUri)
                saveDataToSharedPreferences(this,"imageUrl",imageUri.toString())

                Picasso.get()
                    .load(imageUri).transform(CropCircleTransformation()).centerCrop().resize(profile_Img2.getMeasuredWidth(),profile_Img2.getMeasuredHeight()).error(R.drawable.ic_person)
                    .into(profile_Img2)
            }
        }
    }

    private fun saveImageUrlToFirestore(imageUrl: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            val userRef = FirebaseFirestore.getInstance().collection("users").document(uid)
            userRef.update("profileImageUrl", imageUrl)
                .addOnSuccessListener {
                    // Image URL successfully stored in Firestore.
                }
                .addOnFailureListener {
                    // Handle the Firestore update failure, if any.
                    Log.e("Exception", "Error")
                }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri) {
        val filename = UUID.randomUUID().toString()
        val emp_id = getDataFromSharedPreferences(this,"emp_id")
        FirebaseStorage.getInstance().getReference("/profile_images/$emp_id").delete().addOnCompleteListener(){result->
            Log.e("Deleted",result.isSuccessful.toString())
        }
        val ref = FirebaseStorage.getInstance().getReference("/profile_images/$emp_id/$filename")

        ref.putFile(imageUri)
            .addOnSuccessListener {
                ref.downloadUrl.addOnSuccessListener { downloadUri ->
                    // Get the download URL and store it in Firestore.
                    saveImageUrlToFirestore(downloadUri.toString())
                }
            }
            .addOnFailureListener {
                // Handle the upload failure, if any.
                Toast.makeText(this, "Failed To Upload Image", Toast.LENGTH_SHORT).show()

            }
    }
}


