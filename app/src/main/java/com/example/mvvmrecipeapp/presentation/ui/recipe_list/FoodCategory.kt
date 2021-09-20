package com.example.mvvmrecipeapp.presentation.ui.recipe_list

import com.example.mvvmrecipeapp.presentation.ui.recipe_list.FoodCategory.*

enum class FoodCategory(val value: String) {

    CHICKEN("Chicken"),
    BEEF("Beef"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    MILK("Milk"),
    VEGAN("Vegan"),
    PIZZA("Pizza"),
    DONUT("Donut")
}

fun getAllFoodCategory(): List<FoodCategory>{
    return FoodCategory.values().asList()
}

fun getFoodCategory(value: String): FoodCategory?{
   return FoodCategory.values().find { it.value == value }
}