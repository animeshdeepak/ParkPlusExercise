package com.example.parkplusexercise.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parkplusexercise.databinding.RepoItemBinding
import com.example.parkplusexercise.model.Item

class RepoItemAdapter(private var itemList: ArrayList<Item>) :
    RecyclerView.Adapter<RepoItemAdapter.RepoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RepoItemBinding.inflate(inflater, parent, false)
        return RepoItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) = holder.bind()

    override fun getItemCount() = itemList.size

    fun addItem(_itemList: ArrayList<Item>) {
        itemList = _itemList
        notifyDataSetChanged()
    }

    inner class RepoItemViewHolder(private val binding: RepoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val perItem = itemList[bindingAdapterPosition]
            binding.apply {
                item = perItem
                executePendingBindings()
            }
        }
    }
}