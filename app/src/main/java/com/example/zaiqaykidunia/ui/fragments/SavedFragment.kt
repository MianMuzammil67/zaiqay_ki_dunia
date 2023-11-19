package com.example.zaiqaykidunia.ui.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zaiqaykidunia.R
import com.example.zaiqaykidunia.databinding.FragmentSavedBinding
import com.example.zaiqaykidunia.ui.adapter.MainRvAdapter
import com.example.zaiqaykidunia.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SavedFragment : Fragment(R.layout.fragment_saved) {

   private lateinit var binding: FragmentSavedBinding
   private lateinit var rvAdapter : MainRvAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setupRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch{
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getFavoriteRecipes().collect{recipe->
                    rvAdapter.differ.submitList(recipe)
                }
            }
        }

        rvAdapter.itemclickedlistener {
            val action = SavedFragmentDirections.actionSavedFragmentToDetailActivity(it)
            findNavController().navigate(action)
        }

    }
    private fun setupRecyclerView() {
        rvAdapter = MainRvAdapter()
        binding.savedRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = rvAdapter
        }
    }


}