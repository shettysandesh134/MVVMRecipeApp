package com.example.mvvmrecipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//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
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContent {
//            Column {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp)
//                        .border(border = BorderStroke(width = 1.dp, color = Color.Black)),
//                    verticalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    Text(
//                        text = "ITEM1",
//                        modifier = Modifier.align(Alignment.CenterHorizontally)
//                    )
//                    Text(
//                        text = "ITEM2",
//                        modifier = Modifier.align(Alignment.CenterHorizontally)
//                    )
//                }
//                Row(
//                    modifier = Modifier.width(200.dp)
//                        .height(200.dp)
//                        .border(border = BorderStroke(width = 1.dp, color = Color.Black)),
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Text(
//                        text = "ITEM2",
//                        modifier = Modifier.align(Alignment.CenterVertically)
//                    )
//                }
//            }
//        }


        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(color = Color(0xfff2f2f2)),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.happy_meal_small),
                    contentDescription = "",
                    modifier = Modifier.height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    Modifier.padding(16.dp)
                ) {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        Text(
                            text = "Happy Meal",
                            fontSize = 26.sp
                        )
                        Text(
                            text = "$5.99",
                            color = Color(0xFF85bb65),
                            fontSize = 17.sp,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                     Spacer(modifier = Modifier.padding(top = 10.dp))
                    Text(
                        text = "800 Calories",
                        fontSize = 17.sp)
                    Spacer(modifier = Modifier.padding(top = 10.dp))
                    Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            Text(text = "ORDER NOW")
                        }


                }
            }

        }

    }
    

}
