package com.example.zaiqaykidunia.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.zaiqaykidunia.databinding.InstructionSampleViewBinding
import com.example.zaiqaykidunia.network.Step

class InstructionAdapter : RecyclerView.Adapter<InstructionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            InstructionSampleViewBinding.inflate(
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
        holder.binding.apply {
            val no = currentList.number.toString()
            tvStepNo.text = "Step $no"
            tvStep.text = currentList.step
        }
    }
    private val diffUtilCalback = object : DiffUtil.ItemCallback<Step>() {
        override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean {
            return oldItem.number == newItem.number
        }

        override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean {
            return oldItem == newItem
        }


    }
    val differ = AsyncListDiffer(this, diffUtilCalback)

    class ViewHolder(val binding: InstructionSampleViewBinding) :
        RecyclerView.ViewHolder(binding.root)

}