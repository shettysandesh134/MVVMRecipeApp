package com.example.mvvmrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.Alignment
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
import com.example.mvvmrecipeapp.presentation.components.util.SnackbarController
import com.example.mvvmrecipeapp.presentation.theme.AppTheme
import com.example.mvvmrecipeapp.presentation.ui.recipe_list.RecipeListEvent.*
import com.example.mvvmrecipeapp.util.TAG
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

    private val snacbarController = SnackbarController(lifecycleScope)

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

                /** snackbar with snackbarhoststate
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
end snack with snackbarhoststate */

                /*AppTheme Start*/
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

                    val page = viewModel.page.value

                    val scaffoldState = rememberScaffoldState()

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch =
                                {
                                    if(viewModel.selectedCategory.value?.value == "Milk"){
                                    snacbarController.getScope()
                                        .launch {
                                        snacbarController.showSnackBar(
                                            scaffoldState,
                                            message = "Invalid Category: MILK!!",
                                            actionLabel = "Hide",
                                        )
                                    }
                                }else {
                                    viewModel.onTriggerEvent(NewSearchEvent)
                                }
                                      }
                                ,
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
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }

                    ) {
                        RecipeList(
                            loading = loading,
                            recipes = recipes,
                            onChangeRecipeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                            page = page,
                            onTriggerEvent = {
                                viewModel.onTriggerEvent(NextPageEvent)
                                             },
                            scaffoldState = scaffoldState,
                            snackbarController = snacbarController,
                            navController = findNavController()
                        )
                    }
                }

            }
        }
    }
}


