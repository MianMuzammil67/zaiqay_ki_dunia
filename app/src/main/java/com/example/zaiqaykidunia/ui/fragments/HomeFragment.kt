package com.example.zaiqaykidunia.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zaiqaykidunia.R
import com.example.zaiqaykidunia.databinding.FragmentHomeBinding
import com.example.zaiqaykidunia.ui.adapter.MainRvAdapter
import com.example.zaiqaykidunia.ui.viewModel.MainViewModel
import com.example.zaiqaykidunia.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainRvAdapter: MainRvAdapter
    private var TAG = "HomeFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getRecipes("main course", 10)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.recipeFlow.collect { response ->
                    when (response) {
                        is Resource.Success -> {
                            hideProgressBar()
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
        setupRecyclerView()

        mainRvAdapter.itemclickedlistener {
//            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            findNavController().navigate(R.id.action_homeFragment_to_detailActivity)
        }
    }


    private fun setupRecyclerView() {
        mainRvAdapter = MainRvAdapter()
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainRvAdapter
        }

    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

}