package com.example.mvvmrecipeapp.presentation.components

import android.util.Log
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.mvvmrecipeapp.R
import com.example.mvvmrecipeapp.util.loadPicture


enum class BoxState { Collapsed, Expanded }

@Composable
fun AnimatingBox(boxState: BoxState) {

    val transitionData = updateTransitionData(boxState)
    // UI tree
//    Box(
//        modifier = Modifier
//            .background(transitionData.color)
//            .size(transitionData.size)
//    )
    Log.d("ANimated",transitionData.size.toString()+":"+boxState)

    if (boxState == BoxState.Collapsed){
        loadPicture(defaultImage = R.drawable.heart_grey).value?.let { image->
            Image(
                bitmap = image.asImageBitmap(),
                modifier = Modifier
                    .height(transitionData.size)
                    .width(transitionData.size),
                contentDescription = ""
            )
        }
    } else {
        loadPicture(defaultImage = R.drawable.heart_red).value?.let { image->
            Image(
                bitmap = image.asImageBitmap()
                , contentDescription = "",
                modifier = Modifier
                    .height(transitionData.size)
                    .width(transitionData.size)
            )
        }
    }

}

// Holds the animation values.
private class TransitionData(
    color: State<Color>,
    size: State<Dp>
) {
    val color by color
    val size by size
}

// Create a Transition and return its animation values.
@Composable
private fun updateTransitionData(boxState: BoxState): TransitionData {
    val transition = updateTransition(boxState)
    val color = transition.animateColor { state ->
        when (state) {
            BoxState.Collapsed -> Color.Gray
            BoxState.Expanded -> Color.Red
        }
    }
    val size = transition.animateDp { state ->
        when (state) {
            BoxState.Collapsed -> 50.dp
            BoxState.Expanded -> 80.dp
        }
    }
    return remember(transition) { TransitionData(color, size) }
}