package za.thirdyear.schedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.Date

class FilterProject : AppCompatActivity() {

    private lateinit var btnFilter: Button
    private lateinit var editTextText: EditText
    private lateinit var txtStartDateFilter: EditText
    private lateinit var txtEndDateFilter: EditText
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var btnDisplay:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_project)

        // Initialize UI components
        btnFilter = findViewById(R.id.btnFilter)
        editTextText = findViewById(R.id.editTextText)
        txtStartDateFilter = findViewById(R.id.txtStartDateFilter)
        txtEndDateFilter = findViewById(R.id.txtEndDateFilter)
        btnDisplay=findViewById(R.id.btnDisplay)

        // Initialize projects list
        //projects = Project.projects

        // Set up navigation drawer
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
                R.id.nav_home -> {
                    val moveIntent = Intent(this, MainActivity::class.java)
                    startActivity(moveIntent)
                    true
                }

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

                R.id.nav_logout -> {
                    val moveIntent = Intent(this, LoginActivity::class.java)
                    startActivity(moveIntent)
                    true
                }

                R.id.nav_create_project -> {
                    val moveIntent = Intent(this, CreateProjects::class.java)
                    startActivity(moveIntent)
                    true
                }

                else -> false
            }
        }

        // Apply filter button click listener
        btnFilter.setOnClickListener {
            applyFilter()
        }
        btnDisplay.setOnClickListener{
            val moveIntent = Intent(this, DisplayDetails::class.java)
            startActivity(moveIntent)
            true
        }
    }



    // Method to apply filter
    private fun applyFilter() {
        val filterText = editTextText.text.toString()
        val startDate = txtStartDateFilter.text.toString()
        val startDateFormatted = SimpleDateFormat("dd-MM-yyyy").parse(startDate)
        val endDate = txtEndDateFilter.text.toString()
        val endDateFormatted = SimpleDateFormat("dd-MM-yyyy").parse(endDate)
        if (startDate.isNotEmpty() && endDate.isNotEmpty() || filterText.isNotEmpty())
        {
            // Implement filtering logic here
            // You can use the filterText, startDate, and endDate variables to filter the projects
            for (project in Project.projects) {
                if (project.startDate == startDateFormatted && project.endDate == endDateFormatted&&project.projectName==filterText)
                {
                    editTextText.setText(project.projectName)
                    txtStartDateFilter.setText(project.startDate.toString())
                    txtEndDateFilter.setText(project.endDate.toString())
                    showAlert("Project Name: ${project.projectName} \n Start Date: ${project.startDate} \n End Date: ${project.endDate}\n  Duration: ${project.hoursDuration}  \n Minimum hours:${project.minHours}\n Maximum hours:${project.maxHours}")

                }
                else if (startDate.isEmpty() || endDate.isEmpty() || startDate.isBlank() || endDate.isBlank()) {
                    // Handle invalid date format or empty fields
                    showToast("Invalid date format or empty fields")
                    editTextText.text.clear()
                    txtStartDateFilter.text.clear()
                    txtEndDateFilter.text.clear()}

                else
                {
                    showToast("No projects found matching the filter criteria")
                }
            }


        }

    }



    // Function to show a toast message
    private fun showToast(message: String) {
        // Implement your toast message here
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()

    }
    /***Show Alert Message with String return Type- For Part 3***/
    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok_button_title, null)

        val dialog = builder.create()
        dialog.show()
    }
}