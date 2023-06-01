package com.example.pagingapplication.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingapplication.R
import com.example.pagingapplication.databinding.ItemViewBinding
import com.example.pagingapplication.model.Model

class RecyclerAdapter: PagingDataAdapter<Model, RecyclerAdapter.ViewHolder>(ModelComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemViewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_view,
            parent,
            false
        )
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }
    inner class ViewHolder(private var binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Model) {
            binding.title.text= model.title
            Log.d("TAG","$model")
        }
    }
    object ModelComparator : DiffUtil.ItemCallback<Model>() {
        override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
            return oldItem== newItem
        }

        override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
            return oldItem == newItem
        }
    }
}
