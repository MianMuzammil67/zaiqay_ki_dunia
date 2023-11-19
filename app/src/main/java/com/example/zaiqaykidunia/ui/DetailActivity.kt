package com.example.zaiqaykidunia.ui

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.zaiqaykidunia.R
import com.example.zaiqaykidunia.databinding.ActivityDetailBinding
import com.example.zaiqaykidunia.network.Recipe
import com.example.zaiqaykidunia.network.Step
import com.example.zaiqaykidunia.ui.adapter.InstructionAdapter
import com.example.zaiqaykidunia.ui.adapter.ItemAdapter
import com.example.zaiqaykidunia.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.parcelize.RawValue
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val args: DetailActivityArgs by navArgs()
    private lateinit var ingredientAdapter : ItemAdapter
    private lateinit var instructionAdapter: InstructionAdapter
    private val TAG = "DetailActivity"
    private var savedRecipeFlag:Boolean =false
    private lateinit var viewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this@DetailActivity)[MainViewModel::class.java]


        setupIngredientRecyclerView()
        setupInstructionRecyclerView()

        args.recipe.let { recipes->
            viewModel.toggleFavoriteStatus(recipes)
            binding.apply {
                tvTittle.text = recipes.title
                val time = recipes.readyInMinutes.toString()
                tvTime.text = "$time mins"
                val servings = recipes.servings.toString()
                tvServings.text = "$servings serve"
                val healthScore = recipes.healthScore.toString()
                tvHealthScore.text = "$healthScore healthScore"

                crdSource.setOnClickListener {
                    val uri = Uri.parse(recipes.sourceUrl)
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                }
                crdShare.setOnClickListener {
                    val recipe = recipes.summary
                    Glide.with(this@DetailActivity)
                        .asBitmap()
                        .load(recipes.image)
                        .into(object : CustomTarget<Bitmap>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                            ) {
                                val shareIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        recipe
                                    )
                                    type = "text/plain"
                                    putExtra(
                                        Intent.EXTRA_STREAM,
                                        getImageUri(this@DetailActivity, resource)
                                    )
                                    type = "image/*"
                                }
                                startActivity(Intent.createChooser(shareIntent, "Share using"))
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {
                            }
                        })
                }
    //                crdSave.setOnClickListener{
    //                    viewModel.toggleFavoriteStatus(recipes)
    //                    Toast.makeText(requireContext(), "saved", Toast.LENGTH_SHORT).show()
    //                }

                Glide.with(this@DetailActivity).load(recipes.image)
                    .placeholder(R.drawable.progress_animation).centerCrop()
                    .into(binding.detailImage)

                ingredientAdapter.differ.submitList(recipes.extendedIngredients)
                instructionAdapter.differ.submitList(recipes.analyzedInstructions[0].steps as @RawValue List<Step>)
            }

            /**
            //            viewLifecycleOwner.lifecycleScope.launch{
            //                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
            //                    viewModel.getFavoriteRecipes().collect{recipe->
            //                        for(savedRecipe in recipe){
            //                            if (savedRecipe.id == recipes.id){
            //                                binding.crdSave.setCardBackgroundColor(Color.parseColor("#198754"))
            //                                savedRecipeFlag= true
            //
            //                            }
            //
            //
            //                        }
            //                    }
            //                }
            //            }
             **/

            checkRecipeFavStatus(recipes)

            binding.crdSave.setOnClickListener {

    //               val savedRecipes = viewModel.getLocalRecipes().first()
    //               val savedRecipe = savedRecipes.find { it.id == recipe.id}
    //               savedRecipe?.let {
    //                   it.favourite= !it.favourite
    //                   viewModel.upsertFavourite(it)
    //               }


                if (savedRecipeFlag) {
                    updateIcon(binding.imgSaved, R.color.transparent)
                    Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show()
                } else {
                    updateIcon(binding.imgSaved, R.color.yellow)
                    Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
    private fun checkRecipeFavStatus(recipes: Recipe){
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.getFavoriteRecipes().collect{recipe->
                    for(savedRecipe in recipe){
                        savedRecipeFlag = if (savedRecipe.id == recipes.id){
                            updateIcon(binding.imgSaved,R.color.yellow)
                            true
                        }else{
                            false
                        }
                    }
                }
            }
        }
    }

    fun getImageUri(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path =
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }
    private fun updateIcon(icon: ImageView, color: Int) {
        icon.setColorFilter(ContextCompat.getColor(this, color))
    }
    private fun setupIngredientRecyclerView() {
        ingredientAdapter = ItemAdapter()
        binding.rvIngredients.apply {
            adapter = ingredientAdapter
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }
    private fun setupInstructionRecyclerView() {
        instructionAdapter = InstructionAdapter()
        binding.rvSteps.apply {
            adapter = instructionAdapter
            layoutManager = LinearLayoutManager(this@DetailActivity)
        }




    }



}