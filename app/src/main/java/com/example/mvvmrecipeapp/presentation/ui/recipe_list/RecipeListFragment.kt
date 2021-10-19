package com.example.mvvmrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.mvvmrecipeapp.R
import com.example.mvvmrecipeapp.presentation.BaseApplication
import com.example.mvvmrecipeapp.presentation.components.*
import com.example.mvvmrecipeapp.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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

                /** Basic Snack bar demo
                val isShowing = remember{ mutableStateOf(false)}

                Column {
                    Button(onClick = { isShowing.value = true }) {
                        Text("Show Snackbar")
                    }
                    SnackBarDemo(
                        isShowing = isShowing.value,
                        onHideSnackBar = {
                            isShowing.value = false
                        }
                    )
                }
     EndBasic */

                val snackbarHostState = remember{SnackbarHostState()}
                Column {
                    Button(
                        onClick = {
                            lifecycleScope.launch{
                                snackbarHostState.showSnackbar(
                                    message = "Hey look a snackbar",
                                    actionLabel = "Hide",
                                    duration = SnackbarDuration.Short
                                )
                            }

                        }
                    ) {
                        Text("Show Snackbar")
                    }
                    DecoupledSnackbarDemo(snackbarHostState = snackbarHostState)

                }


                /*AppTheme Start*/
//                AppTheme(
//                    darkTheme = application.isDark.value
//                ) {
//                    /*VARIABLES*/
//                    val focusManager = LocalFocusManager.current
//                    val recipes = viewModel.recipes.value
//                    val query = viewModel.query.value
//                    val selectedCategory = viewModel.selectedCategory.value
//                    val scope = rememberCoroutineScope()
//                    val scrollState = rememberLazyListState()
//                    val loading = viewModel.loading.value   // boolean for when to display indicator
//
//                    Scaffold(
//                        topBar = {
//                            SearchAppBar(
//                                query = query,
//                                onQueryChanged = viewModel::onQueryChanged,
//                                onExecuteSearch = viewModel::newSearch,
//                                focusManager = focusManager,
//                                scrollState = scrollState,
//                                scope = scope,
//                                itemIndex = scrollState.firstVisibleItemIndex,
//                                scrollOffset = scrollState.firstVisibleItemScrollOffset,
//                                selectedCategory = selectedCategory,
//                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
//                                onChangeHorizontalScrollPosition = viewModel :: onChangeHorizontalScrollPosition,
//                                onToggleTheme = {
//                                    application.toggleLightTheme()
//                                }
//                            )
//                        }
//
//                    ) {
//                        Box (  // it overlays all its childrens
//                            modifier = Modifier
//                                .fillMaxSize()
//                                .background(color = MaterialTheme.colors.background)
//                        ){
//
//                            if (loading){
//                                ShimmerRecipeCardItem(imageHeight = 250.dp, padding = 8.dp)
//                            }else{
//                                LazyColumn(
//                                    modifier = Modifier
//                                        .padding(start = 8.dp, end = 8.dp)
//                                ){
//                                    itemsIndexed(
//                                        items = recipes
//                                    ){ index, recipe ->
//                                        RecipeCard(recipe = recipe, onClick = {})
//                                    }
//                                }
//                            }
//                            CircularIndeterminateProgressBar(isDisplayed = loading)
//                        }
//                    }
//                }
                /*AppTheme End*/
            }
        }
    }
}

@Composable
fun DecoupledSnackbarDemo(
    snackbarHostState: SnackbarHostState
){
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val snackbar = createRef()
        SnackbarHost(
            modifier = Modifier.constrainAs(snackbar){
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        },
            hostState = snackbarHostState,
            snackbar = {
                Snackbar(
                    action = {
                        TextButton(
                            onClick = {
                                snackbarHostState.currentSnackbarData?.dismiss()
                            }
                        ) {
                            Text(
                                text = snackbarHostState.currentSnackbarData?.actionLabel?:"Hide",
                                style = TextStyle(color = Color.White)
                            )
                        }
                    }
                ) {
                    Text(text = snackbarHostState.currentSnackbarData?.message?:"Hey Look a Snackbar")
                }
            }
        )
    }
}



@Composable
fun SnackBarDemo(
    isShowing: Boolean,
    onHideSnackBar: () -> Unit
){
    if (isShowing){
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val snackbar = createRef()
            Snackbar(modifier = Modifier.constrainAs(snackbar){
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
                action = {
                    Text(
                        text = "Hide",
                        modifier = Modifier.clickable(
                            onClick = onHideSnackBar
                        ),
                        style = MaterialTheme.typography.h5
                    )
                }
            ) {
                Text(text = "Hey look a Snackbar")
            }
        }
    }

}
