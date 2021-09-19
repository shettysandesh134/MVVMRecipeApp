package com.example.mvvmrecipeapp.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.mvvmrecipeapp.R
import kotlinx.coroutines.ExperimentalCoroutinesApi

const val DEFAULT_RECIPE_IMAGE = R.drawable.empty_plate

@ExperimentalCoroutinesApi
@Composable
fun loadPicture(
    url: String,
    @DrawableRes defaultImage: Int,
    id: Int?
): MutableState<Bitmap?> {

    val bitmapState:  MutableState<Bitmap?> = remember{ mutableStateOf(null) }

//    Glide.with(LocalContext.current)
//        .asBitmap()
//        .load(defaultImage)
//        .into(object: CustomTarget<Bitmap>(){
//            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                Log.d("ImageUtilsRe",id.toString())
//                bitmapState.value = resource
//            }
//
//            override fun onLoadCleared(placeholder: Drawable?) {
//
//            }
//
//        })

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(url)
        .into(object: CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmapState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {

            }
        })

    return bitmapState
}