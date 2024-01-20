package com.mypurchasedproduct.presentation.ui.components


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mypurchasedproduct.R
import com.mypurchasedproduct.presentation.ui.theme.AcidGreenColor
import com.mypurchasedproduct.presentation.ui.theme.TextColor
import com.mypurchasedproduct.presentation.ui.theme.AcidPurpleColor
import com.mypurchasedproduct.presentation.ui.theme.AcidRedColor
import com.mypurchasedproduct.presentation.ui.theme.DeepBlackColor
import com.mypurchasedproduct.presentation.ui.theme.LightGreyColor
import com.mypurchasedproduct.presentation.ui.theme.componentShapes

@Composable
fun NormalTextComponent(value: String, textAlign: TextAlign = androidx.compose.ui.text.style.TextAlign.Center){
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
        textAlign = textAlign)
}

@Composable
fun HeadingTextComponent(value: String, textAlign: TextAlign = TextAlign.Center){
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
        textAlign = textAlign)
}

@Composable
fun ErrorTextComponent(value: String){
    Text(
        text = value,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style= TextStyle(
            fontSize=12.sp,
            fontWeight = FontWeight.Bold,
            fontStyle= FontStyle.Normal
        ),
        color = Color.Red,
        textAlign = TextAlign.Center)
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(textValue: MutableState<String>,labelValue: String, icon: Painter, keyboardOptions: KeyboardOptions = KeyboardOptions.Default, enabled: Boolean = true){

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        value = textValue.value ,
        onValueChange = { it: String -> textValue.value = it },
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = AcidRedColor,
            focusedLabelColor = Color.Black,
            containerColor = LightGreyColor,
            unfocusedBorderColor = LightGreyColor
        ),
        shape = componentShapes.large,
        keyboardOptions = keyboardOptions,
        leadingIcon = { Icon(painter =icon, contentDescription = "", modifier = Modifier.height(32.dp))},
        enabled=enabled

    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextFieldPassword(passwordValue: MutableState<String>, labelValue: String, icon: Painter, keyboardOptions: KeyboardOptions = KeyboardOptions.Default, enabled:Boolean = true){

    val passwordVisibility = remember{ mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(componentShapes.small),
        value = passwordValue.value ,
        onValueChange = { it: String -> passwordValue.value = it },
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = AcidRedColor,
            focusedLabelColor = Color.Black,
            containerColor = LightGreyColor,
            unfocusedBorderColor = LightGreyColor

        ),
        shape = componentShapes.large,
        keyboardOptions = keyboardOptions,
        leadingIcon = { Icon(painter =icon, contentDescription = "", modifier = Modifier.height(32.dp))},
        trailingIcon = {
            var iconImage = if(passwordVisibility.value){
                Icons.Filled.Visibility
            }
            else{
                Icons.Filled.VisibilityOff
            }
            var description = if(passwordVisibility.value){
                stringResource(id = R.string.hide_password)
            }else{
                stringResource(id = R.string.show_password)
            }

            IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value}) {
                Icon(imageVector = iconImage, contentDescription=description)

            }
        },
        visualTransformation = if(passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
        enabled=enabled

    )
}

@Composable
fun CheckBoxComponent(checkedState: Boolean, textValue:String, onTextSelected: (String) -> Unit, onChackedChange: (Boolean) -> Unit){
    Row(modifier= Modifier
        .fillMaxWidth()
        .heightIn(56.dp)
        .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically){

        Checkbox(
            checked = checkedState,
            onCheckedChange = { onChackedChange(!checkedState) },
            colors= CheckboxDefaults.colors(
                checkedColor= AcidRedColor
            ),
            )
        ClickableTextComponent(value = textValue, onTextSelected)
    }
}

@Composable
fun ClickableTextComponent(value:String, onTextSelected: (String) -> Unit){
    val initialText = "Продолжая, вы соглашаетесь с нашей "
    val privacyPolicyText = " политикой приватности "
    val andText = " и "
    val termsAndConditionsText = " правилами пользования приложения."

    val annotedString = buildAnnotatedString {
        append(initialText)
        withStyle(style= SpanStyle(color= AcidRedColor)){
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        append(andText)
        withStyle(style= SpanStyle(color= AcidRedColor)){
            pushStringAnnotation(tag = termsAndConditionsText, annotation = termsAndConditionsText)
            append(termsAndConditionsText)
        }

    }
    ClickableText(
        style=TextStyle(color= TextColor),
        text=annotedString, onClick = {offset ->
        annotedString.getStringAnnotations(offset, offset).firstOrNull()?.also {span ->
            Log.d("ClickableTextComponent", "{$span}")
            if((span.item == termsAndConditionsText) || (span.item == privacyPolicyText)){
                onTextSelected(span.item)
            }

        }
    })
}

@Composable
fun ClickableTextLogInComponent(onTextSelected : (String) -> Unit){
    val initialText = "Уже есть аккаунт?"
    val logInText = " войти"

    val annotedString = buildAnnotatedString {
        append(initialText)
        withStyle(style=SpanStyle(color= AcidRedColor)){
            pushStringAnnotation(tag=logInText, annotation = logInText)
            append(logInText)
        }
    }

    ClickableText(
        text = annotedString,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style= TextStyle(
            color = TextColor,
            fontSize=18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle= FontStyle.Normal,
            textAlign = TextAlign.Center
        ),
        onClick = { offset ->
        annotedString.getStringAnnotations(offset,offset).firstOrNull()?.also{span ->
            Log.d("ClickableTextComponent", "{$span}")
            if(span.item == logInText){
                onTextSelected(span.item)
            }
        }
    })
}
@Composable
fun PrimaryButtonComponent(value: String, onClickButton: () -> Unit, isLoading: Boolean = false){
    Button(
        onClick=onClickButton,
        modifier= Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(),
        border = null
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(AcidRedColor, AcidPurpleColor)),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ){
            if(isLoading){
                CircularProgressIndicator(
                    color = Color.White

                )
            }
            else{
                Text(
                    text=value,
                    fontSize=18.sp,
                    fontWeight = FontWeight.Bold)

            }

        }
    }
}

@Composable
fun SuccessButtonComponent(value: String, onClickButton: () -> Unit, icon:Painter = painterResource(
    id =R.drawable.arrow_circle_right_fill_icon
), isLoading: Boolean = false){
    Button(
        onClick=onClickButton,
        modifier= Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(10.dp,5.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = DeepBlackColor
        ),
        border = null
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(DeepBlackColor, DeepBlackColor)),
                    shape = RoundedCornerShape(50.dp),
                ),
            contentAlignment = Alignment.Center
        ){
            if(isLoading){
                CircularProgressIndicator(
                    color = Color.White

                )
            }
            else{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(48.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text=value,
                        fontSize=18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Icon(painter =icon, contentDescription = "", modifier = Modifier.height(36.dp))

                }
            }

        }
    }
}

@Composable
fun DeviderTextComponent(value:String){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color= TextColor,
            thickness = 1.dp)

        Text(text=value, fontSize=14.sp, color= TextColor, modifier= Modifier.padding(5.dp))

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color= TextColor,
            thickness = 1.dp)
    }
}