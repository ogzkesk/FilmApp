package com.ogzkesk.filmapp.presentation.details

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ogzkesk.filmapp.R
import com.ogzkesk.filmapp.databinding.FragmentDetailsBinding
import com.ogzkesk.filmapp.domain.model.FilmModel
import com.ogzkesk.filmapp.util.Resource
import com.ogzkesk.filmapp.util.collectFlow
import com.ogzkesk.filmapp.util.showImage
import com.ogzkesk.filmapp.util.showToast
import dagger.hilt.android.AndroidEntryPoint
private const val ID_ARG = "imdbID"

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details){

    private val viewModel: DetailsViewModel by viewModels()
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)
        fetchFilm()
        observeData()
    }

    private fun fetchFilm(){
        val id = arguments?.getString(ID_ARG)
        viewModel.getFilmById(id)
    }

    private fun initUI(data: FilmModel) = with(binding) {
        btnBack.setOnClickListener { findNavController().popBackStack() }
        this.textViewTitleDetail.text = data.title
        this.imageViewBackgroundDetailPoster.showImage(data.poster)
        this.imageViewForegroundDetailPoster.showImage(data.poster)
        this.textViewReleaseDateDetail.text = data.released
        this.textViewDuration.text = data.runtime
        this.textViewGenreDetail.text = parseGenre(data.genre)
        this.textViewRatingDetail.text = data.imdbRating
        this.textViewOverview.text = data.plot
        this.tvCast.text = data.actors

    }

    private fun observeData()= with(binding){
        collectFlow(viewModel.uiState){ resource ->
            when(resource){
                Resource.Loading -> {
                    progressBar.isVisible = true
                }
                is Resource.Error -> {
                    progressBar.isVisible = false
                    showToast(resource.exception?.message ?: "")
                }
                is Resource.Success -> {
                    progressBar.isVisible = false
                    initUI(resource.data)
                }
            }
        }
    }


    private fun parseGenre(genre:String?): String?{
        return genre?.split(",")?.first()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}