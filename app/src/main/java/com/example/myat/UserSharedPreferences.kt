import android.content.Context

// Function to save data in Shared Preferences
fun saveDataToSharedPreferences(context: Context, key: String, value: String) {
    val sharedPrefs = context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
    val editor = sharedPrefs.edit()
    editor.putString(key, value)
    editor.apply()
}

// Function to retrieve data from Shared Preferences
fun getDataFromSharedPreferences(context: Context, key: String): String? {
    val sharedPrefs = context.getSharedPreferences("CurrentUser", Context.MODE_PRIVATE)
    return sharedPrefs.getString(key, null)
}
/*

// Usage
fun main() {
    val context = // Get the application context

        // Save data to Shared Preferences
        saveDataToSharedPreferences(context, "username", "JohnDoe")

    // Retrieve data from Shared Preferences
    val username = getDataFromSharedPreferences(context, "username")
    println("Username: $username") // Output: Username: JohnDoe
}
*/
