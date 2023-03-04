package com.example.numberguessinggame

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numberguessinggame.ui.theme.NumberGuessingGameTheme
import kotlin.random.Random.Default.nextInt
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var randomNumber = nextInt(1, 1000)

        setContent {
            NumberGuessingGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize()) {
                    NumberGuessingGameScreen(randomNumber)
                }
            }
        }
    }
}
//modifier = Modifier.background(MaterialTheme.colors.background)

@Composable
fun NumberGuessingGameScreen(randomNumber: Int) {
    var randomNumber by remember { mutableStateOf(randomNumber) }

    var inputNumber by remember { mutableStateOf("") }
    val input = inputNumber.toIntOrNull()

    var hint by remember { mutableStateOf("Let's Guess") }
    var count by remember { mutableStateOf(0) }

    var canGuess by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.baseline_numbers_24),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
        )
        Text(
            text = stringResource(R.string.app_name),
            color = MaterialTheme.colors.surface,
            fontFamily = FontFamily.Monospace,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.header_of_page),
            color = MaterialTheme.colors.surface,
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))

        EditNumberField(value = inputNumber,
            onValueChange = { inputNumber = it }
        )
        Spacer(Modifier.height(16.dp))

        if (canGuess) {
            Text(
                text = stringResource(R.string.hint, hint),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colors.surface,
                fontSize = 20.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))

            Button(onClick = {
                count += 1

                if (input == null) {
                    hint = "Let's Guess"
                } else if (input > randomNumber) {
                    hint = "Lower"
                } else if (input < randomNumber) {
                    hint = "Higher"
                } else {
                    canGuess = false
                } },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = MaterialTheme.colors.onPrimary)) {
                Text(stringResource(R.string.guess))
            }
        } else {
            Text(
                text = stringResource(R.string.success),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.count, count),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
        }

        Button(onClick = {
            count = 0
            canGuess = true
            randomNumber = nextInt(1, 1000) },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.onBackground,
                contentColor = Color.White)
        ){
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Refresh Icon",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(R.string.restart))
        }


    }
}

@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.your_guess)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() })
    )
}

//@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
fun LightThemePreview() {
    var randomNumber = nextInt(1, 1001)

    NumberGuessingGameTheme(darkTheme = false) {
        NumberGuessingGameScreen(randomNumber)
    }
}

/**
 * Composable that displays what the UI of the app looks like in dark theme in the design tab.
 */
@Preview(showBackground = true, backgroundColor = 0x757575)
@Composable
fun DarkThemePreview() {
    var randomNumber = nextInt(1, 1001)

    NumberGuessingGameTheme(darkTheme = true) {
        NumberGuessingGameScreen(randomNumber)
    }
}
