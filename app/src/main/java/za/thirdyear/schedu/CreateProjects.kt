package za.thirdyear.schedu

import Project
import android.content.Intent
import android.icu.text.DateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import layout.Category
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date

class CreateProjects : AppCompatActivity() {

    private lateinit var categories : MutableList<String>
    lateinit var txtStartDate : EditText
    lateinit var txtEndDate : EditText
    lateinit var txtHoursRequired : EditText
    private lateinit var adapter: ArrayAdapter<Project>


    // ArrayAdapter for projects
    lateinit var projectAdapter: ArrayAdapter<Project>
    lateinit var btnCreateProject: Button // Button to add projects
    lateinit var txtProjectName : EditText
    lateinit var btnAddProjectPhoto:Button
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var txtMin:EditText
    private lateinit var txtMax:EditText




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_projects)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        toolbar = findViewById(R.id.toolbar)


        // Linking views using findViewByIdm@gmail
        txtProjectName = findViewById(R.id.txtProjectName)
        txtStartDate = findViewById(R.id.txtStartDate)
        txtEndDate = findViewById(R.id.txtEndDate)
        txtHoursRequired = findViewById(R.id.txtHoursRequired)
        btnCreateProject=findViewById(R.id.btnCreateProject)
        btnAddProjectPhoto=findViewById(R.id.btnAddProjectPhoto)
        txtMin=findViewById(R.id.txtMin)
        txtMax=findViewById(R.id.txtMax)


        categories = ViewCategoriesActivity.categoryNameList //Mutable List of Categories from ViewCategories


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
                R.id.nav_logout->{
                    val moveIntent = Intent(this, LoginActivity::class.java)
                    startActivity(moveIntent)
                    true
                }
                R.id.nav_view_categories ->{
                    val moveIntent = Intent(this, ViewCategoriesActivity::class.java)
                    startActivity(moveIntent)
                    true
                }
                R.id.nav_display_details->{
                    val moveIntent = Intent(this, DisplayDetails::class.java)
                    startActivity(moveIntent)
                    true
                }
                R.id.nav_logout->{
                    val moveIntent = Intent(this, LoginActivity::class.java)
                    startActivity(moveIntent)
                    true
                }
                else -> false
            }
        }

// Initializing the ArrayAdapter with the context, layout, and data
//adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, categories)
        projectAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Project.projects)

// Add Project to the Project list when the button is clicked
        btnCreateProject.setOnClickListener {
            try {
                val project = txtProjectName.text.toString()
                val sDate = txtStartDate.text.toString()
                val eDate = txtEndDate.text.toString()
                val hours = txtHoursRequired.text.toString()
                val min= txtMin.text.toString()
                val max= txtMax.text.toString()


                var startDate: Date = SimpleDateFormat("dd-MM-yyyy").parse(sDate)
                var endDate: Date = SimpleDateFormat("dd-MM-yyyy").parse(eDate)
                var projectObject: Project =
                    Project("PRO$project", "CAT", project, startDate, endDate, hours.toDouble(),min.toInt(),max.toInt()) //Create Project Object
                if (!Project.projects.contains(projectObject))
                {
                    Project.projects.add(projectObject) //Add project object
                    txtProjectName.text.clear()
                    txtStartDate.text.clear()
                    txtEndDate.text.clear()
                    txtHoursRequired.text.clear()
                    txtMin.text.clear()
                    txtMax.text.clear()
                }
                val moveIntent = Intent(this, DisplayDetails::class.java)
                startActivity(moveIntent)
            }

            catch (e : Exception)
            {
                showAlert("Unable to save project")
            }
        }
        btnAddProjectPhoto.setOnClickListener {
            val moveIntent = Intent(this, AddPhotoActivity::class.java)
            startActivity(moveIntent)
            true
        }
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