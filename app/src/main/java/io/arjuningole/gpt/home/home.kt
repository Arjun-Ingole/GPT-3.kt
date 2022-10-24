package io.arjuningole.gpt.home
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.arjuningole.gpt.Screen
import io.arjuningole.gpt.services.getResponse
import io.arjuningole.gpt.services.story
import io.arjuningole.gpt.ui.theme.Colfax
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    navController : NavHostController
){
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        Color(0xFF197E63)
    )
    Column(
        Modifier
            .padding(all = 10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var xprompt = PromptInput()
        var xmodel = getModel()
        var xtoken = getToken()
        Row(
            Modifier
                .padding(horizontal = 6.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Token : ", fontFamily = Colfax)
            Text(xtoken.toString(), fontFamily = Colfax)
        }
        var xpp = getPenalty()
        Row(
            Modifier
                .padding(horizontal = 6.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Presence Penalty : ", fontFamily = Colfax)
            Text(xpp.toString(), fontFamily = Colfax)
        }
        var xtemp = getTempreture()
        Row(
            Modifier
                .padding(horizontal = 6.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Temperature : ", fontFamily = Colfax)
            Text(xtemp.toString(), fontFamily = Colfax)
        }
        var xtopp = getTopP()
        Row(
            Modifier
                .padding(start = 6.dp, end = 6.dp, bottom = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Top P : ", fontFamily = Colfax)
            Text(xtopp.toString(), fontFamily = Colfax)
        }
        Button(
            onClick = {
            getResponse(user_prompt = xprompt, model = xmodel, token = xtoken, topP = xtopp.toDouble(), temp = xtemp.toDouble(), penalty = xpp.toDouble())
            navController.navigate(route = Screen.Output.route)
        },
            Modifier
                .fillMaxWidth()
                .height(75.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFF197E63),
                contentColor = Color.White
            )
        ){
            Text(text = "Generate", fontFamily = Colfax)
        }
    }
}

@Composable
fun getToken():Int{
    var sliderPosition by remember { mutableStateOf(256f) }
    Slider(
        value = sliderPosition,
        onValueChange = {sliderPosition = it},
        valueRange = 256f..2000f,
        modifier = Modifier.fillMaxWidth(),
        colors = SliderDefaults.colors(
            thumbColor = Color(0xFF197E63),
            activeTrackColor = Color(0xFF197E63)
        )
    )
    return sliderPosition.roundToInt()
}

@Composable
fun getTopP():Float{
    var sliderPosition by remember { mutableStateOf(1.0f) }
    Slider(
        value = sliderPosition,
        onValueChange = {sliderPosition = it},
        valueRange = 0.0f..1.0f,
        modifier = Modifier.fillMaxWidth(),
        colors = SliderDefaults.colors(
            thumbColor = Color(0xFF197E63),
            activeTrackColor = Color(0xFF197E63)
        )
    )
    return sliderPosition
}

@Composable
fun getTempreture():Float{
    var sliderPosition by remember { mutableStateOf(0.0f) }
    Slider(
        value = sliderPosition,
        onValueChange = {sliderPosition = it},
        valueRange = 0.0f..1.0f,
        modifier = Modifier.fillMaxWidth(),
        colors = SliderDefaults.colors(
            thumbColor = Color(0xFF197E63),
            activeTrackColor = Color(0xFF197E63)
        )
    )
    return sliderPosition
}

@Composable
fun getPenalty():Float{
    var sliderPosition by remember { mutableStateOf(1.0f) }
    Slider(
        value = sliderPosition,
        onValueChange = {sliderPosition = it},
        valueRange = 0.0f..1.0f,
        modifier = Modifier.fillMaxWidth(),
        colors = SliderDefaults.colors(
            thumbColor = Color(0xFF197E63),
            activeTrackColor = Color(0xFF197E63)
        )
    )
    return sliderPosition
}

@Composable
fun getModel():String {
    val kModels = listOf("text-ada-001","text-babbage-001","text-curie-001","text-davinci-002","davinci-instruct-beta-v3")
    var mSelectedText by remember { mutableStateOf("davinci-instruct-beta-v3") }
    var mExpanded by remember { mutableStateOf(false) }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero)}
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    Column(
        Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()) {
        OutlinedTextField(
            value = mSelectedText,
            readOnly = true,
            onValueChange = { mSelectedText = it },
            textStyle = TextStyle(
                fontFamily = Colfax
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF197E63),
                unfocusedBorderColor = Color(0xFF197E63),
                focusedLabelColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { mExpanded = !mExpanded }
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                },
            label = {Text("Models", fontFamily = Colfax)},
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier.width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
        ) {
            kModels.forEach { label ->
                DropdownMenuItem(onClick = {
                    mSelectedText = label
                    mExpanded = false
                }) {
                    Text(text = label, fontFamily = Colfax)
                }
            }
        }
    }
    return mSelectedText
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PromptInput(): String{
    val kc = LocalSoftwareKeyboardController.current
    var value by remember {
        mutableStateOf("Wanna eat some Ice cream?")
    }
    OutlinedTextField(
        value = value,
        onValueChange = {value = it},
        maxLines = 3,
        shape = RoundedCornerShape(10),
        textStyle = TextStyle(
            fontFamily = Colfax
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                if (kc != null) {
                    kc.hide()
                }
            }
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color(0xFF197E63),
            unfocusedBorderColor = Color(0xFF197E63),
            textColor = Color(0xFF9197A3)
        ),
        modifier = Modifier
            .height(90.dp)
            .fillMaxWidth()
            .border(
                width = 3.dp,
                color = Color(0xFF197E63),
                shape = RoundedCornerShape(10),
            )
    )
    return value
}