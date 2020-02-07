package app.shynline.jikanium

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import app.shynline.jikanium.data.requests.bygenre.GenreRepository
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class JikanActivity : DaggerAppCompatActivity() {

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            findNavController(R.id.nav_host_fragment).navigateUp()
        } else if (item?.itemId == R.id.navigation_genreListFragment) {
            findNavController(R.id.nav_host_fragment).navigate(R.id.navigation_genreListFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    @Inject
    lateinit var genreRepository: GenreRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jikan)
        setSupportActionBar(findViewById(R.id.toolbar))
//        findViewById<Toolbar>(R.id.appbar).setTitleTextColor(Color.WHITE)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_animeListByGenreFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        MenuInflater(this).inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}