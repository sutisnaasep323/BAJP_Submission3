package com.bismillah.mymovies.ui.favorite

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bismillah.mymovies.R
import com.bismillah.mymovies.ui.favorite.movies.FavoriteMoviesFragment
import com.bismillah.mymovies.ui.favorite.tvshows.FavoriteTvShowsFragment

class SectionPagerAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getPageTitle(position: Int): CharSequence {
        return mContext.getString(tabTitles[position])
    }

    private val fragment: List<Fragment> = listOf(
        FavoriteMoviesFragment(),
        FavoriteTvShowsFragment()
    )

    @StringRes
    val tabTitles = intArrayOf(
        R.string.movies,
        R.string.tv_show
    )

    override fun getCount() = tabTitles.size

    override fun getItem(position: Int): Fragment {
        return fragment[position]
    }
}