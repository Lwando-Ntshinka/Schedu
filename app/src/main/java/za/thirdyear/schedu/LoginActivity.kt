package za.thirdyear.schedu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity() {
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var txtEmailAddress: EditText
    private lateinit var txtPassword: EditText
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        FirebaseApp.initializeApp(this) //Initialise Firebase App in application

        // Initialize views
        btnRegister = findViewById(R.id.btnRegisterRedirect)
        btnLogin = findViewById(R.id.btnLogin)
        txtEmailAddress = findViewById(R.id.txtEmailAddress)
        var email = txtEmailAddress.text.toString()
        txtPassword = findViewById(R.id.txtPassword)
        var password = txtPassword.text.toString()
        /******Declare variables*****/
        var loginAuth : FirebaseAuth = FirebaseAuth.getInstance()

        // Redirect to Register Page
        btnRegister.setOnClickListener {
            val registerPage = Intent(this, RegistrationActivity::class.java)
            startActivity(registerPage)

        }

        // Authenticate User
        btnLogin.setOnClickListener {
            val email = txtEmailAddress.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            // Retrieve saved user data from SharedPreferences
            val savedEmail = sharedPreferences.getString("emailAddress", "")
            val savedPassword = sharedPreferences.getString("password", "")

            // Compare user input with saved data
            if (email == savedEmail && password == savedPassword) {
                // Login successful, navigate to main activity
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            } else {
                // Login failed, display error message
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}