package com.ogzkesk.filmapp.presentation.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ogzkesk.filmapp.R
import com.ogzkesk.filmapp.data.dto.Search
import com.ogzkesk.filmapp.databinding.FragmentHomeBinding
import com.ogzkesk.filmapp.presentation.home.adapter.SearchAdapter
import com.ogzkesk.filmapp.util.Resource
import com.ogzkesk.filmapp.util.collectFlow
import com.ogzkesk.filmapp.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var searchAdapter: SearchAdapter
    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var mediaList: Pair<List<Search?>, List<Search?>>? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        initAdapter()
        initUI()
        observeData()
    }

    private fun initUI() = with(binding) {
        recyclerViewSearchResult.adapter = searchAdapter
        recyclerViewSearchResult.setHasFixedSize(true)
        searchBarTextBox.doAfterTextChanged { edt ->
            lifecycleScope.launch {
                delay(1000)
                edt?.let { viewModel.onSearch(it.toString()) }
            }
        }
        switchMovie.setOnCheckedChangeListener { _, b ->
            if (b) {
                viewNoResult.root.isVisible = mediaList?.second?.isEmpty() == true
                searchAdapter.submitList(mediaList?.second)
            } else {
                viewNoResult.root.isVisible = mediaList?.first?.isEmpty() == true
                searchAdapter.submitList(mediaList?.first)
            }
        }
    }

    private fun initAdapter() {
        searchAdapter = SearchAdapter()
        searchAdapter.setOnClickListener { id ->
            id?.let {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToDetailsFragment(it)
                )
            }
        }
    }


    private fun observeData() = with(binding) {
        collectFlow(viewModel.uiState) { resource ->
            when (resource) {
                Resource.Loading -> {
                    viewNoResult.root.isVisible = false
                    progressBar.isVisible = true
                }

                is Resource.Error -> {
                    progressBar.isVisible = false
                    showToast(resource.exception?.message ?: "")
                }

                is Resource.Success -> {
                    progressBar.isVisible = false

                    collectFlow(viewModel.types) {
                        mediaList = it
                        searchAdapter.submitList(it?.first)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

