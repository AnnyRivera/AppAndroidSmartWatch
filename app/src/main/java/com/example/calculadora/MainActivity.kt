package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora.ui.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    CalculatorScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var display by remember { mutableStateOf("0") }
    var operand1 by remember { mutableStateOf("") }
    var operand2 by remember { mutableStateOf("") }
    var operator by remember { mutableStateOf("") }

    // Definir el modifier para los botones
    val buttonModifier = Modifier
        .size(70.dp)
        .padding(4.dp)

    // Lambdas para manejar los eventos de click
    val handleNumberClick: (String) -> Unit = { number ->
        if (operator.isEmpty()) {
            operand1 += number
            display = operand1
        } else {
            operand2 += number
            display = operand2
        }
    }

    val handleOperatorClick: (String) -> Unit = { op ->
        if (operand1.isNotEmpty() && operand2.isEmpty()) {
            operator = op
        }
    }

    val handleClearClick: () -> Unit = {
        operand1 = ""
        operand2 = ""
        operator = ""
        display = "0"
    }

    val handleEqualsClick: () -> Unit = {
        val result = when (operator) {
            "+" -> operand1.toDouble() + operand2.toDouble()
            "-" -> operand1.toDouble() - operand2.toDouble()
            "*" -> operand1.toDouble() * operand2.toDouble()
            "/" -> if (operand2 != "0") operand1.toDouble() / operand2.toDouble() else Double.NaN
            else -> null
        }
        display = result?.let {
            if (it == it.toInt().toDouble()) it.toInt().toString() else it.toString()
        } ?: "Error"
        operand1 = display
        operand2 = ""
        operator = ""
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.DarkGray, Color.Black)
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = display,
            color = Color.White,
            fontSize = 36.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )

        // Fila 1 de botones
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalcButtonWithText("1", buttonModifier) { handleNumberClick("1") }
            CalcButtonWithText("2", buttonModifier) { handleNumberClick("2") }
            CalcButtonWithText("3", buttonModifier) { handleNumberClick("3") }
            CalcButtonWithOperator("+", buttonModifier) { handleOperatorClick("+") }
        }

        // Fila 2 de botones
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalcButtonWithText("4", buttonModifier) { handleNumberClick("4") }
            CalcButtonWithText("5", buttonModifier) { handleNumberClick("5") }
            CalcButtonWithText("6", buttonModifier) { handleNumberClick("6") }
            CalcButtonWithOperator("-", buttonModifier) { handleOperatorClick("-") }
        }

        // Fila 3 de botones
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalcButtonWithText("7", buttonModifier) { handleNumberClick("7") }
            CalcButtonWithText("8", buttonModifier) { handleNumberClick("8") }
            CalcButtonWithText("9", buttonModifier) { handleNumberClick("9") }
            CalcButtonWithOperator("*", buttonModifier) { handleOperatorClick("*") }
        }

        // Fila 4 de botones
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalcButtonSimple("C", buttonModifier) { handleClearClick() }
            CalcButtonWithText("0", buttonModifier) { handleNumberClick("0") }
            CalcButtonSimple("=", buttonModifier) { handleEqualsClick() }
            CalcButtonWithOperator("/", buttonModifier) { handleOperatorClick("/") }
        }
    }
}

@Composable
fun CalcButtonWithText(symbol: String, modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray
        ),
        shape = CircleShape
    ) {
        Text(text = symbol, fontSize = 24.sp, color = Color.White)
    }
}

@Composable
fun CalcButtonWithOperator(symbol: String, modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFff8c00)
        ),
        shape = CircleShape
    ) {
        Text(text = symbol, fontSize = 24.sp, color = Color.White)
    }
}

@Composable
fun CalcButtonSimple(symbol: String, modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red
        ),
        shape = CircleShape
    ) {
        Text(text = symbol, fontSize = 24.sp, color = Color.White)
    }
}
