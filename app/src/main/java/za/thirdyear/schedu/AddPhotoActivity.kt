package za.thirdyear.schedu

import Project
import android.annotation.SuppressLint
import android.app.Activity
import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.io.IOException


class AddPhotoActivity : AppCompatActivity() {

    private lateinit var button1: Button
    private lateinit var imageView: ImageView
    private lateinit var button2: Button
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var takePhoto : Button

    val data_entries = mutableListOf<Project>() // Create an empty list

    private val CAMERA_PERMISSION_CODE = 1000
    private val READ_PERMISSION_CODE = 1001

    private val IMAGE_CHOOSE = 1000
    private val IMAGE_CAPTURE = 1001

    private var imageUri: Uri? = null
    private var img3gallery: ImageView? = null


    companion object{
        val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)

        button1 = findViewById(R.id.btnAddPicture)
        button2 = findViewById(R.id.btnDone)
        imageView = findViewById(R.id.imgEntryImage)
        takePhoto = findViewById(R.id.btnTakePhoto)

        button1.setOnClickListener{
            pickImageFromGallery()
        }

        button2.setOnClickListener{
            val bitmap = (imageView.drawable as? BitmapDrawable)?.bitmap
            if (bitmap != null) {
                saveImageToInternalStorage(bitmap)
                // Add the data entry object to the list here, after saving the image
                //val imageUri = intent.data // Get the URI from the intent parameter, not from the data class variable
                //val dataEntry = data_Entries("Some title", 0, "Some date", "Some user", imageUri.toString()) // Create a data entry object with the image URI
                //data_entries.add(dataEntry) // Add the object to your list

            } else {
                Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show()
            }
        }

        //Take photo Button
        takePhoto.setOnClickListener()
        {
            val permissionGranted = requestCameraPermission()
            if (permissionGranted) {
                // Open the camera interface
                openCameraInterface()
            }
        }



        //back button
        val backbutton: Button = findViewById(R.id.btnBackHome)
        backbutton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    private fun chooseImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_CHOOSE)
    }

    private fun requestCameraPermission(): Boolean {
        var permissionGranted = false

        // If system os is Marshmallow or Above, we need to request runtime permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val cameraPermissionNotGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
            if (cameraPermissionNotGranted) {
                val permission = arrayOf(Manifest.permission.CAMERA)

                // Display permission dialog
                requestPermissions(permission, CAMERA_PERMISSION_CODE)
            } else {
                // Permission already granted
                permissionGranted = true
            }
        } else {
            // Android version earlier than M -> no need to request permission
            permissionGranted = true
        }

        return permissionGranted
    }

    private fun openCameraInterface() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, R.string.add_category_photo)
        imageUri = contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        // Create camera intent
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)

        // Launch intent
        startActivityForResult(intent, IMAGE_CAPTURE)
    }

    // Handle Allow or Deny response from the permission dialog
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                openCameraInterface()
            } else {
                // Permission was denied
                showAlert("Camera permission was denied. Unable to take a picture.")
            }
        } else if (requestCode == READ_PERMISSION_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val storagePermissionGranted = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                if (storagePermissionGranted) {
                    chooseImageGallery()
                } else {
                    showAlert("Storage permission was denied.")
                }
            } else {
                showAlert("Storage permission Not Needed.")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            imageView.setImageURI(data?.data)
        }
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.Okay, null)

        val dialog = builder.create()
        dialog.show()

    }

    private fun saveImageToInternalStorage(bitmap: Bitmap) {
        val filename = "image.jpg" // Specify the desired filename for your image

        try {
            val outputStream = openFileOutput(filename, Context.MODE_PRIVATE)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
            Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show()
        }
        //Menu, Drawer and Toolbar
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.nav_view_categories -> {

                    val moveIntent = Intent(this, ViewCategoriesActivity::class.java)
                    startActivity(moveIntent)
                    true
                }

                R.id.nav_display_details -> {

                    val moveIntent = Intent(this, DisplayDetails::class.java)
                    startActivity(moveIntent)
                    true
                }

                R.id.nav_create_project -> {

                    val moveIntent = Intent(this, CreateProjects::class.java)
                    startActivity(moveIntent)
                    true
                }


                R.id.nav_logout -> {

                    val moveIntent = Intent(this, LoginActivity::class.java)
                    startActivity(moveIntent)
                    true
                }

                else -> false
            }
        }

    }
}
