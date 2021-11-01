package com.example.mvvmrecipeapp.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmrecipeapp.domain.model.Recipe
import com.example.mvvmrecipeapp.presentation.ui.recipe.RecipeEvent.*
import com.example.mvvmrecipeapp.repository.RecipeRepository
import com.example.mvvmrecipeapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val STATE_KEY_RECIPE = "state.key.recipeId"

@HiltViewModel
class RecipeViewModel
@Inject
constructor(
    private val recipeRepository: RecipeRepository,
    private @Named("auth_token") val token: String,
    private val savedStateHandle: SavedStateHandle
):ViewModel(){

    val recipe: MutableState<Recipe?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    init {
        //restore if process dies
        savedStateHandle.get<Int>(STATE_KEY_RECIPE)?.let { recipeId->
            onTriggerEvent(GetRecipeEvent(recipeId))
        }
    }

    fun onTriggerEvent(event: RecipeEvent){
        viewModelScope.launch {
            try {

                when (event){
                    is GetRecipeEvent -> {
                        getRecipe(event.id)
                    }
                }

            }catch (e: Exception){
                Log.e(TAG, "onTriggerEvent: Exception ${e}, ${e.cause}")
            }
        }
    }

    private suspend fun getRecipe(id: Int){
        loading.value = true

        //simulate a delay
        delay(1000)

        val recipe = recipeRepository.get(
            token = token,
            id = id
        )
        this.recipe.value = recipe
        savedStateHandle.set(STATE_KEY_RECIPE, recipe.id)
        loading.value = false
    }

}