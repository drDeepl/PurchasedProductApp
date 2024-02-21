package com.mypurchasedproduct.presentation.ui.components


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDismissState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mypurchasedproduct.R
import com.mypurchasedproduct.data.remote.model.response.CategoryResponse
import com.mypurchasedproduct.data.remote.model.response.MeasurementUnitResponse
import com.mypurchasedproduct.data.remote.model.response.ProductResponse
import com.mypurchasedproduct.data.remote.model.response.PurchasedProductResponse
import com.mypurchasedproduct.domain.model.AddPurchasedProductModel
import com.mypurchasedproduct.domain.model.EditPurchasedProductModel
import com.mypurchasedproduct.presentation.ViewModel.AddProductFormViewModel
import com.mypurchasedproduct.presentation.ViewModel.AddPurchasedProductFormViewModel
import com.mypurchasedproduct.presentation.ViewModel.CategoryListViewModel
import com.mypurchasedproduct.presentation.ViewModel.DateRowListViewModel
import com.mypurchasedproduct.presentation.ViewModel.EditPurchasedProductFormViewModel
import com.mypurchasedproduct.presentation.ViewModel.MeasurementUnitsListViewModel
import com.mypurchasedproduct.presentation.ViewModel.ProductListBottomSheetViewModel
import com.mypurchasedproduct.presentation.ViewModel.PurchasedProductListViewModel
import com.mypurchasedproduct.presentation.ViewModel.SignInViewModel
import com.mypurchasedproduct.presentation.ViewModel.SignUpViewModel
import com.mypurchasedproduct.presentation.item.DayItem
import com.mypurchasedproduct.presentation.state.CategoryListState
import com.mypurchasedproduct.presentation.state.DateBoxUIState
import com.mypurchasedproduct.presentation.ui.theme.AcidGreenColor
import com.mypurchasedproduct.presentation.ui.theme.AcidPurpleColor
import com.mypurchasedproduct.presentation.ui.theme.AcidRedColor
import com.mypurchasedproduct.presentation.ui.theme.AcidRedPurpleGradient
import com.mypurchasedproduct.presentation.ui.theme.BackgroundColor
import com.mypurchasedproduct.presentation.ui.theme.DeepBlackColor
import com.mypurchasedproduct.presentation.ui.theme.GreenToYellowGradient
import com.mypurchasedproduct.presentation.ui.theme.GreyColor
import com.mypurchasedproduct.presentation.ui.theme.GreyGradient
import com.mypurchasedproduct.presentation.ui.theme.LightGreyColor
import com.mypurchasedproduct.presentation.ui.theme.SecondaryColor
import com.mypurchasedproduct.presentation.ui.theme.SmoothBlackGradient
import com.mypurchasedproduct.presentation.ui.theme.TextColor
import com.mypurchasedproduct.presentation.ui.theme.componentShapes
import com.mypurchasedproduct.presentation.ui.theme.extraLowPadding
import com.mypurchasedproduct.presentation.ui.theme.largePadding
import com.mypurchasedproduct.presentation.ui.theme.lowPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.mypurchasedproduct.presentation.ui.components.PrimaryOutlinedTextFieldPassword as PrimaryOutlinedTextFieldPassword1

private val TAG = "AppComponent"

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
fun ErrorTextComponent(value: String, fontSize: TextUnit = 12.sp){
    Text(
        text = value,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style= TextStyle(
            fontSize=fontSize,
            fontWeight = FontWeight.Bold,
            fontStyle= FontStyle.Normal
        ),
        color = Color.Red,
        textAlign = TextAlign.Center)
}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryOutlinedTextField(
    textValue: String,
    labelValue: String,
    enabled: Boolean = true,
    icon: Painter = rememberVectorPainter(image = Icons.Filled.Abc),
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    ){
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = lowPadding)
            .clip(componentShapes.small),
        value = textValue,
        onValueChange = { onValueChange(it)},
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = GreyColor,
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
fun PrimaryClickableOutlinedTextField(
    textValue: String,
    labelValue: String,
    isExpanded: Boolean,
    enabled: Boolean = true,
    isReadOnly: Boolean = true,
    onValueChange: (String) -> Unit = {},
    onClick: (Boolean) -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIconSuffix: @Composable() (() -> Unit)? = null,
    ){
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = lowPadding)
            .clip(componentShapes.small),
        value = textValue,
        onValueChange = { onValueChange(it)},
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = GreyColor,
            focusedLabelColor = Color.Black,
            containerColor = LightGreyColor,
            unfocusedBorderColor = LightGreyColor
        ),
        shape = componentShapes.large,
        keyboardOptions = keyboardOptions,
        trailingIcon = {
            Row()
            {
                if (trailingIconSuffix != null) {
                    trailingIconSuffix()
                }
                IconButton(
                    onClick = { onClick(!isExpanded) })
                {
                    if (isExpanded) {
                        Icon(Icons.Default.KeyboardArrowUp, contentDescription = null)
                    } else {
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null)
                    }
                }
            }
        },
        enabled=enabled,
        readOnly = isReadOnly,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryOutlinedTextFieldPassword(password: String, labelValue: String, icon: Painter, onValueChange: (String) -> Unit, keyboardOptions: KeyboardOptions = KeyboardOptions.Default, enabled:Boolean = true){

    val passwordVisibility = remember{ mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = lowPadding)
            .clip(componentShapes.small),
        value = password,
        onValueChange = { onValueChange(it)},
        label = { Text(text = labelValue) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = GreyColor,
            focusedLabelColor = Color.Black,
            containerColor = LightGreyColor,
            unfocusedBorderColor = LightGreyColor

        ),
        shape = componentShapes.large,
        keyboardOptions = keyboardOptions,
        leadingIcon = { Icon(painter =icon, contentDescription = "", modifier = Modifier.height(32.dp))},
        trailingIcon = {
            val iconImage = if(passwordVisibility.value){
                Icons.Filled.Visibility
            }
            else{
                Icons.Filled.VisibilityOff
            }
            val description = if(passwordVisibility.value){
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(textValue: String, labelValue: String,  onValueChange: (String) -> Unit, keyboardOptions: KeyboardOptions = KeyboardOptions.Default, enabled: Boolean = true){
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 10.dp),
        value = textValue ,
        onValueChange = onValueChange,
        label = { Text(text = labelValue) },
        keyboardOptions = keyboardOptions,
        enabled=enabled,
        colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor = GreyColor,
            focusedIndicatorColor = Color.Black,
            containerColor = LightGreyColor,
        )
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
fun ClickableTextComponent(value: String, onTextSelected: (String) -> Unit){
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
fun PrimaryGradientButtonComponent(value: String, onClickButton: () -> Unit, isLoading: Boolean = false, modifier: Modifier = Modifier.fillMaxWidth(), gradientColors: List<Color> = SmoothBlackGradient){
    Button(
        onClick=onClickButton,
        modifier= modifier
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(),
    ){
        Box(
            modifier = modifier
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(gradientColors),
                    shape = RoundedCornerShape(50.dp)
                )
                .border(
                    border = BorderStroke(
                        2.dp,
                        brush = Brush.horizontalGradient(AcidRedPurpleGradient)
                    ),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ){
            if(isLoading){
                CircularProgressIndicator(color = Color.White)
            }
            else{
                Text(
                    text=value,
                    fontSize=18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

            }

        }
    }
}

@Composable
fun SecondaryGradientButtonComponent(
    value: String,
    onClickButton: () -> Unit,
    isLoading: Boolean = false,
    gradientColors: List<Color> = AcidRedPurpleGradient,
    modifier: Modifier = Modifier.fillMaxWidth()
){
    Button(
        onClick=onClickButton,
        modifier= modifier
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            contentColor = TextColor
        ),
        border = BorderStroke(width =  2.dp,brush = Brush.horizontalGradient(gradientColors))

    )
    {
        Box(
            modifier = modifier
                .heightIn(48.dp)
                .background(
                    color = BackgroundColor,
                    shape = componentShapes.large
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
fun SuccessButtonIcon(onClick: () -> Unit, isLoading: Boolean = false){
    Button(onClick = onClick,
        modifier= Modifier.size(50.dp),  //avoid the oval shape
        shape = CircleShape,
        elevation = ButtonDefaults.buttonElevation(pressedElevation=8.dp),
        border = BorderStroke(2.dp, brush = Brush.horizontalGradient(listOf(AcidGreenColor, AcidPurpleColor))),
        contentPadding = PaddingValues(0.dp),  //avoid the little icon
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        )
    ){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            if(isLoading){
                CircularProgressIndicator(
                    color = Color.White

                )
            }
            else{
                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .graphicsLayer(alpha = 0.9f)
                        .drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                drawRect(
                                    brush = Brush.horizontalGradient(
                                        listOf(
                                            AcidGreenColor,
                                            AcidPurpleColor
                                        )
                                    ), blendMode = BlendMode.SrcAtop
                                )
                            }
                        },
                    imageVector = Icons.Filled.Check, contentDescription = null)
            }

        }

    }
}

@Composable
fun DismissButtonIcon(onClick: () -> Unit, isLoading: Boolean = false){
    Button(onClick = onClick,
        modifier= Modifier.size(50.dp),  //avoid the oval shape
        shape = CircleShape,
        elevation = ButtonDefaults.buttonElevation(pressedElevation=8.dp),
        border = BorderStroke(2.dp, brush = Brush.horizontalGradient(listOf(AcidRedColor, AcidPurpleColor)),),
        contentPadding = PaddingValues(0.dp),  //avoid the little icon
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        )
    ){
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            if(isLoading){
                CircularProgressIndicator(
                    color = Color.White

                )
            }
            else{
                Icon(
                    modifier = Modifier
                        .size(32.dp)
                        .graphicsLayer(alpha = 0.9f)
                        .drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                drawRect(
                                    brush = Brush.horizontalGradient(
                                        listOf(
                                            AcidRedColor,
                                            AcidPurpleColor
                                        )
                                    ), blendMode = BlendMode.SrcAtop
                                )
                            }
                        },
                    imageVector = Icons.Filled.Close, contentDescription = null)
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

@Composable
fun PurchasedProductItem(purchasedProduct:PurchasedProductResponse){
    Surface(
        modifier= Modifier
            .fillMaxWidth()
            .padding(2.dp, 7.dp)
            .height(65.dp),
        shadowElevation = 5.dp,
        border = BorderStroke(
            width = 2.dp,
            brush = Brush.horizontalGradient(listOf(AcidRedColor, AcidPurpleColor)),
        ),
        shape = componentShapes.medium
    ){
        Column(
            modifier = Modifier.padding(15.dp, 10.dp),
            horizontalAlignment= Alignment.Start,
            verticalArrangement = Arrangement.SpaceAround
        ){
            Text(
                purchasedProduct.product.name,
                fontSize=18.sp,
                fontWeight= FontWeight.Bold,
            )
            Row(horizontalArrangement = Arrangement.Center){
                Text(
                    purchasedProduct.count.toString(),
                    fontSize=16.sp,
                )
                Spacer(modifier = Modifier.padding(2.dp,0.dp))
                Text(
                    purchasedProduct.unitMeasurement.name,
                    fontSize=16.sp,
                )

            }
        }
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "${purchasedProduct.price.toString()} ₽",
                modifier = Modifier.padding(8.dp),
                fontSize=17.sp,
                fontWeight= FontWeight.Bold,
            )

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchasedProductViewComponent(
    viewModel: PurchasedProductListViewModel,
    modifier: Modifier = Modifier
        .fillMaxSize(),
    paddingValues: PaddingValues,
    onSwipeToEdit: (PurchasedProductResponse) -> Unit
)
{
    val listState = viewModel.state.collectAsState()
    val purchasedProducts = viewModel.purchasedProducts.collectAsState()
    val scope = rememberCoroutineScope()
    Box(
        modifier =modifier.padding(paddingValues),
    ) {

        if(listState.value.isLoading){
            LoadScreen()
        }
        else{
            LazyColumn(
                state = rememberLazyListState(),
                userScrollEnabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
            ) {
                items(purchasedProducts.value) { purchasedProduct ->
                    val state = rememberDismissState(
                        confirmValueChange = {
                                if (it == DismissValue.DismissedToStart) {
                                    viewModel.onSwipeDeletePurchasedProduct(purchasedProduct)
                                }
                                if (it == DismissValue.DismissedToEnd) {
                                    onSwipeToEdit(purchasedProduct)
                                }
                            false
                        },

                    )
                    SwipeToDismiss(
                        state = state,
                        background = {
                            val color = when (state.dismissDirection) {
                                DismissDirection.EndToStart -> Color.Transparent
                                DismissDirection.StartToEnd -> Color.Transparent
                                null -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(2.dp, 7.dp)
                                    .height(65.dp)
                                    .background(color = color, shape = componentShapes.medium),
                            ) {
                                Spacer(modifier = modifier.padding(horizontal = 10.dp))
                                Icon(
                                    imageVector = Icons.Outlined.Delete,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(42.dp)
                                        .align(Alignment.CenterEnd)
                                        .graphicsLayer(alpha = 0.9f)
                                        .drawWithCache {
                                            onDrawWithContent {
                                                drawContent()
                                                drawRect(
                                                    brush = Brush.horizontalGradient(
                                                        listOf(AcidRedColor, AcidPurpleColor)
                                                    ),
                                                    blendMode = BlendMode.SrcAtop
                                                )
                                            }
                                        }
                                )
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(42.dp)
                                        .align(Alignment.CenterStart)
                                        .graphicsLayer(alpha = 0.9f)
                                        .drawWithCache {
                                            onDrawWithContent {
                                                drawContent()
                                                drawRect(
                                                    brush = Brush.horizontalGradient(
                                                        listOf(AcidRedColor, AcidPurpleColor)
                                                    ),
                                                    blendMode = BlendMode.SrcAtop
                                                )
                                            }
                                        }
                                )
                                Spacer(modifier = modifier.padding(horizontal = 10.dp))
                            }
                        },
                        dismissContent = {
                            PurchasedProductItem(purchasedProduct)
                        }
                    )

                }
            }
        }
    }
}

@Composable
fun WithAnimation(modifier: Modifier = Modifier.fillMaxSize(), delay: Long = 1, animation: EnterTransition, content: @Composable ()->Unit){
    val visible = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit){
        delay(delay)
        visible.value = true
    }
    Box(modifier = modifier){
        if(!visible.value){
            Box(modifier = Modifier.alpha(0f)){
                content()
            }
        }
        AnimatedVisibility(visible = visible.value, enter=animation, modifier = modifier) {
            content()
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadScreen(modifier: Modifier = Modifier, isActive: Boolean=true){
    Log.wtf("LoadScreen", "LOAD SCREEN")
    if(isActive) {
        AlertDialog(
            onDismissRequest = { /*TODO*/ },
            properties = DialogProperties(
                dismissOnBackPress = false,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = true
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.height(45.dp),
                    color = DeepBlackColor
                )

            }

        }
    }
}

@Composable
fun PrimaryFloatingActionButton(
    onClick: () -> Unit,
    painter: Painter = painterResource(id = R.drawable.ic_plus),
    modifier: Modifier = Modifier
        .height(65.dp)
        .width(65.dp),
    isLoading: Boolean = false,
    )
{
    IconButton(
        modifier = modifier
            .background(
                brush = Brush.horizontalGradient(listOf(AcidRedColor, AcidPurpleColor)),
                shape = RoundedCornerShape(percent = 100)
            )
            .padding(8.dp),
        onClick = onClick)
    {
        if(isLoading){
            CircularProgressIndicator(color = Color.White)
        }
        else{
            Icon(
                modifier = modifier,
                painter=painter, contentDescription = "ic_plus", tint=Color.White)
        }

    }
}

@Composable
fun CardHeaderComponent(value: String, textAlign: TextAlign = TextAlign.Center){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style= TextStyle(
            fontSize=24.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle= FontStyle.Normal
        ),
        color = TextColor,
        textAlign = textAlign)
}

@Composable
fun DialogCardComponent(
    onDismiss:() -> Unit,
    onConfirm:() -> Unit,
    content: @Composable () -> Unit?
) {

    Dialog(
        onDismissRequest = {onDismiss},
        properties = DialogProperties(dismissOnBackPress = false,dismissOnClickOutside = false, usePlatformDefaultWidth = false, decorFitsSystemWindows=true),
    ){
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            CardHeaderComponent(value = "Добавить продукт")
            Divider(modifier = Modifier.padding(vertical = 20.dp))
            content()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Bottom
            ){
                SuccessButtonIcon(onClick=onConfirm)
                DismissButtonIcon(onClick=onDismiss)
                }


        }
    }
}

@Composable
fun DialogCardComponentWithoutActionBtns(textHeader: String, content: @Composable () -> Unit?)
{
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false,dismissOnClickOutside = false, usePlatformDefaultWidth = false, decorFitsSystemWindows=true),
    ){
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            CardHeaderComponent(value = textHeader)
            Divider(modifier = Modifier.padding(vertical = 20.dp))
            content()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductModalBottomSheet(
    viewModel: ProductListBottomSheetViewModel,
    onClickAddProduct: () -> Unit,
    onClickProductItem: (product: ProductResponse) -> Unit
) {
    val skipPartiallyExpanded by remember { mutableStateOf(true) }
    val edgeToEdgeEnabled by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )
    val state = viewModel.state.collectAsState()
    val products = viewModel.products.collectAsState()

    // Sheet content
    if (state.value.isActive) {
        val windowInsets = if (edgeToEdgeEnabled)
            WindowInsets(0) else BottomSheetDefaults.windowInsets

        ModalBottomSheet(
            containerColor=Color.White,
            onDismissRequest = {
                scope.launch {
                    viewModel.setActive(false)
                }
                               },
            sheetState = bottomSheetState,
            windowInsets = windowInsets
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text(text="Нет нужного продукта?")
                TextButton(
                    onClick = {
                        scope.launch {
                            bottomSheetState.hide()
                            onClickAddProduct()
                        }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                viewModel.setActive(false)
                            }
                        }
                    }
                ) {
                    Text("добавить", fontSize=16.sp)
                }
            }
            Divider(modifier = Modifier.padding(5.dp, 10.dp), thickness=2.dp)
            LazyColumn(
                userScrollEnabled = true,
            ) {
                items(products.value){product ->
                    TextButton(modifier = Modifier.fillMaxWidth(), onClick = { onClickProductItem(product) }) {Text(product.name, fontSize = 16.sp) }
                    Divider(thickness = 2.dp)
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormModalBottomSheet(
    openBottomSheet: Boolean,
    setStateBottomSheet: (Boolean) -> Unit,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit,

){
    Log.wtf(TAG, "FormModalBottomSheet")
    val skipPartiallyExpanded by remember { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded
    )

    if (openBottomSheet) {
        ModalBottomSheet(
            containerColor = Color.White,
            onDismissRequest = {
                setStateBottomSheet(false)
                onDismissRequest()
                               },
            sheetState = bottomSheetState,
            windowInsets = BottomSheetDefaults.windowInsets,
        ) {
            content()
        }

    }
}

@Composable
fun SelectCategoryButton(categoryResponse: CategoryResponse, onClick: (id:Long) -> Unit){
    var isSelected by remember {mutableStateOf(false)}
    OutlinedButton(onClick = {
        isSelected = !isSelected
        if(isSelected) {
            onClick(categoryResponse.id)
        }
    }
    ) {
        Text(text = categoryResponse.name)
        if(isSelected){
            Icon(imageVector = Icons.Filled.Check, contentDescription = null)
        }
    }
}

@Composable
fun SuccessMessageDialog(text: String, onDismiss: () -> Unit){
    Dialog(
        onDismissRequest = {onDismiss},
        properties = DialogProperties(dismissOnBackPress = false,dismissOnClickOutside = false, usePlatformDefaultWidth = false, decorFitsSystemWindows=true),
    ){
        Surface(shape= componentShapes.medium)
        {
            Column(
                modifier= Modifier
                    .fillMaxWidth(0.8f)
                    .background(Color.White)
                    .padding(10.dp, 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            )
            {
                Icon(
                    modifier = Modifier
                        .size(128.dp)
                        .graphicsLayer(alpha = 0.9f)
                        .drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                drawRect(
                                    brush = Brush.horizontalGradient(
                                        listOf(AcidRedColor, AcidPurpleColor)
                                    ),
                                    blendMode = BlendMode.SrcAtop
                                )
                            }
                        },
                    painter = painterResource(id = R.drawable.check_circle_icon),
                    contentDescription = null
                )
                HeadingTextComponent(text)
                Spacer(modifier=Modifier.height(15.dp))
                SecondaryGradientButtonComponent(value="супер", onClickButton = onDismiss )
            }
        }
    }
}

@Composable
fun ErrorMessageDialog(headerText: String, description: String, onDismiss: () -> Unit){
    Dialog(
        onDismissRequest = {onDismiss},
        properties = DialogProperties(dismissOnBackPress = false,dismissOnClickOutside = false, usePlatformDefaultWidth = false, decorFitsSystemWindows=true),
    ){
        Surface(shape= componentShapes.medium)
        {
            Column(
                modifier= Modifier
                    .fillMaxWidth(0.8f)
                    .background(Color.White)
                    .padding(10.dp, 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            )
            {
                Icon(
                    modifier = Modifier
                        .size(128.dp)
                        .graphicsLayer(alpha = 0.9f)
                        .drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                drawRect(
                                    brush = Brush.horizontalGradient(
                                        listOf(AcidRedColor, AcidPurpleColor)
                                    ),
                                    blendMode = BlendMode.SrcAtop
                                )
                            }
                        },
                    painter = painterResource(id = R.drawable.ic_x_circle),
                    contentDescription = null
                )
                HeadingTextComponent(headerText)
                Spacer(modifier = Modifier.padding(10.dp))
                NormalTextComponent(value = description)
                Spacer(modifier=Modifier.height(15.dp))
                SecondaryGradientButtonComponent(value="окей", onClickButton = onDismiss )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogComponent(headerText: String, onDismiss: () -> Unit, onConfirm: () -> Unit, content: @Composable () -> Unit){
    AlertDialog(
        modifier= Modifier.padding(horizontal = 20.dp),
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows=true)
    ){
        Card {
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            HeadingTextComponent(value=headerText)
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Divider(thickness=2.dp)
            content()
            Spacer(modifier = Modifier.padding(vertical = 15.dp))
            Row(
                horizontalArrangement= Arrangement.SpaceAround,
            ){
                OutlinedButton(onClick = onConfirm) {
                    Text(text = "да")
                }
                Button(onClick = onDismiss) {
                    Text(text = "нет")
                }
            }
        }
    }
}

@Composable
fun MeasurementUnitListComponent(
    measurementUnits:  State<List<MeasurementUnitResponse>>,
    onClick: (it: MeasurementUnitResponse)-> Unit,
    modifier: Modifier = Modifier
){
    Log.d(TAG, "MeasurementUnitListComponent")
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = lowPadding
            ),
        state = listState,
        userScrollEnabled = true,
        horizontalArrangement = Arrangement.SpaceAround,
    ){
        itemsIndexed(items=measurementUnits.value){
            index, item: MeasurementUnitResponse ->
            OutlinedButton(
                onClick = {
                    scope.launch{
                        onClick(item)
                    }
                }
            ){
                Text(text=item.name)
            }
        }
    }
}


@Composable
fun CategoryListComponent(
    categories:  State<MutableList<CategoryResponse>>,
    onClick: (it: CategoryResponse)-> Unit,
    categoryListState: State<CategoryListState>,
    modifier: Modifier = Modifier
){
    Log.d(TAG, "CategoryListComponent")
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    if(categoryListState.value.isLoading){
        LinearProgressIndicator()
    }
    else {
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    vertical = lowPadding
                ),
            state = listState,
            userScrollEnabled = true,
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            itemsIndexed(items = categories.value) { index, item: CategoryResponse ->
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            onClick(item)
                        }
                    }
                ) {
                    Text(text = item.name)
                }
            }
        }
    }
}

@Composable
fun ErrorListContainer(errors: List<String>, modifier: Modifier = Modifier.animateContentSize()){
    LazyColumn(
        modifier = modifier.padding(horizontal = lowPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ){
        items(errors){
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ){
                Icon(
                    painter = rememberVectorPainter(image = Icons.Filled.Error),
                    contentDescription = "",
                    modifier= Modifier
                        .size(32.dp)
                        .graphicsLayer(alpha = 0.9f)
                        .drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                drawRect(
                                    brush = Brush.horizontalGradient(
                                        listOf(
                                            AcidRedColor,
                                            AcidRedColor
                                        )
                                    ), blendMode = BlendMode.SrcIn
                                )
                            }
                        })
                Text(text = it, fontSize = 16.sp, modifier = Modifier.padding(horizontal = extraLowPadding), color= AcidRedColor)
            }
            Spacer(modifier = Modifier.padding(vertical = extraLowPadding))

        }
    }

}

@Composable
fun AddProductFormComponent(
    addProductFormViewModel: AddProductFormViewModel,
    categoryListVM: CategoryListViewModel,
    onDismiss: () -> Unit
){
    val formState = addProductFormViewModel.formState.collectAsState()
    val isOpenCategoryContainer = remember { mutableStateOf(false) }
    val product = addProductFormViewModel.productItem
    val productName = remember {
        mutableStateOf("")
    }
    val categoryName = remember {
        mutableStateOf("")
    }
    val categoryId = remember { mutableStateOf(product.value.categoryId) }
    val categoryListState = categoryListVM.state.collectAsState()
    val categories = categoryListVM.categories.collectAsState()
    
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxHeight(0.5f).animateContentSize()
    ){
        PrimaryClickableOutlinedTextField(
            textValue = categoryName.value,
            labelValue = "категория",
            isExpanded = isOpenCategoryContainer.value,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onClick = {
                isOpenCategoryContainer.value = it
            },
            onValueChange = {
                categoryName.value = it
                            },
            isReadOnly = true
        )
        if(isOpenCategoryContainer.value){
            CategoryListComponent(
                categories = categories,
                onClick = {
                    categoryName.value = it.name
                    categoryId.value = it.id
                },
                categoryListState = categoryListState
            )
        }
        PrimaryOutlinedTextField(textValue = productName.value, labelValue = "продукт", onValueChange = {productName.value = it})
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = lowPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SecondaryGradientButtonComponent(
                value = "добавить",
                onClickButton = {
                    Log.wtf(TAG, "TODO CONFIRM ADD PRODUCT")
                },
                modifier = Modifier.widthIn(150.dp)
            )
            SecondaryGradientButtonComponent(
                value = "отмена",
                onClickButton = onDismiss,
                gradientColors = GreyGradient,
                modifier = Modifier.widthIn(150.dp)
            )
        }
    }

}

@Composable
fun AddPurchasedProductFormComponent(
    addPurchasedProductVM: AddPurchasedProductFormViewModel,
    productListBottomSheetVM: ProductListBottomSheetViewModel,
    measurementUnitsListVM: MeasurementUnitsListViewModel,
    onClickAddProduct: () -> Unit,
    onConfirm: (addPurchasedProductModel: AddPurchasedProductModel) -> Unit,
    onDismiss: () -> Unit,
){

    val scope = rememberCoroutineScope()
    val addPurchasedProductState = addPurchasedProductVM.state.collectAsState()
    val productState = productListBottomSheetVM.state.collectAsState()
    val measurementUnitState = measurementUnitsListVM.state.collectAsState()
    val isOpenMeasurementUnits = remember{mutableStateOf(false)}
    val measurementUnits = measurementUnitsListVM.measurementUnits.collectAsState()
    if(addPurchasedProductState.value.measurementUnit == null) {
        addPurchasedProductVM.setMeasurementUnit(measurementUnits.value[0])
    }
    Column(
        modifier = Modifier
//            .heightIn(screenHeight.value)
            .fillMaxHeight(0.5f)
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (productState.value.isLoading) {
            LinearProgressIndicator()
        } else {
            PrimaryClickableOutlinedTextField(
                textValue = addPurchasedProductState.value.product?.let { it.name } ?: "",
                labelValue = "продукт",
                isExpanded = productState.value.isActive,
                onClick = {
                    productListBottomSheetVM.setActive(it)
                },
            )
            ProductModalBottomSheet(
                viewModel = productListBottomSheetVM,
                onClickAddProduct = onClickAddProduct,
                onClickProductItem = {
                    scope.launch {
                        addPurchasedProductVM.setProduct(it)
                        productListBottomSheetVM.setActive(false)
                    }
                }
            )
        }
        Column(modifier = Modifier.animateContentSize())
        {
            if (measurementUnitState.value.isLoading) {
                LinearProgressIndicator()
            } else {
                PrimaryClickableOutlinedTextField(
                    textValue = addPurchasedProductState.value.count,
                    labelValue = "количество",
                    isExpanded = isOpenMeasurementUnits.value,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onClick = {
                        isOpenMeasurementUnits.value = it
                    },
                    onValueChange = {addPurchasedProductVM.setCount(it)},
                    isReadOnly = false,
                    trailingIconSuffix = {
                        TextButton(onClick = { isOpenMeasurementUnits.value = !isOpenMeasurementUnits.value }) {
                            Text(text=addPurchasedProductState.value.measurementUnit?.let{it.name} ?: "", fontSize = 16.sp)
                        }
                    },
                )
                if(isOpenMeasurementUnits.value) {
                    MeasurementUnitListComponent(
                        measurementUnits = measurementUnits,
                        onClick = {
                            addPurchasedProductVM.setMeasurementUnit(it)
                            isOpenMeasurementUnits.value = false
                        }
                    )
                }
            }
        }
        PrimaryOutlinedTextField(
            textValue = addPurchasedProductState.value.price,
            labelValue = "цена",
            onValueChange = {
                addPurchasedProductVM.setPrice(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            icon = rememberVectorPainter(image = Icons.Filled.Numbers)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = lowPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SecondaryGradientButtonComponent(
                value = "добавить",
                onClickButton = {
                    scope.launch {
                        onConfirm(addPurchasedProductVM.getAddPurchasedProductModel())
                    }
                },
                modifier = Modifier.widthIn(150.dp)
            )
            SecondaryGradientButtonComponent(
                value = "отмена",
                onClickButton = onDismiss,
                gradientColors = GreyGradient,
                modifier = Modifier.widthIn(150.dp)
            )
        }
    }
}


@Composable
fun EditPurchasedProductFormComponent(
    editPurchasedProductVM: EditPurchasedProductFormViewModel,
    productListBottomSheetVM: ProductListBottomSheetViewModel,
    measurementUnitsListVM: MeasurementUnitsListViewModel,
    onClickAddProduct: () -> Unit,
    onConfirm: (editPurchasedProductModel: EditPurchasedProductModel) -> Unit,
    onDismiss: () -> Unit,
){
    val editPurchasedProductState = editPurchasedProductVM.state.collectAsState()
    val selectedPurchasedProduct = editPurchasedProductVM.purchasedProductToEdit.collectAsState()
    val errors = editPurchasedProductVM.errors.collectAsState()
    if(editPurchasedProductState.value.isError){
        Log.d(TAG, "ERRORS COUNT :${errors.value.size}")
        ErrorListContainer(errors = errors.value)
    }
    val scope = rememberCoroutineScope()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp.value/1.8f
    val productState = productListBottomSheetVM.state.collectAsState()
    val measurementUnitState = measurementUnitsListVM.state.collectAsState()
    val isOpenMeasurementUnits = remember{mutableStateOf(false)}
    val measurementUnits = measurementUnitsListVM.measurementUnits.collectAsState()

    val newProduct = remember {
        mutableStateOf(selectedPurchasedProduct.value.product)
    }

    val newCount = remember {
        mutableStateOf(selectedPurchasedProduct.value.count.toString())
    }

    val newMeasurementUnit = remember {
        mutableStateOf(selectedPurchasedProduct.value.unitMeasurement)
    }

    val newPrice = remember {
        mutableStateOf(selectedPurchasedProduct.value.price.toString())
    }

    Column(
        modifier = Modifier
            .heightIn(Dp(screenHeight))
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (productState.value.isLoading) {
            LinearProgressIndicator()
        } else {
            PrimaryClickableOutlinedTextField(
                textValue = newProduct.value.name,
                labelValue = "продукт",
                isExpanded = productState.value.isActive,
                onClick = {
                    productListBottomSheetVM.setActive(it)
                },
            )
            ProductModalBottomSheet(
                viewModel = productListBottomSheetVM,
                onClickAddProduct = onClickAddProduct,
                onClickProductItem = {
                    scope.launch {
                        newProduct.value = it
                        productListBottomSheetVM.setActive(false)
                    }
                }
            )
        }
        Column(modifier = Modifier.animateContentSize())
        {
            if (measurementUnitState.value.isLoading) {
                LinearProgressIndicator()
            } else {
                PrimaryClickableOutlinedTextField(
                    textValue = newCount.value,
                    labelValue = "количество",
                    isExpanded = isOpenMeasurementUnits.value,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onClick = {
                        isOpenMeasurementUnits.value = it
                    },
                    onValueChange = {newCount.value = it},
                    isReadOnly = false,
                    trailingIconSuffix = {
                        TextButton(onClick = { isOpenMeasurementUnits.value = !isOpenMeasurementUnits.value }) {
                            Text(text=newMeasurementUnit.value.name, fontSize = 16.sp)
                        }
                    },
                )
                if(isOpenMeasurementUnits.value) {
                    MeasurementUnitListComponent(
                        measurementUnits = measurementUnits,
                        onClick = {
                            newMeasurementUnit.value = it
                            isOpenMeasurementUnits.value = false
                        }
                    )
                }
            }
        }
        PrimaryOutlinedTextField(
            textValue = newPrice.value,
            labelValue = "цена",
            onValueChange = {
                newPrice.value = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            icon = rememberVectorPainter(image = Icons.Filled.Numbers)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = lowPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SecondaryGradientButtonComponent(
                value = "добавить",
                onClickButton = {
                    scope.launch{
                        editPurchasedProductVM.setLoading(true)
                        if(editPurchasedProductVM.validate(newProduct.value, newCount.value, newMeasurementUnit.value, newPrice.value)){
                            // TODO: SEND REQUEST TO EDIT
                            Log.d(TAG,"VALIDATE SUCCESS IS COMPLETED")
                            editPurchasedProductVM.setLoading(false)
                        }
                        else{
                            Log.d(TAG,"VALIDATE NOT SUCCESS IS COMPLETED")

                        }

//                        onConfirm(addPurchasedProductVM.getAddPurchasedProductModel())
                    }
                },
                modifier = Modifier.widthIn(150.dp),
                isLoading = editPurchasedProductState.value.isLoading
            )
            SecondaryGradientButtonComponent(
                value = "отмена",
                onClickButton = onDismiss,
                gradientColors = GreyGradient,
                modifier = Modifier.widthIn(150.dp)
            )
        }
    }
}



@Composable
fun AddCategoryForm(isLoading: Boolean, onConfirm: (String) -> Unit, onDismiss: (isActive:Boolean) -> Unit) {
    var categoryName by remember {
        mutableStateOf("")
    }
    DialogCardComponentWithoutActionBtns("Добавление категории") {
        MyTextField(
            textValue = categoryName,
            labelValue = "название",
            onValueChange = {
                categoryName = it
            },
            enabled = !isLoading
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom
        ) {
            PrimaryGradientButtonComponent(
                value ="добавить" ,
                { onConfirm(categoryName) },
                isLoading=isLoading,
                modifier = Modifier.widthIn(64.dp)
            )
            SecondaryGradientButtonComponent(value = "отмена", onClickButton = {onDismiss(false) }, modifier = Modifier.widthIn(64.dp))

        }
    }
}

@Composable
fun SignInFormComponent(
    viewModel: SignInViewModel,
    ){
    val state = viewModel.signInState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(state.value.isError){
            ErrorTextComponent(value = state.value.error, fontSize = 16.sp)
        }
        PrimaryOutlinedTextField(
            state.value.username,
            onValueChange = {viewModel.onUsernameChange(it)},
            labelValue = "Имя пользователя",
            icon=painterResource(id = R.drawable.user_icon),
            keyboardOptions=KeyboardOptions(imeAction = ImeAction.Next),
            enabled=!state.value.isLoading
        )
        Spacer(modifier = Modifier.height(10.dp))
        PrimaryOutlinedTextFieldPassword1(
            password = state.value.password,
            onValueChange = {viewModel.onPasswordChange(it)},
            labelValue = "Пароль",
            icon=painterResource(id = R.drawable.password_icon),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            enabled=!state.value.isLoading
        )
        Spacer(modifier = Modifier.height(20.dp))
        PrimaryGradientButtonComponent(
            value=stringResource(R.string.login_btn_text),
            onClickButton={ viewModel.toSignIn() },
            isLoading=state.value.isLoading
        )
    }
}

@Composable
fun SelectionTabComponent(labelTabs: List<String>, currentTab: State<String>, onClickTab: (id: Int) -> Unit){

//    Switch(checked = checkedState.value, onCheckedChange = {checkedState.value = it})
    val scope = rememberCoroutineScope()
//    val selectedOption = remember {
//        mutableStateOf(currentTab)
//    }



    val onSelectionChange = { text:String ->
        scope.launch {
            onClickTab(labelTabs.indexOf(text))
        }
    }
    Box() {
        Row(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(24.dp))
                .background(brush = Brush.horizontalGradient(SmoothBlackGradient)),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            labelTabs.forEach { text->
                Text(
                    text = text,
                    fontSize=16.sp,
                    fontWeight= FontWeight.SemiBold,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(24.dp))
                        .clickable {
                            onSelectionChange(text)
                        }
                        .border(
                            width = 2.dp,
                            shape = RoundedCornerShape(24.dp),
                            brush = if (text == currentTab.value) {
                                Brush.horizontalGradient(colors = GreenToYellowGradient)
                            } else {
                                Brush.horizontalGradient(colors = SmoothBlackGradient)
                            }

                        )
                        .padding(
                            vertical = 12.dp,
                            horizontal = 16.dp,
                        )
                        .graphicsLayer(alpha = 0.99f)
                        .drawWithCache {
                            val brush = if (text == currentTab.value) {
                                Brush.horizontalGradient(GreenToYellowGradient)
                            } else {
                                Brush.horizontalGradient(listOf(Color.White, Color.White))
                            }
                            onDrawWithContent {
                                drawContent()
                                drawRect(brush, blendMode = BlendMode.SrcAtop)
                            }
                        },

                )
            }
        }
    }
}

@Composable
fun SignUpFormComponent(
    viewModel: SignUpViewModel,
    onTermsAndConditionText: (String) -> Unit
){

    val state = viewModel.signUpState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(state.value.isError){
            ErrorTextComponent(value = state.value.error.toString(), fontSize = 16.sp)
        }
        PrimaryOutlinedTextField(
            state.value.username,
            onValueChange = {viewModel.onUsernameChange(it)},
            labelValue = "Имя пользователя",
            icon=painterResource(id = R.drawable.user_icon),
            keyboardOptions=KeyboardOptions(imeAction = ImeAction.Next),
            enabled=!state.value.isLoading
        )
        Spacer(modifier = Modifier.height(10.dp))
        PrimaryOutlinedTextFieldPassword1(
            password = state.value.password,
            onValueChange = {viewModel.onPasswordChange(it)},
            labelValue = "Пароль",
            icon=painterResource(id = R.drawable.password_icon),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            enabled=!state.value.isLoading
        )
        CheckBoxComponent(
            checkedState = state.value.isApplyTermsAndConditions,
            textValue = stringResource(id = R.string.term_and_conditions),
            onTextSelected = { onTermsAndConditionText(it) },
            onChackedChange = {
                viewModel.setIsApplyTermsAndConditions(it)
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        PrimaryGradientButtonComponent(
            value=stringResource(R.string.signup_btn_text),
            onClickButton={ viewModel.toSignUp() },
            isLoading=state.value.isLoading
        )
    }
}

@Composable
fun DayComponent(dayItem: DayItem, state: State<DateBoxUIState>, onClick: (day:DayItem) -> Unit){
    val scope = rememberCoroutineScope()

    val gradient = remember {
        mutableStateOf(listOf(SecondaryColor, SecondaryColor))
    }
    LaunchedEffect(state.value.selectedDate){
        if(state.value.selectedDate.dayOfMonth == dayItem.dayOfMonth){
            gradient.value = AcidRedPurpleGradient.reversed()
        }
        else{
            gradient.value = listOf(SecondaryColor, SecondaryColor)
        }
    }
    Column(
        modifier= Modifier
            .fillMaxWidth()
            .heightIn(64.dp)
            .widthIn(32.dp)
            .border(
                width = 1.5.dp,
                shape = componentShapes.medium,
                brush = Brush.verticalGradient(gradient.value)
            )
            .clickable {
                scope.launch {
                    onClick(dayItem)
//                    gradient.value = AcidRedPurpleGradient.reversed()
                }
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier=Modifier.padding(all=4.dp),
            text=dayItem.dayWeekName,
            fontWeight = FontWeight.SemiBold
        )
        Box(
            modifier = Modifier
                .heightIn(36.dp)
                .widthIn(30.dp)
                .background(
                    color = GreyColor.copy(alpha = .25f),
                    shape = RoundedCornerShape(
                        topStart = 8.dp,
                        topEnd = 8.dp,
                        bottomEnd = 18.dp,
                        bottomStart = 18.dp
                    )
                )
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center,
        ){
            Text(text=dayItem.dayOfMonth.toString(),fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun DaysRowComponent(viewModel: DateRowListViewModel, modifier: Modifier = Modifier){
    Log.wtf("DaysRowComponent", "start")
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        val state = viewModel.state.collectAsState()
        LoadScreen(isActive = state.value.isLoading)
        val days = viewModel.listDates.collectAsState()
        Log.wtf("DaysRowComponent", "count days: ${days.value.size}")

        val listState = rememberLazyListState(initialFirstVisibleItemIndex=state.value.selectedDate.dayOfMonth-3)
        val scope = rememberCoroutineScope()

        LazyRow(
            modifier = modifier.fillMaxWidth(0.95f),
            state = listState,
            userScrollEnabled = true,
            horizontalArrangement = Arrangement.SpaceAround,
        ){
            itemsIndexed(items=days.value){index: Int, it: DayItem ->
                Spacer(modifier = Modifier.padding(2.dp))
                DayComponent(
                    it,
                    state,
                    onClick = {
                        scope.launch {
                            viewModel.onSelectDay(it)
                        }
                    })
            }
        }
    }
}