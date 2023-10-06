package com.example.zaiqaykidunia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zaiqaykidunia.databinding.SampleItemBinding
import com.example.zaiqaykidunia.network.Recipe

class MainRvAdapter() : RecyclerView.Adapter<MainRvAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
return ViewHolder(SampleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentList = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this).load(currentList.image).into(holder.binding.thumImage)
            setOnClickListener {
                _itemClicked?.let {it(currentList)  }
            }
        }
        holder.binding.apply {
            val time = currentList.readyInMinutes
            tvTittle.text = currentList.title
            "$time Mins".also { tvTime.text = it }
        }
    }
   private var _itemClicked :((Recipe)-> Unit)? = null
    fun itemclickedlistener(listener : (Recipe)-> Unit){
        _itemClicked = listener
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
    private val diffUtillCallback = object : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, diffUtillCallback)
   inner class ViewHolder(val binding: SampleItemBinding) : RecyclerView.ViewHolder(binding.root)
}

