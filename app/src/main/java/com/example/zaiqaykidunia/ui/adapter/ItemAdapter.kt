package com.example.zaiqaykidunia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zaiqaykidunia.R
import com.example.zaiqaykidunia.databinding.IngredientSampleViewBinding
import com.example.zaiqaykidunia.network.ExtendedIngredient
import com.example.zaiqaykidunia.utils.Constants.Companion.BASE_IMAGE_URL

class ItemAdapter() : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            IngredientSampleViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentList = differ.currentList[position]
        holder.itemView.apply {
//            Glide.with(this).load(BASE_IMAGE_URL + currentList.image)
//                .placeholder(R.drawable.progress_animation).into(holder.binding.itemImage,)
            Glide.with(this).load(BASE_IMAGE_URL + currentList.image)
                .placeholder(R.drawable.progress_animation).into(holder.binding.backItemImage,)
        }
        holder.binding.apply {
//            tvTittle.isSelected = true
            tvTittle.text = currentList.name
            tvAmount.text = currentList.amount.toString()+ "\t" + currentList.unit
            tvConsistency.text = currentList.consistency
        }
    }
    private val diffUtilCallback = object : DiffUtil.ItemCallback<ExtendedIngredient>() {
        override fun areItemsTheSame(
            oldItem: ExtendedIngredient,
            newItem: ExtendedIngredient
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ExtendedIngredient,
            newItem: ExtendedIngredient
        ): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffUtilCallback)

    inner class ViewHolder(var binding: IngredientSampleViewBinding) :
        RecyclerView.ViewHolder(binding.root)

}