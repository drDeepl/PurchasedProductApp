package com.mypurchasedproduct.ui.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mypurchasedproduct.R
import com.mypurchasedproduct.ui.theme.TextColor
import com.mypurchasedproduct.ui.theme.AccentYellowColor
import com.mypurchasedproduct.ui.theme.SecondaryColor
import com.mypurchasedproduct.ui.theme.componentShapes

@Composable
fun NormalTextComponent(value: String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style= TextStyle(
            fontSize=24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle= FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center)
}

@Composable
fun HeadingTextComponent(value: String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style= TextStyle(
            fontSize=30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle= FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center)
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(labelValue: String, icon: Painter){
    val textValue = remember {
        mutableStateOf("")
    }


    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        value = textValue.value ,
        onValueChange = { it: String -> textValue.value = it },
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = AccentYellowColor,
            focusedLabelColor = SecondaryColor
        ),
        leadingIcon = { Icon(painter =icon, contentDescription = "", modifier = Modifier.height(32.dp))}

    )


}