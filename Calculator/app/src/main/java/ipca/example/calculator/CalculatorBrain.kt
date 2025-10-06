package ipca.example.calculator

class CalculatorBrain {

    enum class Operation(op: String) {
        ADD("+"),
        SUBTRACT("-"),
        MULTIPLY("×"),
        DIVIDE("÷"),
        EQUAL("="),
        SQRT("√"),
        PERCENTAGE("%"),
        CLEAR("AC"),
        CLEAR_ENTRY("C");


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
                    "AC" -> CLEAR
                    "C" -> CLEAR_ENTRY
                    else -> EQUAL
                }
            }
        }
    }


    var  operand = 0.0
    var  operation : Operation? = null

    fun unaryOperation(newOperand : Double, newOperation : Operation) {
        var result = newOperand
        when(newOperation){
            Operation.SQRT -> result = kotlin.math.sqrt(newOperand)
            Operation.PERCENTAGE -> result = newOperand / 100
            Operation.EQUAL -> {
                operation?.let {
                    when(operation){
                        Operation.ADD ->  { result = operand + newOperand }
                        Operation.SUBTRACT -> result = operand - newOperand
                        Operation.MULTIPLY -> result = operand * newOperand
                        Operation.DIVIDE -> result = operand / newOperand
                        else -> {}
                    }
                }
            }
            Operation.CLEAR -> {
                operation = null
                result = 0.0
            }
            Operation.CLEAR_ENTRY -> result = 0.0
            else -> {}

        }
        operand = result
    }

    fun doOperation(newOperand : Double, newOperation : Operation) {
        var result = newOperand
        when(operation){
            Operation.ADD ->  { result = operand + newOperand }
            Operation.SUBTRACT -> result = operand - newOperand
            Operation.MULTIPLY -> result = operand * newOperand
            Operation.DIVIDE -> result = operand / newOperand
            else -> {}
        }

        operation = newOperation
        operand = result
    }

}