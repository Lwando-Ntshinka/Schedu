package za.thirdyear.schedu

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

abstract class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var toolbar: Toolbar
    lateinit var imgPurpleBackground: ImageView
    lateinit var imgLogo: ImageView
    lateinit var txtScheduText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        // Initialize views
        imgPurpleBackground = findViewById(R.id.imgBackgroundImage)
        imgLogo = findViewById(R.id.imageViewLogo)
        txtScheduText = findViewById(R.id.textViewName)
        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)

        // Set up toolbar and navigation drawer
        setupToolbar()
        setupNavigationDrawer()
    }

    private fun setupToolbar() {
        Log.d("BaseActivity", "setupToolbar called")
        setSupportActionBar(toolbar)
    }

    private fun setupNavigationDrawer() {
        Log.d("BaseActivity", "setupNavigationDrawer called")
        navigationView.setNavigationItemSelectedListener(this)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.scedu_menu, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("BaseActivity", "onNavigationItemSelected called")
        when (item.itemId) {
            R.id.nav_home -> startActivity(Intent(this, MainActivity::class.java))
            R.id.nav_view_categories -> startActivity(Intent(this, ViewCategoriesActivity::class.java))
            R.id.nav_display_details -> startActivity(Intent(this, DisplayDetails::class.java))
            R.id.nav_create_project -> startActivity(Intent(this, CreateProjects::class.java))
            R.id.nav_focus_timer -> startActivity(Intent(this, TimerActivity::class.java))
            R.id.nav_logout -> startActivity(Intent(this, LoginActivity::class.java))
            else -> return false
        }
        closeDrawer()
        return true
    }

    private fun closeDrawer() {
        drawerLayout.closeDrawer(GravityCompat.START)
    }

    @LayoutRes
    abstract fun getLayoutResourceId(): Int

    public fun ShowAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(message)
        builder.setPositiveButton(R.string.Okay, null)
        val dialog = builder.create()
        dialog.show()
    }

    public fun ShowToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
