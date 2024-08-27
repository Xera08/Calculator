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
    private var canAddOperator= false
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
        val button = view as Button
        val buttonText = button.text.toString()

            if (workingsTV.text == "."){
                if (canAddDecimal) {
                    workingsTV.append(buttonText)
                }
                canAddDecimal = false
            }
            else {
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
        workingsTV.text = "0"
        resultTV.text = "0"
    }

    fun backspaceAction(view: View) {
        var workingsTV : TextView = findViewById(R.id.workings_textview)
        var resultTV : TextView = findViewById(R.id.result_textview)
        val workingsLength = workingsTV.length()
        if (workingsLength > 1) {
            workingsTV.text = workingsTV.text.subSequence(0, workingsLength - 1)
        }
    }



}