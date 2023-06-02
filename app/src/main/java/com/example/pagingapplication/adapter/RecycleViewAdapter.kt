package com.example.pagingapplication.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingapplication.R
import com.example.pagingapplication.databinding.ItemViewBinding

import com.example.pagingapplication.model.Model

class RecyclerAdapter : PagingDataAdapter<Model, RecyclerAdapter.ViewHolder>(ModelComparator) {

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
        val item = getItem(position)
       /* if (item != null) {
            holder.bind(item)///getItem(position) method is used to retrieve the item at the given position from the data set. If the item is not null, the bind() method is called on the ViewHolder instance to bind the data to the views.
        }*/
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Model?) {
            binding.data=model// Assign the model to the binding
            binding.executePendingBindings() // Make sure the data bindings are executed immediately
            Log.d("TAG", "$model")
        }
    }

    object ModelComparator : DiffUtil.ItemCallback<Model>() {
        override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
            return oldItem.id== newItem.id
        }

        override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
            return oldItem == newItem
        }
    }
}

class LoadStateViewHolder(private val view: View) : RecyclerView.ViewHolder(view)

class LoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.load_state_item, parent, false)
        return LoadStateViewHolder(view)
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        val progressBar = holder.itemView.findViewById<ProgressBar>(R.id.progress_bar)
        val button = holder.itemView.findViewById<Button>(R.id.retry_button)

        progressBar.isVisible = loadState is LoadState.Loading
        button.isVisible = loadState !is LoadState.Loading

        button.setOnClickListener { retry.invoke() }
    }
}
