package com.example.mvvmrecipeapp.presentation.ui.recipe_list

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmrecipeapp.domain.model.Recipe
import com.example.mvvmrecipeapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    val query = mutableStateOf("chicken")

    init {
       newSearch()
    }

    fun newSearch(){
        viewModelScope.launch {
            val result = repository.search(
                token = token,
                page = 1,
                query = "chicken"
            )
            recipes.value = result
        }
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

}