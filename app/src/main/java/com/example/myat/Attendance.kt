package com.example.myat

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class Attendance : AppCompatActivity() {
    private lateinit var dte: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)


        val rg = findViewById<RadioGroup>(R.id.radiogroup)

        dte = findViewById(R.id.tvDate)
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR,year)
            myCalendar.set(Calendar.MONTH,month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }

        dte.setOnClickListener {
            DatePickerDialog(this,datePicker,myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        rg.setOnCheckedChangeListener{ group, checkedID ->
            when(checkedID){
                R.id.yes ->{
                    Toast.makeText(this,"You Are Present",Toast.LENGTH_SHORT).show()
                }
                R.id.no ->{
                    Toast.makeText(this,"You Are Absent",Toast.LENGTH_SHORT).show()
                }
                R.id.leave ->{
                    Toast.makeText(this,"You Are On Leave",Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        dte.setText(sdf.format(myCalendar.time))
    }

}