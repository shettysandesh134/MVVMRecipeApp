package com.example.mvvmrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mvvmrecipeapp.presentation.BaseApplication
import com.example.mvvmrecipeapp.presentation.components.*
import com.example.mvvmrecipeapp.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment: Fragment() {

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = ComposeView(requireContext())
//        view.apply {
//            setContent {
//                Text(text = "Hey look a composable")
//            }
//        }
//        return view
//    }

    @Inject
    lateinit var application: BaseApplication

    val viewModel: RecipeListViewModel by viewModels()

    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                AppTheme(
                    darkTheme = application.isDark.value
                ) {
                    /*VARIABLES*/
                    val focusManager = LocalFocusManager.current
                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val scope = rememberCoroutineScope()
                    val scrollState = rememberLazyListState()
                    val loading = viewModel.loading.value   // boolean for when to display indicator

                    Column {

                        SearchAppBar(
                            query = query,
                            onQueryChanged = viewModel::onQueryChanged,
                            onExecuteSearch = viewModel::newSearch,
                            focusManager = focusManager,
                            scrollState = scrollState,
                            scope = scope,
                            itemIndex = scrollState.firstVisibleItemIndex,
                            scrollOffset = scrollState.firstVisibleItemScrollOffset,
                            selectedCategory = selectedCategory,
                            onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                            onChangeHorizontalScrollPosition = viewModel :: onChangeHorizontalScrollPosition,
                            onToggleTheme = {
                                application.toggleLightTheme()
                            }
                        )




                        Box (  // it overlays all its childrens
                            modifier = Modifier.fillMaxSize()
                                .background(color = MaterialTheme.colors.background)
                        ){

                            if (loading){
                                ShimmerRecipeCardItem(imageHeight = 250.dp, padding = 8.dp)
                            }else{
                                LazyColumn(
                                    modifier = Modifier
                                        .padding(start = 8.dp, end = 8.dp)
                                ){
                                    itemsIndexed(
                                        items = recipes
                                    ){ index, recipe ->
                                        RecipeCard(recipe = recipe, onClick = {})
                                    }
                                }
                            }
                            CircularIndeterminateProgressBar(isDisplayed = loading)
                        }

                    }
                }



            }
        }
    }
}

@Composable
fun GradientDemo(){
    val colors = listOf(
        Color.Blue,
        Color.Red,
        Color.Blue
    )

//    val brush = linear

}