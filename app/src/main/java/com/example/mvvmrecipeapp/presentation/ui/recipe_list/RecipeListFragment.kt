package com.example.mvvmrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.mvvmrecipeapp.R
import com.example.mvvmrecipeapp.presentation.components.RecipeCard
import com.example.mvvmrecipeapp.util.TAG
import dagger.hilt.android.AndroidEntryPoint

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

    val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val recipes = viewModel.recipes.value

                val query = viewModel.query.value

                Column {

                    TextField(
                        value = query,
                        onValueChange = { newValue->
                            viewModel.onQueryChanged(newValue)
                        }
                    )

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

            }
        }
    }

}