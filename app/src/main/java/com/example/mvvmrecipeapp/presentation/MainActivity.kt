package com.example.mvvmrecipeapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmrecipeapp.R
import dagger.hilt.android.AndroidEntryPoint

//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
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /* EXAMPLE of Retrofit Service */
//        val service = Retrofit.Builder()
//            .baseUrl("https://food2fork.ca/api/recipe/")
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build()
//            .create(RecipeService::class.java)
//
//        CoroutineScope(IO).launch {
//            val recipe = service.get(
//                token = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48",
//                id = 583
//            )
//            Log.d("MainActivity","onCreate: ${recipe.title}")
//        }
        /*End of Retrofit Service*/

    }
    

}
