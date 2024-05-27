import java.util.Date

class Project {
    lateinit var projectID : String
    lateinit var categoryName : String
    lateinit var userID : String
    lateinit var projectName : String
    lateinit var startDate : Date
    lateinit var endDate : Date
    var hoursDuration : Double
    var hoursSpent : Double
    var minHours : Int // Add minHours attribute
    var maxHours : Int // Add maxHours attribute

    constructor(ID : String, CategoryID : String ,
                Name : String, StartDate : Date, EndDate : Date, HoursDuration : Double,
                minHours : Int, maxHours : Int) // Add minHours and maxHours parameters to constructor
    {
        this.projectID = ID
        this.categoryName = CategoryID
        this.projectName = Name
        this.startDate = StartDate
        this.endDate = EndDate
        this.hoursDuration = HoursDuration
        this.hoursSpent = 0.0
        this.minHours = minHours
        this.maxHours = maxHours
    }

    companion object
    {
        var projects: ArrayList<Project> = ArrayList<Project>()
    }
}