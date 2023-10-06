package com.example.zaiqaykidunia.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.zaiqaykidunia.R
import com.example.zaiqaykidunia.databinding.ActivityMainBinding
import com.example.zaiqaykidunia.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

/**       val api = RetrofitHelper.getApiInstance().create(RecipeApi::class.java)
        val repository = RecipeRepository(api)
        val factory = ViewModelFactory(repository)*/

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]
//        val nvaController = findNavController(R.id.nav_host_fragment)
//        NavigationUI.setupWithNavController(binding.bottomNavigationView,nvaController)

        val navConroller = findNavController(R.id.recipeNavHostFragment)
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navConroller)

       /**
        lifecycleScope.launch {
            viewModel.recipeFlow.collect {
                when (it) {
                    is Resource.Success -> {
                        hideProgressBar()
                    }

                    is Resource.Error -> {
                        hideProgressBar()
                    }

                    is Resource.Loading -> {
                        showProgressBar()
                    }

                }
            }
        }

        GlobalScope.launch {
            val result = api.getRecipies("main course", 10)
            if (result.body() != null) {
                Log.i("responce", result.body().toString())
//                Toast.makeText(this@MainActivity, result.body().toString(), Toast.LENGTH_SHORT).show()
            } else
                Log.i("responce", result.message().toString())
        }

            Toast.makeText(this@MainActivity, result.message().toString(), Toast.LENGTH_SHORT).show()

*/
    }
}