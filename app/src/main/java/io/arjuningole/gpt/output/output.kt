package io.arjuningole.gpt.output

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.arjuningole.gpt.R
import io.arjuningole.gpt.Screen
import io.arjuningole.gpt.services.story
import io.arjuningole.gpt.ui.theme.Colfax

@Composable
fun PromptOutput(value : String){
    Column() {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            maxLines = 50,
            readOnly = true,
            textStyle = TextStyle(
                fontFamily = Colfax
            ),
            shape = RoundedCornerShape(5),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF197E63),
                unfocusedBorderColor = Color(0xFF197E63),
                textColor = Color(0xFF9197A3)
            ),
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth()
                .border(
                    width = 3.dp,
                    color = Color(0xFF197E63),
                    shape = RoundedCornerShape(5),
                )

        )
    }
}

@Composable
fun OutputScreen(
    navController: NavHostController
){
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        Color(0xFF197E63))
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PromptOutput(value = story)
        Row(Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    navController.navigate(Screen.Home.route)
                },
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 8.dp, top = 16.dp)
                    .height(75.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF197E63),
                    contentColor = Color.White
                )
            ){
                Icon(Icons.Outlined.ArrowBack, "Back")
            }
            Button(
                onClick = {
                    clipboardManager.setText(AnnotatedString(story))
                },
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = 8.dp, top = 16.dp)
                    .height(75.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF197E63),
                    contentColor = Color.White
                )
            ){
                Icon(painter = painterResource(id = R.drawable.ic_outline_content_copy_24), "Copy")
            }

        }
    }
}