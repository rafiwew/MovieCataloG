package com.piwew.movieapp_cleanarchitecture.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.piwew.movieapp_cleanarchitecture.core.ui.MovieAdapter
import com.piwew.movieapp_cleanarchitecture.detail.MovieDetailActivity
import com.piwew.movieapp_cleanarchitecture.favorite.databinding.ActivityFavoriteBinding
import com.piwew.movieapp_cleanarchitecture.favorite.di.favoriteModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val movieAdapter = MovieAdapter()
    private val favoriteViewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadKoinModules(favoriteModule)
        binding.ivActionBack.setOnClickListener { onSupportNavigateUp() }
        observeFavoriteMovieData()
        setupRecycleView()
    }

    private fun observeFavoriteMovieData() {
        favoriteViewModel.favoriteMovie.observe(this) { favoriteItem ->
            movieAdapter.submitList(favoriteItem)
            binding.viewEmpty.root.visibility = if (favoriteItem.isNotEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun setupRecycleView() {
        movieAdapter.onItemClick = { selectedItem ->
            startActivity(
                Intent(this@FavoriteActivity, MovieDetailActivity::class.java)
                    .apply { putExtra(MovieDetailActivity.EXTRA_DATA, selectedItem) }
            )
        }

        with(binding.rvFavoriteMovie) {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            setHasFixedSize(false)
            adapter = movieAdapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}