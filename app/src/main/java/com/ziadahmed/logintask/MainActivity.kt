package com.ziadahmed.logintask
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextUsername = findViewById<EditText>(R.id.editTextUsername)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        val errorEmail = findViewById<TextView>(R.id.errorEmail)
        val errorUsername = findViewById<TextView>(R.id.errorUsername)
        val errorPassword = findViewById<TextView>(R.id.errorPassword)

        buttonSubmit.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            var isValid = true

            // Validate Email
            if (!Validation.isValidEmail(email)) {
                errorEmail.text = "Invalid email address"
                errorEmail.visibility = TextView.VISIBLE
                isValid = false
            } else {
                errorEmail.visibility = TextView.GONE
            }

            // Validate Username
            if (!Validation.isValidUsername(username)) {
                errorUsername.text = "Invalid username (only letters allowed)"
                errorUsername.visibility = TextView.VISIBLE
                isValid = false
            } else {
                errorUsername.visibility = TextView.GONE
            }

            // Validate Password
            if (!Validation.isValidPassword(password)) {
                errorPassword.text = "Password must be at least 12 characters, include a number and a special character"
                errorPassword.visibility = TextView.VISIBLE
                isValid = false
            } else {
                errorPassword.visibility = TextView.GONE
            }

            // Check if all validations are successful
            if (isValid) {
                // Handle successful login
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, InfoActivity::class.java)
                startActivity(intent)

            } else {
                // Handle validation error
                Toast.makeText(this, "Invalid input. Please check your details.", Toast.LENGTH_SHORT).show()
            }
        }


    }


}
