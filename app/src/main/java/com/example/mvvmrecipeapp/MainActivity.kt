package com.example.mvvmrecipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

//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
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        setContent{
            Column(
                Modifier.padding(16.dp)
            ) {
                Text("Hey look some text")
                Spacer(Modifier.padding(top=10.dp))
                Button(
                    onClick = {}
                ){
                    Text(text = "A Button")
                }
            }
        }
//


    }

}
