package za.thirdyear.schedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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

                R.id.nav_home->{
                    val moveIntent = Intent(this, MainActivity::class.java)

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

                R.id.nav_create_project->{
                    val moveIntent = Intent(this, CreateProjects::class.java)
                    startActivity(moveIntent)
                    true
                }

                else -> false
            }
        }
    }
}

