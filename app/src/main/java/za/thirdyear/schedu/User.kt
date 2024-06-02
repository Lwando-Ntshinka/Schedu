package za.thirdyear.schedu

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class User {
    var userID : String = ""
    var userName : String = ""
    var userSurname : String = ""
    var userEmail : String = ""
    lateinit var userUserName : String
    lateinit var userPassword : String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase



    //Constructor
    constructor()
    {}

    constructor(Name : String,
                Surname : String,
                Email : String,
                Username : String,
                Password : String)
    {
        this.userName = Name
        this.userSurname = Surname
        this.userEmail = Email
        this.userUserName = Username
        this.userPassword = Password
    }

    constructor(ID : String,
                Name : String,
                Surname : String,
                Email : String,)
    {
        this.userID = ID
        this.userName = Name
        this.userSurname = Surname
        this.userEmail = Email
    }

    /******Create- Insert data to add Details to Firebase for Registration******/
    public fun RegisterUser(NewUser: User): Boolean {
        var registered: Boolean = false

        //Enter newUser into Firebase (as Dictionary)
        firebaseAuth.createUserWithEmailAndPassword(NewUser.userName, NewUser.userPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    // User registered successfully
                    val user = firebaseAuth.currentUser
                    user?.let {
                        // Get the user ID
                        val userID = user.uid

                        // Save additional user information to the Realtime Database
                        val userReference = firebaseDatabase.reference.child(NewUser.userEmail)
                        userReference.child(NewUser.userEmail).setValue(NewUser)
                            .addOnSuccessListener { registered = true }
                            .addOnFailureListener { registered = false } // User registration failed
                    }
                }

                // User registration unsuccessful
                else {

                    registered = false
                }
            }

        //Return true of registration is successful or false if registration failed
        return registered
    }

    /******Update User Details:******/
    public fun UpdateUserDetails (newData : String, Column : String) : Boolean
    {
        var updated = false

        return updated
    }

    /******Delete User Details:******/
    public fun DeleteUserDetails (newData : String, Column : String) : Boolean
    {
        var deleted = false

        return deleted
    }

    companion object{
        lateinit var firebaseAuth : FirebaseAuth //Declared Firebase Authorisation
        private lateinit var firebaseStore : FirebaseFirestore


        //Shared preference function to save user data
        public fun saveUserData(
            name: String,
            surname: String,
            username: String,
            emailAddress: String,
            password: String
        ) {
            // Initialize SharedPreferences
            lateinit var sharedPreferences: SharedPreferences
            //sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
            // Save user data to SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("name", name)
            editor.putString("surname", surname)
            editor.putString("username", username)
            editor.putString("emailAddress", emailAddress)
            editor.putString("password", password)
            editor.apply()
        }

        /******Retrieve username and password from database******/
        fun LoginUser(email : String, password : String) : User
        {
            var loginUser : User = User()
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful)
                    {
                        //Successful Login
                        val currentUserFirebase = firebaseAuth.currentUser
                        currentUserFirebase?.let {
                            val firebaseUserId = it.uid
                            loginUser = retrieveUserDetails(firebaseUserId)
                        }

                    }

                    else
                    {
                        //Unsuccessful Login
                        loginUser = User()
                    }
                }
            return loginUser
        }

        public fun retrieveUserDetails(userId: String): User {

            var UserData = User()
            firebaseStore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val name = document.getString("name") ?: ""
                        val surname = document.getString("surname") ?: ""
                        val email = document.getString("email") ?: ""

                        //Store User Data
                        UserData = User(userId, name, surname, email)
                        Log.d(ContentValues.TAG, "Stored user data")
                        // Navigate to the next screen or update UI


                    } else {
                        Log.d(ContentValues.TAG, "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d(ContentValues.TAG, "get failed with ", exception)
                }

            return UserData
        }

    }



}