package com.example.pagingapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingapplication.R
import com.example.pagingapplication.adapter.RecyclerAdapter
import com.example.pagingapplication.api.ApiService
import com.example.pagingapplication.api.RetrofitClient
import com.example.pagingapplication.databinding.ActivityMainBinding
import com.example.pagingapplication.viewmodel.MainViewModel
import com.example.pagingapplication.viewmodel.MainViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var mainRecycler: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerAdapter: RecyclerAdapter
    private   lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainRecycler = binding.recycler
        apiService = RetrofitClient.getClient()

        val factory = MainViewModelFactory(apiService)
        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        recyclerAdapter = RecyclerAdapter()
        mainRecycler.layoutManager = LinearLayoutManager(this)
        mainRecycler.adapter = recyclerAdapter
        fetchData()
    }
    private fun fetchData() {
        lifecycleScope.launch {
            viewModel.getItems().collectLatest { pagingData ->
                recyclerAdapter.submitData(pagingData)
            }
        }
    }

}
