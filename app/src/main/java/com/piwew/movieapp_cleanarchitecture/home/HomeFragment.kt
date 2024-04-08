package com.piwew.movieapp_cleanarchitecture.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.piwew.movieapp_cleanarchitecture.R
import com.piwew.movieapp_cleanarchitecture.core.data.Resource
import com.piwew.movieapp_cleanarchitecture.core.ui.MovieAdapter
import com.piwew.movieapp_cleanarchitecture.databinding.FragmentHomeBinding
import com.piwew.movieapp_cleanarchitecture.detail.MovieDetailActivity
import com.piwew.movieapp_cleanarchitecture.detail.MovieDetailActivity.Companion.EXTRA_DATA
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val movieAdapter = MovieAdapter()
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        if (activity != null) {
            setupRecycleView()
            observeMovieData()

            binding.toolbar.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_favorite -> {
                        val uri = Uri.parse("movieapp_cleanarchitecture://favorites")
                        startActivity(Intent(Intent.ACTION_VIEW, uri))
                        true
                    }

                    else -> false
                }
            }
        }

        return binding.root
    }

    private fun setupRecycleView() {
        movieAdapter.onItemClick = { selectedItem ->
            startActivity(Intent(activity, MovieDetailActivity::class.java)
                .apply { putExtra(EXTRA_DATA, selectedItem) }
            )
        }

        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(false)
            adapter = movieAdapter
        }
    }

    private fun observeMovieData() {
        homeViewModel.movie.observe(requireActivity()) { result ->
            if (_binding != null) {
                showLoading(result is Resource.Loading)
                when (result) {
                    is Resource.Success -> movieAdapter.submitList(result.data)
                    is Resource.Error -> showErrorMessage(result.message)
                    else -> {}
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showErrorMessage(errorMessage: String?) {
        with(binding.viewError) {
            root.visibility = View.VISIBLE
            tvError.text = errorMessage ?: getString(R.string.something_wrong)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}