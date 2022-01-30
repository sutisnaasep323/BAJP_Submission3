package com.bismillah.mymovies.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bismillah.mymovies.R
import com.bismillah.mymovies.databinding.ActivityHomeBinding
import com.bismillah.mymovies.ui.favorite.FavoriteFragment
import com.bismillah.mymovies.ui.movies.MoviesFragment
import com.bismillah.mymovies.ui.tvshows.TvShowsFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var homeBinding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)

        navigationChange(MoviesFragment())

        homeBinding.bottomNavigationContainer.setNavigationChangeListener { _, position ->
            when (position) {
                0 -> navigationChange(MoviesFragment())
                1 -> navigationChange(TvShowsFragment())
                2 -> navigationChange(FavoriteFragment())
            }
        }
    }

    private fun navigationChange(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}