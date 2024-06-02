package za.thirdyear.schedu

import android.content.Intent
import android.os.Bundle
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView

class DisplayDetails : BaseActivity() {

    /***Declare Control Variables- lateint var ***/
    private lateinit var listViewDisplayDetails: ListView
    private lateinit var txtCategory: TextView
    private lateinit var textDuration: TextView
    private lateinit var btnFilter: Button
    private lateinit var btnRefresh: Button

    public var projectsArray : MutableMap<String, Double> = mutableMapOf<String, Double>()
    val categoryDurationMap = mutableMapOf<String, Double>()

//Crashes when navigating to display details as navigation and menu is not working
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_details)

        // Initialize views
        txtCategory = findViewById(R.id.textViewCategory)
        btnFilter = findViewById(R.id.buttonFilter)
        textDuration = findViewById(R.id.textHoursDuration)
        btnRefresh = findViewById(R.id.btnRefresh)
        listViewDisplayDetails = findViewById(R.id.ltvProjects)



        /***Variable Declaration***/
        for (project in Project.projects) {
            val categoryName = project.categoryName
            val duration = project.hoursDuration

            // If the category already exists in the map, add the duration to its existing sum
            // Otherwise, create a new entry in the map
            if (categoryDurationMap.containsKey(categoryName)) {
                categoryDurationMap[categoryName] = categoryDurationMap[categoryName]!! + duration
            } else {
                categoryDurationMap[categoryName] = duration
            }
        }

        // Convert the map to an ArrayList of pairs
        val projectsDurationArray = ArrayList<Pair<String, Double>>()
        for ((categoryName, duration) in categoryDurationMap) {
            projectsDurationArray.add(Pair(categoryName, duration))
        }
        var projectList: ArrayList<String> = ArrayList<String>()
        val listViewAdapter: Adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, projectList)
        listViewDisplayDetails = findViewById(R.id.ltvProjects)
        var categoryName: String = ViewCategoriesActivity.selectedCategoryName
        var totalDurationInCategory: Double =
            0.0//Add sum of total Hours spent and Duration of Projects in category

        //Get name of Category to display in the title of Page
        if (categoryName == "") {
            txtCategory.text = "Projects"
        } else {
            txtCategory.text = categoryName
        }

        //Code to display Project Details in a table
        //Note- Project Array is defined as Project.projects and use this when filtering
        if (Project.projects.isEmpty()) {
            projectList.add("No projects entered") //Initialize array if there are no projects
        }
        else {
            if (categoryName != ""){
                for (project in Project.projects) {
                    if (categoryName.contains(categoryName, ignoreCase = true) == project.categoryName.contains(project.categoryName, ignoreCase = true))
                    {
                        totalDurationInCategory += project.hoursDuration
                        val projectString: String =
                            "Project Name: ${project.projectName} \n Start Date: ${project.startDate} \n End Date: ${project.endDate}\n  Duration: ${project.hoursDuration}  \n Minimum hours:${project.minHours}\n Maximum hours:${project.maxHours}"
                        projectList.add(projectString) // Add projects stored in project class
                    }
                }
            }

            else {
                for (project in Project.projects) {
                    totalDurationInCategory += project.hoursDuration
                    var projectString: String =
                        "Project Name: ${project.projectName} \n Start Date: ${project.startDate} \n End Date: ${project.endDate}\n  Duration: ${project.hoursDuration}  \n Minimum hours:${project.minHours}\n Maximum hours:${project.maxHours}"
                    projectList.add(projectString) //Add projects stored in project class
                }
            }

        }

        textDuration.text = "Total Hours Spent: $totalDurationInCategory"
        listViewDisplayDetails.adapter = listViewAdapter as ListAdapter?


        //Button to get to ProjectFilteringActivity
        btnFilter.setOnClickListener()
        {
            val moveIntent = Intent(this, FilterProject::class.java)
            startActivity(moveIntent)

        }

        btnRefresh.setOnClickListener()
        {
            for (project in Project.projects) {
                totalDurationInCategory += project.hoursDuration
                var projectString: String =
                    "Project Name: ${project.projectName} \n Start Date: ${project.startDate} \n End Date: ${project.endDate}\n  Duration: ${project.hoursDuration}  \n Minimum hours:${project.minHours}\n Maximum hours:${project.maxHours}"
                projectList.add(projectString) //Add projects stored in project class
            }
            textDuration.text = "Total Hours Spent: $totalDurationInCategory"
            listViewDisplayDetails.adapter = listViewAdapter as ListAdapter?
        }


    }



    override fun getLayoutResourceId(): Int {
        return R.layout.activity_base
    }
}