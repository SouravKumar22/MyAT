package com.example.myat// ListActivity.kt
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myat.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ViewAttendance : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_attendance)
        val tableLayout: TableLayout = findViewById(R.id.tableLayout)

        displayAttendanceData(tableLayout)

        /*
        // Set a click listener for the ListView items
        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = dataList[position]
            Toast.makeText(this, "Clicked: $selectedItem", Toast.LENGTH_SHORT).show()
        }*/
    }

    private fun displayAttendanceData(tableLayout:TableLayout) {
        val user_id = FirebaseAuth.getInstance().currentUser!!.uid

        val dataMap: HashMap<Any, String> = HashMap()
        FirebaseFirestore.getInstance().collection("users").document(user_id.trim()).collection("attendance").get().addOnCompleteListener{
                querySnapshot ->
            if (querySnapshot.isSuccessful) {
                for (document in querySnapshot.result!!) {
                    val attended: String = document.getString("attended").toString()
                    val date: Any = document.getString("date").toString()

                    dataMap[date] = attended
                    // Store the key-value pair in the Map
                    Log.e("datamap",dataMap.toString())
                }
               // val sortedByDate = dataMap.toList().sort { (_, value) -> value }.toMap()


                for ((key, value) in dataMap) {
                    val row = TableRow(this)
                    val keyTextView = TextView(this)
                    val valueTextView = TextView(this)

                    keyTextView.text = key as CharSequence?
                    valueTextView.text = value

                    keyTextView.textSize = 20f
                    valueTextView.textSize = 20f
                    // Add padding and other styles if needed
                    keyTextView.setPadding(16, 8, 8, 8)
                    valueTextView.setPadding(16, 8, 8, 8)

                    row.addView(keyTextView)
                    row.addView(valueTextView)

                    tableLayout.addView(row)
                }
            }
        }
    }
}
