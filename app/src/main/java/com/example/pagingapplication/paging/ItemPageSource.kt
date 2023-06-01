package com.example.pagingapplication.paging



import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingapplication.api.ApiService
import com.example.pagingapplication.model.Model

class ItemPagingSource(private val apiService: ApiService) : PagingSource<Int, Model>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Model> {
        return try {
            val page = params.key ?:STARTING_PAGE_INDEX
            val pageSize = params.loadSize
            val response = apiService.getItems(page, pageSize)
            LoadResult.Page(
                data = response,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, Model>):  Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}
