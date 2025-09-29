package ipca.example.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class CalculatorBrain {

    enum class Operation(op: String) {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("×"),
        DIVIDE("÷"),
        EQUAL("="),
        SQRT("√"),
        PERCENTAGE("%");

        companion object {
            fun parseOperation(op: String): Operation {
                return when (op) {
                    "+" -> ADD
                    "-" -> SUBTRACT
                    "×" -> MULTIPLY
                    "÷" -> DIVIDE
                    "=" -> EQUAL
                    "√" -> SQRT
                    "%" -> PERCENTAGE
                    else -> EQUAL
                }
            }
        }
    }


    var  operand = 0.0
    var  operation : Operation? = null

    fun doOperation(newOperand : Double, newOperation : Operation) {
        var result = newOperand
        when(newOperation){
            Operation.ADD -> result = operand + newOperand
            Operation.SUBTRACT -> result = operand - newOperand
            Operation.MULTIPLY -> result = operand * newOperand
            Operation.DIVIDE -> result = operand / newOperand
            else->{}
        }
        operand = result
    }

}