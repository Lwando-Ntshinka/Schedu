package za.thirdyear.schedu

import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class User {
    var userID : String
    var userName : String
    var userSurname : String
    var userEmail : String
    var userUserName : String
    var userPassword : String
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase


    //Constructor
    constructor(ID : String,
                Name : String,
                Surname : String,
                Email : String,
                Username : String,
                Password : String)
    {
        this.userID = ID
        this.userName = Name
        this.userSurname = Surname
        this.userEmail = Email
        this.userUserName = Username
        this.userPassword = Password
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
                        val userReference = firebaseDatabase.reference.child(NewUser.userID)
                        userReference.child(NewUser.userID).setValue(NewUser)
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
        /******Retrieve username and password from database******/
        fun LoginUser(email : String, password : String) : Boolean
        {
            var login : Boolean = false
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful)
                    {
                        //Successful Login
                        login = true
                    }

                    else
                    {
                        //Unsuccessful Login
                        login = false
                    }
                }
            return login
        }

    }



}