package com.mypurchasedproduct.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp



@Preview(showBackground=true)
@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class,

)
@Composable
fun CarouselCard(){
    val items = listOf<String>("Card 1","Card 2","Card 3","Card 4","Card 5")
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)){
        HorizontalPager(
            pageCount=items.size,
            contentPadding = PaddingValues(horizontal = 5.dp)


        ) {page->
            Text(text = items.get(page))
        }
    }

}