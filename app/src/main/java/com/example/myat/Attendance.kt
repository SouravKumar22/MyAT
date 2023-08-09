package com.example.myat

import android.app.DatePickerDialog
import android.app.ProgressDialog.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.play.integrity.internal.x
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import getDataFromSharedPreferences
import java.text.SimpleDateFormat
import java.util.*

class Attendance : AppCompatActivity() {
    private lateinit var dte: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)


        val rg = findViewById<RadioGroup>(R.id.radiogroup)
        val submit = findViewById<Button>(R.id.btnsmtatt)

        dte = findViewById(R.id.tvDate)
        dte.setOnClickListener {
            showDatePicker()
        }

        var attended="Present"
        rg.setOnCheckedChangeListener{ group, checkedID ->
            when(checkedID){
                R.id.yes ->{
                    attended="Present"
                    Toast.makeText(this,"You Are Present",Toast.LENGTH_SHORT).show()
                }
                R.id.no ->{
                    attended="Absent"
                    Toast.makeText(this,"You Are Absent",Toast.LENGTH_SHORT).show()
                }
                R.id.leave ->{
                    attended="Leave"
                    Toast.makeText(this,"You Are On Leave",Toast.LENGTH_SHORT).show()
                }
            }

        }

        submit.setOnClickListener{
            val attendanceDetails = hashMapOf(
                "date" to dte.text,
                "attended" to attended,
            )
            val user_id = FirebaseAuth.getInstance().currentUser!!.uid
            if (!user_id.equals("")&& user_id!=null){
                Firebase.firestore.collection("users").document(user_id).collection("attendance").document().set(attendanceDetails)
            }
            Toast.makeText(this,"Attendance Marked",Toast.LENGTH_SHORT).show()
        }
    }



    private fun showDatePicker() {
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        // Create the date picker dialog
        val datePicker = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Do something with the selected date if needed
                // For example, you can display the selected date in a TextView
                val selectedDate = Calendar.getInstance()
                selectedDate.set(selectedYear, selectedMonth, selectedDay)
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    .format(selectedDate.time)
                dte.text = formattedDate
            },
            year,
            month,
            day
        )

        // Set the minimum and maximum selectable dates
        val minDate = Calendar.getInstance()
        minDate.add(Calendar.DAY_OF_MONTH, 0) // Disable past dates
        datePicker.datePicker.minDate = minDate.timeInMillis

        val maxDate = Calendar.getInstance()
        maxDate.add(Calendar.MONTH,0) // Disable dates beyond 3 months in the future
        datePicker.datePicker.maxDate = maxDate.timeInMillis

        // Show the date picker dialog
        datePicker.show()
    }
}