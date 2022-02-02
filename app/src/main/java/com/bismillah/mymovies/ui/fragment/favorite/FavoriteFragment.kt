package com.bismillah.mymovies.ui.fragment.favorite

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bismillah.mymovies.databinding.FragmentFavoriteBinding
import com.bismillah.mymovies.ui.adapter.SectionPagerAdapter

class FavoriteFragment : Fragment() {

    private var fragmentFavoriteBinding: FragmentFavoriteBinding? = null
    private val binding get() = fragmentFavoriteBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionPagerAdapter = SectionPagerAdapter(context as Context, childFragmentManager)
        binding.viewpagerFavorite.adapter = sectionPagerAdapter
        binding.tabsFavorite.setupWithViewPager(binding.viewpagerFavorite)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentFavoriteBinding = null
    }

}