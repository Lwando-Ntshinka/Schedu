package layout

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import za.thirdyear.schedu.AddPhotoActivity
import za.thirdyear.schedu.CreateCategoryActivity
import za.thirdyear.schedu.R
import java.io.IOException


class Category
{
    //Attributes
    private var categoryID : String = ""
    private var userIU : String = ""
    private var categoryName : String = ""
    @RequiresApi(Build.VERSION_CODES.O)
    private var categoryImage : Uri? = null //Image stored as uri

    //Constructor for Class
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(categoryID: String, userIU: String, categoryName: String, Image: Uri?)
    {
        this.categoryID = categoryID
        this.userIU = userIU
        this.categoryName = categoryName
        this.categoryImage = Image
    }
    @RequiresApi(Build.VERSION_CODES.O)
    constructor(userIU: String, categoryName: String, Image: Uri)
    {
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
        var categoryList : MutableList<Array<String>> = mutableListOf<Array<String>>(
            arrayOf("CATWORK", "Work", "0"),
            arrayOf("CATPERSONAL", "Personal", "0"),
            arrayOf("CATSCHOOL", "School", "0"))

        private fun showAlert(message: String, context : Context) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(message)
            builder.setPositiveButton(R.string.Okay, null)
            val dialog = builder.create()
            dialog.show()
        }

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



    }
}