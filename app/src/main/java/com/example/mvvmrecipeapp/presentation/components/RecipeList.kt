package com.example.mvvmrecipeapp.presentation.components

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mvvmrecipeapp.R
import com.example.mvvmrecipeapp.domain.model.Recipe
import com.example.mvvmrecipeapp.presentation.components.util.SnackbarController
import com.example.mvvmrecipeapp.presentation.ui.recipe_list.PAGE_SIZE
import com.example.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent
import com.example.mvvmrecipeapp.util.TAG
import kotlinx.coroutines.launch

@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    page: Int,
    onTriggerEvent: (RecipeListEvent) -> Unit,
    scaffoldState: ScaffoldState,
    snackbarController: SnackbarController,
    navController: NavController
) {
    Box (  // it overlays all its childrens
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ){

        if (loading && recipes.isEmpty()){
            ShimmerRecipeCardItem(imageHeight = 250.dp, padding = 8.dp)
        }else{
            LazyColumn(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
            ){
                itemsIndexed(
                    items = recipes
                ){ index, recipe ->
                    onChangeRecipeScrollPosition(index)
                    Log.d(TAG, "nextPage: ${index}${loading}")
                    if ((index + 1) >= (page * PAGE_SIZE) && !loading){
                        onTriggerEvent(RecipeListEvent.NextPageEvent)
                    }
                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            if (recipe.id != null){
                                val bundle = Bundle()
                                bundle.putInt("recipeId", recipe.id)
                                navController.navigate(R.id.viewRecipe, bundle)
                            }else{
                                snackbarController.getScope().launch {
                                    snackbarController.showSnackBar(
                                        scaffoldState = scaffoldState,
                                        message = "RecipeError",
                                        actionLabel = "OK"
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
        CircularIndeterminateProgressBar(isDisplayed = loading)
        DefaultSnackbar(
            snackbarHostState = scaffoldState.snackbarHostState,
            onDismiss = {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}