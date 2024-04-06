package com.piwew.movieapp_cleanarchitecture.favorite.di

import com.piwew.movieapp_cleanarchitecture.favorite.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { FavoriteViewModel(get()) }
}