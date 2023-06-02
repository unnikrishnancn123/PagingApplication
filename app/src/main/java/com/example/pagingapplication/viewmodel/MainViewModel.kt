package com.example.pagingapplication.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagingapplication.paging.ItemPagingSource
import com.example.pagingapplication.api.ApiService
import com.example.pagingapplication.model.Model
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val apiService: ApiService) : ViewModel() {

    fun getItems(): Flow<PagingData<Model>> {
        return Pager(PagingConfig(pageSize =20, prefetchDistance = 10,  enablePlaceholders = false,initialLoadSize = 1), pagingSourceFactory = { ItemPagingSource(apiService) })
            .flow
            .cachedIn(viewModelScope)
    }



}
