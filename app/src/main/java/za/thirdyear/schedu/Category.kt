package layout

import android.media.Image
import android.os.Build
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import za.thirdyear.schedu.CreateCategoryActivity
import java.util.Date


class Category
{
    //Attributes
    private var categoryID : String = "CAT000000"
    private var userIU : String = "User000000"
    private var categoryName : String = "Default Category Name"
    @RequiresApi(Build.VERSION_CODES.O)
    private lateinit var categoryImage : ImageView




    //Constructor for Class
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(categoryID: String, userIU: String, categoryName: String, Image: ImageView)
    {
        this.categoryID = categoryID
        this.userIU = userIU
        this.categoryName = categoryName
        this.categoryImage = Image
    }







    /******Create new Category:******/
    public fun NewCategory (newCategory: Category) : Boolean
    {
        var created : Boolean = false

        return created
    }


    /******Companion Object for Static Methods******/
    companion object StaticMethods
    {
        //Project Duration

        private var categoryArray : Array<Array<String>> = arrayOf(
            arrayOf("CATWORK", "Work", "0"),
            arrayOf("CATPERSONAL", "Personal", "0"),
            arrayOf("CATSCHOOL", "School", "0"))



        public var categoryList : MutableList<Array<String>> = mutableListOf<Array<String>>(
            arrayOf("CATWORK", "Work", "0"),
            arrayOf("CATPERSONAL", "Personal", "0"),
            arrayOf("CATSCHOOL", "School", "0"))


        /******Edit Category:******/
        fun UpdateCategory () : Boolean
        {
            var updated : Boolean = false

            return updated
        }


        /******Delete Category:******/
        public fun DeleteCategory (categoryID: String) : Boolean
        {
            var created : Boolean = false

            return created
        }


        /******Create Category Image******/
        public fun CategoryImage (categoryName : String)
        {

        }


    }
}