package za.thirdyear.schedu

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import layout.Category
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase

private var categoryArray : Array<Array<String>> = arrayOf(
    arrayOf("CATWORK", "Work"),
    arrayOf("CATPERSONAL", "Personal"),
    arrayOf("CATSCHOOL", "School"))


class CreateCategoryActivity : BaseActivity() {
    private lateinit var btnAddCategoryPhoto : Button //Redirect to Add Photo Activity
    private lateinit var categoryImage: Uri
    private lateinit var imgCategoryPhoto : ImageView //Preview Photo
    private lateinit var txtCategoryName : EditText
    private lateinit var btnCreateCategory : Button
    //Code for UserID Preference
    private lateinit var authenticator: FirebaseAuth
    private lateinit var newCategoryImage : Image
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    //var imageView: ImageView? = null
    val IMAGE_CHOOSE = 1
    val IMAGE_CAPTURE = 2
    val IMAGE_PICK_CODE = 3 // GIVEN INTEGER VALUE FOR IMAGE_PICK_CODE



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_category)

        /******Hooks******/
        btnAddCategoryPhoto = findViewById(R.id.btnAddCategoryPhoto)
        txtCategoryName = findViewById(R.id.txtCategoryName)
        imgCategoryPhoto = findViewById(R.id.imgCategoryImage)
        btnCreateCategory = findViewById(R.id.btnCreateCategory)
        /******Variables******/

        //Check if user has ID
        val currentUserID = FirebaseAuth.getInstance().currentUser?.uid
        val currentUser = FirebaseAuth.getInstance().currentUser
        val database : FirebaseDatabase = FirebaseDatabase.getInstance()
        val id : String = ""

        /******btnAddCategoryPhoto Event Handlers******/
        btnAddCategoryPhoto.setOnClickListener()
        {
            var accessPermission : Boolean = true
            var message : String
            //Still need to convert ImageView to Image
            lifecycleScope.launch {

                try
                {
                    //Access Photos
                    var picture : AddPhotoActivity = AddPhotoActivity() //Add Photo Activity to


                    accessPermission = AccessGallery()
                    if (accessPermission == true)
                    {
                        //Select Gallery Image


                        //Store Image from Gallery into imageview and image variable


                    }

                    else //Show alert that permission was denied
                    {
                        message = "Permission denied"
                        ShowAlert(message)
                    }
                }
                catch (e : Exception)
                {
                    e.printStackTrace()
                }
            }
        }

        /******btnCreateCategory Event Handlers******/
        //Use Category Class to put category in other class
        btnCreateCategory.setOnClickListener()
        {
            //Create Category ID
            var categoryID = "CAT ${txtCategoryName.text.toString()}"

            //Add new category to List
            Category.categoryList.add(arrayOf(categoryID, txtCategoryName.text.toString()))
            val viewCategory : Intent = Intent(this, ViewCategoriesActivity::class.java)
            startActivity(viewCategory)
            Toast.makeText(this@CreateCategoryActivity, "Category successfully created", Toast.LENGTH_SHORT).show()

        }

    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_base
    }

    //Need to set for image imported from gallery- For Part 3
    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val bitmap :Bitmap
        var canvas : Canvas
        //imgCategoryPhoto.draw(canvas)
        //Check if there is an image selected
        if (resultCode == IMAGE_PICK_CODE && requestCode == Activity.RESULT_OK && data != null) {
            // Set image captured to image view
            //categoryImage = data.data- get image
            if(categoryImage != null)
            {
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, categoryImage)
                //Stores image into bitmap
                val imageVariable = bitmap


            }

        }
        else
        {
            //if Image URI is null (Show Alert)
            
        }
    }

    /******Check for permissions to access gallery- For Part 3******/
    private fun AccessGallery() : Boolean
    {
        val PERMISSION_CODE_READ = 1 // GIVE AN INTEGER VALUE FOR PERMISSION_CODE_READ LIKE 1
        val PERMISSION_CODE_WRITE = 2 // GIVE AN INTEGER VALUE FOR PERMISSION_CODE_WRITE LIKE 2
        var accessPermission = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                && (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            ) //If permissions are denied
            {
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                val permissionCoarse = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

                //Display Permission dialogue to user
                requestPermissions(permission, PERMISSION_CODE_READ)
                requestPermissions(permissionCoarse, PERMISSION_CODE_WRITE)
                //handle deny response from user
            }
            else //Get Image from user
            {
                accessPermission = true
            }
        }

        return accessPermission
    }

    /******Show Alert Message with String return Type- For Part 3******/
    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok_button_title, null)

        val dialog = builder.create()
        dialog.show()
    }




}