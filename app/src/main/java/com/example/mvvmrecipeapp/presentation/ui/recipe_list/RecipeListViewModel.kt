package com.example.mvvmrecipeapp.presentation.ui.recipe_list

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmrecipeapp.domain.model.Recipe
import com.example.mvvmrecipeapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val repository: RecipeRepository,
    private @Named("auth_token") val token: String   // to differentiate a particular string(for e.g. "auth_token") in dependency injection
): ViewModel(){

   val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())

    val query = mutableStateOf("")

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    var itemIndex: Int = 0
    var scrollOffset: Int = 0

    val loading = mutableStateOf(false)

    init {
        newSearch()

    }

    fun newSearch(){
        viewModelScope.launch {
            loading.value = true
            resetSearchState()
            delay(3000)
            val result = repository.search(
                token = token,
                page = 1,
                query = query.value
            )
            recipes.value = result
            loading.value = false
        }

    }

    private fun resetSearchState(){
        recipes.value = listOf()
        if (selectedCategory.value?.value != query.value)
            clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onChangeHorizontalScrollPosition(lazyListState: LazyListState) {
        itemIndex = lazyListState.firstVisibleItemIndex
        scrollOffset = lazyListState.firstVisibleItemScrollOffset
    }

}