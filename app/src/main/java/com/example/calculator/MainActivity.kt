package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding
import com.udojava.evalex.Expression
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var expression: TextView
    private lateinit var result: TextView
    private lateinit var clean: Button
    private lateinit var backSpace: Button
    private lateinit var percent: Button
    private lateinit var devide: Button
    private lateinit var multiply: Button
    private lateinit var sum: Button
    private lateinit var subtraction: Button
    private lateinit var equal: Button
    private lateinit var dot: Button
    private lateinit var zero: Button
    private lateinit var doubleZero: Button
    private lateinit var one: Button
    private lateinit var two: Button
    private lateinit var three: Button
    private lateinit var four: Button
    private lateinit var five: Button
    private lateinit var six: Button
    private lateinit var seven: Button
    private lateinit var eight: Button
    private lateinit var nine: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))

        expression = binding.layout.expression
        result = binding.layout.result
        clean = binding.layout.clean
        backSpace = binding.layout.backSpace
        percent = binding.layout.percent
        devide = binding.layout.devide
        multiply = binding.layout.multiply
        sum = binding.layout.sum
        subtraction = binding.layout.subtraction
        equal = binding.layout.equal
        dot = binding.layout.dot
        zero = binding.layout.zero
        doubleZero = binding.layout.doubleZero
        one = binding.layout.one
        two = binding.layout.two
        three = binding.layout.three
        four = binding.layout.four
        five = binding.layout.five
        six = binding.layout.six
        seven = binding.layout.seven
        eight = binding.layout.eight
        nine = binding.layout.nine
        expression.movementMethod = ScrollingMovementMethod()
        expression.isActivated = true
        expression.isPressed = true
        initButtons()
    }

    private fun initButtons() {
        var str: String
        fun numberClick(number: String) {
            expression.textSize = 60F
            result.textSize = 30F
            if (expression.text.toString().startsWith("0")) {
                str = expression.text.toString().replace("0", "") + number
                expressionText(str)
                resultText()
            } else {
                str = expression.text.toString() + number
                expressionText(str)
                resultText()
            }
        }
        fun addOperationSymbol(symbol: String) {
            expression.textSize = 60F
            result.textSize = 30F
            if (expression.text.toString().endsWith(symbol) ||
                expression.text.toString().endsWith("/") ||
                expression.text.toString().endsWith("*") ||
                expression.text.toString().endsWith("+") ||
                expression.text.toString().endsWith("-") ||
                expression.text.toString().endsWith(".")
            ) {
                str = expression.text.toString()
                expressionText(str)
            } else {
                str = expression.text.toString() + symbol
                expressionText(str)
            }
        }
        clean.setOnClickListener {
            expressionText("0")
            expression.textSize = 60F
            result.textSize = 30F
            resultText()
        }
        backSpace.setOnClickListener {
            if (expression.text.toString().isNotEmpty()) {
                val lastIndex = expression.text.toString().lastIndex
                str = expression.text.toString().substring(0, lastIndex)
                expressionText(str)
            } else {
                result.text = "0"
            }
        }
        equal.setOnClickListener {
            expression.textSize = 30F
            result.textSize = 60F
            expressionText(result.text.toString())
            if (result.text.toString() == getString(R.string.error)) {
                expressionText("0")
            }
        }

        devide.setOnClickListener { addOperationSymbol("/") }
        multiply.setOnClickListener { addOperationSymbol("*") }
        sum.setOnClickListener { addOperationSymbol("+") }
        subtraction.setOnClickListener { addOperationSymbol("-") }
        dot.setOnClickListener { addOperationSymbol(".") }
        percent.setOnClickListener { numberClick("%") }
        zero.setOnClickListener { numberClick("0") }
        doubleZero.setOnClickListener { numberClick("00") }
        one.setOnClickListener { numberClick("1") }
        two.setOnClickListener { numberClick("2") }
        three.setOnClickListener { numberClick("3") }
        four.setOnClickListener { numberClick("4") }
        five.setOnClickListener { numberClick("5") }
        six.setOnClickListener { numberClick("6") }
        seven.setOnClickListener { numberClick("7") }
        eight.setOnClickListener { numberClick("8") }
        nine.setOnClickListener { numberClick("9") }
    }

    private fun expressionText(str: String) { expression.text = str }
    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun resultText() {
        try {
            val res = Expression(expression.text.toString()).eval()
            val decimalFormat = DecimalFormat("#.##")
            result.text = decimalFormat.format(res.toDouble())
        } catch (e: Exception) {
            result.text = getString(R.string.error)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_exit -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}