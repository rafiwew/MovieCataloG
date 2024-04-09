package com.piwew.movieapp_cleanarchitecture.core.domain.usecase

import com.piwew.movieapp_cleanarchitecture.core.domain.model.Movie
import com.piwew.movieapp_cleanarchitecture.core.domain.repository.IMovieRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MovieInteractorTest {

    @Mock
    lateinit var movieRepository: IMovieRepository

    private lateinit var movieInteractor: MovieInteractor

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        movieInteractor = MovieInteractor(movieRepository)
    }

    @Test
    fun testSetFavoriteMovie() {
        val movie = Movie("1", "Title 1", "Overview 1", "2024-01-01", 7.5, "en", "", "", false)
        val newState = true

        movieInteractor.setFavoriteMovie(movie, newState)

        verify(movieRepository).setFavoriteMovie(movie, newState)
    }
}
