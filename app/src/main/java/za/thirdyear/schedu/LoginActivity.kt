package za.thirdyear.schedu

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {
    private lateinit var btnRegister: Button
    private lateinit var btnLogin: Button
    private lateinit var txtEmailAddress: EditText
    private lateinit var txtPassword: EditText
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var firebaseStore : FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /******Hooks******/
        btnRegister = findViewById(R.id.btnRegisterRedirect)
        btnLogin = findViewById(R.id.btnLogin)
        txtEmailAddress = findViewById(R.id.txtEmailAddress)
        var email = txtEmailAddress.text.toString()
        txtPassword = findViewById(R.id.txtPassword)
        var password = txtPassword.text.toString()
        var loginAuth : FirebaseAuth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE) //Initialize SharedPreferences
        FirebaseApp.initializeApp(this) //Initialise Firebase App in application


        /******Redirect to Register Page******/
        btnRegister.setOnClickListener {
            val registerPage = Intent(this, RegistrationActivity::class.java)
            startActivity(registerPage)

        }

        // Authenticate User
        btnLogin.setOnClickListener {
            var loginUser : User
            if(txtEmailAddress.text.isEmpty() or txtPassword.text.isEmpty())
            {
                Toast.makeText(this, "Please fill in you credetials to login", Toast.LENGTH_SHORT).show()
            }
            else
            {
                val email = txtEmailAddress.text.toString().trim()
                val password = txtPassword.text.toString().trim()

                loginUser = User.LoginUser(email, password) //Login User
                //Retrieve data from Firebase including user's name and store in SharedPreference
                if(loginUser.equals("")) //if sign in failed
                {
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                }
                else //If login was successful
                {
                    //Get User Details and store in shared preference
                    saveUserData(loginUser.userID, loginUser.userName, loginUser.userSurname, loginUser.userEmail)
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                }


            }
        }
    }

    //Shared preference function to save user data
    private fun saveUserData(
        id : String,
        name: String,
        surname: String,
        emailAddress: String,
    ) {
        // Save user data to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("id", id)
        editor.putString("name", name)
        editor.putString("surname", surname)
        editor.putString("emailAddress", emailAddress)
        editor.apply()
    }



}