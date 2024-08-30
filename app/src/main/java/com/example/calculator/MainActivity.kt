package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var canAddOperator = false
    private var canAddDecimal = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    fun numberAction(view: View) {
        var workingsTV : TextView = findViewById(R.id.workings_textview)
        var resultTV : TextView = findViewById(R.id.result_textview)

        // cast view as a button so it is possible to invoke the text attribute
        val button = view as Button
        val buttonText = button.text.toString()

        if (buttonText == ".") {
            if (canAddDecimal) { // adds a decimal only if there was no decimal before
                workingsTV.append(buttonText)
            }
            canAddDecimal = false
        } else {
            workingsTV.append(buttonText)
        }

        canAddOperator = true


    }


    fun operatorAction(view: View) {
        var workingsTV : TextView = findViewById(R.id.workings_textview)
        var resultTV : TextView = findViewById(R.id.result_textview)

        val button = view as Button
        val buttonText = button.text.toString()

        if (canAddOperator) {
            workingsTV.append(buttonText)
            canAddOperator = false
            canAddDecimal = true
        }
    }

    fun allClearAction(view: View) {
        var workingsTV : TextView = findViewById(R.id.workings_textview)
        var resultTV : TextView = findViewById(R.id.result_textview)
        workingsTV.text = ""
        resultTV.text = ""
    }

    fun backspaceAction(view: View) {
        var workingsTV : TextView = findViewById(R.id.workings_textview)
        var resultTV : TextView = findViewById(R.id.result_textview)
        val workingsLength = workingsTV.length()
        if (workingsLength > 0) {
            workingsTV.text = workingsTV.text.subSequence(0, workingsLength - 1)
        }
    }

    fun equalsAction(view: View) {
        var workingsTV : TextView = findViewById(R.id.workings_textview)
        var resultTV : TextView = findViewById(R.id.result_textview)
        resultTV.text = calculateResults()
    }

    private fun calculateResults() : String {
        val digitsOperators = listDigitsOperators()
        if (digitsOperators.isEmpty()) { return "" }

        val multiplicationDivision = calculateAllMultiplicationDivision(digitsOperators)
        if (multiplicationDivision.isEmpty()) { return "" }


        val result = calculateAdditionSubtraction(multiplicationDivision)
        return result.toString()
    }

    private fun calculateAllMultiplicationDivision(passedList : MutableList<Any>) : MutableList<Any> {
        var workList = passedList
        while (workList.contains('*') || workList.contains('/')) {
            workList = calculateMultiplicationDivision(workList)

        }
        return workList
    }

    private fun calculateMultiplicationDivision(passedList: MutableList<Any>) : MutableList<Any> {
        var newList = mutableListOf<Any>()
        var restartIndex = passedList.size
        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex) {
                val operator = passedList[i]
                val firstDigit = passedList[i-1] as Float
                val secondDigit = passedList[i+1] as Float
                when (operator) {
                    '*' ->  {
                        newList.add(firstDigit * secondDigit)
                        restartIndex = i + 1
                    }
                    '/' -> {
                        newList.add(firstDigit / secondDigit)
                        restartIndex = i + 1
                    }
                    else -> {
                        newList.add(firstDigit)
                        newList.add(operator)
                    }
                }
            }
            if (i > restartIndex) {
                newList.add(passedList[i])
            }
        }
        return newList
    }

    private fun calculateAdditionSubtraction(passedList: MutableList<Any>) : Float {
        var result = passedList[0] as Float

        for(i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex) {
                val operator = passedList[i]
                val secondDigit = passedList[i + 1] as Float
                if (operator == '+') {
                    result += secondDigit
                }
                if (operator == '-') {
                    result -= secondDigit
                }
            }
        }

        return result
    }


    private fun listDigitsOperators() : MutableList<Any> {
        var workingsTV : TextView = findViewById(R.id.workings_textview)
        var resultTV : TextView = findViewById(R.id.result_textview)

        val list = mutableListOf<Any>()
        var currentDigit = ""

        for (character in workingsTV.text) {

            if (character.isDigit() || character == '.') {
                currentDigit += character
            } else {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }


        if (currentDigit != "") {
            list.add(currentDigit.toFloat())
        }
        return list
    }
}
