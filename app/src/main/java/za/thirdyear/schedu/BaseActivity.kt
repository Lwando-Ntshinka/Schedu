package za.thirdyear.schedu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class BaseActivity : AppCompatActivity() {

    lateinit var imgPurpleBackground : ImageButton
    lateinit var imglogo : ImageView
    lateinit var txtScheduText : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        imgPurpleBackground = findViewById(R.id.imgBackgroundImage)
        imglogo = findViewById(R.id.imageViewLogo)
        txtScheduText = findViewById(R.id.imageViewLogo)



    }

}