package com.example.zaiqaykidunia.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zaiqaykidunia.R
import com.example.zaiqaykidunia.databinding.FragmentSearchBinding
import com.example.zaiqaykidunia.ui.adapter.MainRvAdapter
import com.example.zaiqaykidunia.ui.viewModel.MainViewModel
import com.example.zaiqaykidunia.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var mainRvAdapter: MainRvAdapter
    private val TAG = "Search Fragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        showSearchAnim()
        hideProgressBar()
        setupRecyclerView()
        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500)
                editable.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchRecipe(editable.toString(), 30)
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.searchFlow.collect() { response ->
                        when (response) {
                            is Resource.Success -> {
                                hideProgressBar()
                                hideSearchAnim()
                                response.data?.let {
                                    mainRvAdapter.differ.submitList(it.recipes)
                                }
                            }

                            is Resource.Loading -> {
                                showProgressBar()
                            }

                            is Resource.Error -> {
                                hideProgressBar()
                                response.data?.let {
                                    Log.i(TAG, "an error occur: $it")
                                }
                            }
                        }
                    }
                }
            }
        }
        mainRvAdapter.itemclickedlistener {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailActivity(it)
            findNavController().navigate(action)
        }
    }
    private fun setupRecyclerView() {
        mainRvAdapter = MainRvAdapter()
        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainRvAdapter
        }
    }
    private fun showProgressBar() {
        binding.searchProgressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        binding.searchProgressBar.visibility = View.INVISIBLE
    }
    private fun showSearchAnim() {
        binding.animView.visibility = View.VISIBLE
        binding.animView.playAnimation()
    }
    private fun hideSearchAnim() {
        binding.animView.visibility = View.INVISIBLE
    }
}