package za.thirdyear.schedu

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationActivity : AppCompatActivity() {

    // Define view variables
    private lateinit var txtTextName: TextView
    private lateinit var txtSurname: TextView
    private lateinit var txtUserName: TextView
    private lateinit var txtEmailAddress: TextView
    private lateinit var txtPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var registerUser: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var btnRegisterRedirect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        FirebaseApp.initializeApp(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)

        // Initialize views
        txtTextName = findViewById(R.id.txtTextName)
        txtSurname = findViewById(R.id.txtSurname)
        txtUserName = findViewById(R.id.txtUserName)
        txtEmailAddress = findViewById(R.id.txtEmailAddress)
        txtPassword = findViewById(R.id.txtPassword)
        btnRegister = findViewById(R.id.btnRegister)
        btnRegisterRedirect = findViewById(R.id.btnRegisterRedirect)


        /******Register User Button******/
        btnRegister.setOnClickListener {
            val name = txtTextName.text.toString().trim()
            val surname = txtSurname.text.toString().trim()
            val username = txtUserName.text.toString().trim()
            val emailAddress = txtEmailAddress.text.toString().trim()
            val password = txtPassword.text.toString().trim()
            val newUser: User //Enter Details
            val savedNewUser : User
            var registrationStatus: Boolean

            // Check if any field is empty
            if (name.isEmpty() || surname.isEmpty() || username.isEmpty() || emailAddress.isEmpty() || password.isEmpty()) {
                // Display error message
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please fill in all fields",
                    Toast.LENGTH_SHORT
                ).show()
            }
            //Register User
            else {
                // All fields are filled, proceed with registration
                // Save the data (you can implement your saving logic here)
                newUser = User(
                    name,
                    surname,
                    username,
                    emailAddress,
                    password
                ) //Create object of user to enter into firebase
                //Create Account for Firebase with username and password
                RegisterUser(emailAddress, password, newUser) {registrationStatus->
                    if (registrationStatus) //If registration was successful
                    {
                        // Show success message
                        Toast.makeText(
                            this@RegistrationActivity,
                            "Thank you, your account has been successfully registered!",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Navigate to login activity
                        val loginIntent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                        startActivity(loginIntent)
                        saveUserData(
                            name,
                            surname,
                            username,
                            emailAddress,
                            password
                        ) //Save user data in shared preference
                    } else //if registration was unsuccessful
                    {
                        Toast.makeText(
                            this@RegistrationActivity,
                            "Your registration has unsuccessful",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }



            }
        }

        //Redirect to Login
        btnRegisterRedirect.setOnClickListener {
            val loginIntent = Intent(this@RegistrationActivity, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    //Shared preference function to save user data
    private fun saveUserData(
        name: String,
        surname: String,
        username: String,
        emailAddress: String,
        password: String
    ) {
        // Save user data to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putString("name", name)
        editor.putString("surname", surname)
        editor.putString("username", username)
        editor.putString("emailAddress", emailAddress)
        editor.putString("password", password)
        editor.apply()
    }


    //Requires Testing
    public fun RegisterUser(email: String, password: String, user: User, callback: (Boolean) -> Unit): Boolean {
        var registered = false
        registerUser = FirebaseAuth.getInstance() //Taking FirebaseAuth Instance
        val firebaseFirestore = FirebaseFirestore.getInstance()

        //Register UserAuthResult
        registerUser.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener()
            { task ->
                if (task.isSuccessful) {
                    val userID = registerUser.currentUser?.uid
                    if (userID != null) {
                        //Save user data in Firestore
                        firebaseFirestore.collection("users").document(userID).set(user)
                            .addOnCompleteListener { firestoreTask ->
                                if (firestoreTask.isSuccessful) /*if registration is successful*/ {
                                    callback(true)
                                    registered = true
                                } else {

                                    callback(false)
                                    registered = false
                                }
                            }
                    } else if (!task.isSuccessful) /*if registration is unsuccessful*/{
                         callback(false)//make registered false
                        registered = false
                    }
                }

            }
        return registered;
    }
}
