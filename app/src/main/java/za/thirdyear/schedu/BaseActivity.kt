package za.thirdyear.schedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

//Indicate this class as parent class
abstract class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //Controls Declaration
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar
    lateinit var imgPurpleBackground : ImageView
    lateinit var imglogo : ImageView
    lateinit var txtScheduText : TextView


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        imgPurpleBackground = findViewById(R.id.imgBackgroundImage)
        imglogo = findViewById(R.id.imageViewLogo)
        txtScheduText = findViewById(R.id.textViewName)

        setupToolbar()
        setupNavigationDrawer()
    }

    //Navigation Drawer
    private fun setupNavigationDrawer() {
        Log.d("BaseActivity", "setupNavigationDrawer called")
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.navigationView)
        navView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    //Tool Bar
    private fun setupToolbar() {
        Log.d("BaseActivity", "setupToolbar called")
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    //Create Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.scedu_menu, menu)
        return true
    }

    //Method implemented from
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("BaseActivity", "onNavigationItemSelected called")
        when(item.itemId)
        {
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

            R.id.nav_create_project -> {

                val moveIntent = Intent(this, CreateProjects::class.java)
                startActivity(moveIntent)
                true
            }

            R.id.nav_focus_timer->{
                val moveIntent = Intent(this, TimerActivity::class.java)
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
        closeDrawer()
        return true
    }

    //Close Drawer
    private fun closeDrawer() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    @LayoutRes
    abstract fun getLayoutResourceId(): Int
    /*
    * Code for implementing the above member (Copy and Paste):
    override fun getLayoutResourceId(): Int {
        return R.layout.activity_base
    }
    * */

    /******Show Message Alert ******/
    public fun ShowAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.ok_button_title, null)

        val dialog = builder.create()
        dialog.show()
    }

    public fun ShowToast(message: String)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}