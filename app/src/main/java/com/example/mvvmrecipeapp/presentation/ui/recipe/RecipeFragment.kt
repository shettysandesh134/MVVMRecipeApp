package com.example.mvvmrecipeapp.presentation.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mvvmrecipeapp.presentation.BaseApplication
import com.example.mvvmrecipeapp.presentation.components.CircularIndeterminateProgressBar
import com.example.mvvmrecipeapp.presentation.components.DefaultSnackbar
import com.example.mvvmrecipeapp.presentation.components.RecipeView
import com.example.mvvmrecipeapp.presentation.components.util.SnackbarController
import com.example.mvvmrecipeapp.presentation.theme.AppTheme
import com.example.mvvmrecipeapp.presentation.ui.recipe.RecipeEvent.*
import com.example.mvvmrecipeapp.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment: Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val snackbarController = SnackbarController(lifecycleScope)

   private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let { id ->
            viewModel.onTriggerEvent(GetRecipeEvent(id))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val loading = viewModel.loading.value
                val recipe = viewModel.recipe.value

                val scaffoldState = rememberScaffoldState()

                AppTheme(darkTheme = application.isDark.value) {

                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        }
                    ) {

                        Box(modifier = Modifier.fillMaxSize()){
                            if (loading && recipe == null) {
                                Text(text = "Loading....")
                            }else{
                                recipe?.let { recipe ->
                                    if (recipe.id == 1){
                                        snackbarController.showSnackBar(
                                            scaffoldState = scaffoldState,
                                            message = "Error occcured",
                                            actionLabel = "OK"
                                        )
                                    }else{
                                        RecipeView(recipe = recipe)
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
                }
            }
        }
    }

}