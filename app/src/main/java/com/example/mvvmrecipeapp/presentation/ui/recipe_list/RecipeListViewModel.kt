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
import com.example.mvvmrecipeapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

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

    val page = mutableStateOf(1)

    private var recipeScrollPosition = 0

    init {
        newSearch()

    }

    fun newSearch(){
        viewModelScope.launch {
            loading.value = true
            resetSearchState()
//            delay(3000)
            val result = repository.search(
                token = token,
                page = 1,
                query = query.value
            )
            recipes.value = result
            loading.value = false
        }

    }

    fun nextPage(){
        viewModelScope.launch {
            // prevent duplicate events due to recompose happening too quickly
            if ((recipeScrollPosition + 1) >= (page.value * PAGE_SIZE)){
                loading.value = true
                incrementPage()
                Log.d(TAG, "nextPage: triggered: ${page.value}")

                //just to show pagination, api is fast
                delay(1000)

                if (page.value > 1){
                    val result = repository.search(
                        token = token,
                        page = page.value,
                        query = query.value
                    )
                    Log.d(TAG, "nextPage: ${result}")
                    appendRecipes(result)
                }
                loading.value = false
            }
        }
    }

    /**
     *  Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipes: List<Recipe>){
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage(){
        page.value = page.value +1
    }

    fun onChangeRecipeScrollPosition(position: Int){
        recipeScrollPosition = position
    }


    private fun resetSearchState(){
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
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