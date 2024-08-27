
package com.example.calculator

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private lateinit var editTextTime1: EditText
    private lateinit var editTextTime2: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonSubtract: Button
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        editTextTime1 = findViewById(R.id.editText1)
        editTextTime2 = findViewById(R.id.editText2)
        buttonAdd = findViewById(R.id.buttonPlus)
        buttonSubtract = findViewById(R.id.buttonMinus)
        textViewResult = findViewById(R.id.textViewResult)

        buttonAdd.setOnClickListener {
            val time1InSeconds = convertToSeconds(editTextTime1.text.toString())
            val time2InSeconds = convertToSeconds(editTextTime2.text.toString())
            val totalSeconds = time1InSeconds + time2InSeconds
            val result = convertToTimeFormat(totalSeconds)
            textViewResult.text = result
            showToast("Результат: $result")
            updateTextStyle(result)
        }

        buttonSubtract.setOnClickListener {
            val time1InSeconds = convertToSeconds(editTextTime1.text.toString())
            val time2InSeconds = convertToSeconds(editTextTime2.text.toString())
            val differenceSeconds = time1InSeconds - time2InSeconds
            textViewResult.text = if (differenceSeconds < 0) {
                "Результат не может быть отрицательным."
            } else {
                val result = convertToTimeFormat(differenceSeconds)
                showToast("Результат: $result")
                updateTextStyle(result)
                result
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_clear -> {
                clearFields()
                true
            }
            R.id.menu_exit -> {
                showExitMessage()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showExitMessage() {
        Toast.makeText(this, "Приложение закрыто", Toast.LENGTH_SHORT).show()
        finish() // Завершение работы приложения
    }
    override fun onBackPressed() {
        super.onBackPressed()
        showExitMessage()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Приложение закрыто", Toast.LENGTH_SHORT).show()
    }
    private fun clearFields() {
        editTextTime1.text.clear()
        editTextTime2.text.clear()
        textViewResult.setTextColor(Color.parseColor("#000000"))
        textViewResult.text = "Result"
        Toast.makeText(this, "Данные очищены", Toast.LENGTH_SHORT).show()
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateTextStyle(result: String) {
        val color = if (result.isEmpty() || result == "Result") {
            "#000000"
        } else {
            "#8B0000"
        }
        textViewResult.setTextColor(Color.parseColor(color))
    }

    private fun convertToSeconds(timeString: String): Int {
        var totalSeconds = 0
        // Регулярное выражение для поиска времени в формате "1h 23m 44s"
        val regex = Regex("(\\d+)([hms])")
        val matches = regex.findAll(timeString)

        for (match in matches) {
            val value = match.groupValues[1].toInt()
            val unit = match.groupValues[2]
            totalSeconds += when (unit) {
                "h" -> value * 3600
                "m" -> value * 60
                "s" -> value
                else -> 0
            }
        }

        return totalSeconds
    }

    private fun convertToTimeFormat(seconds: Int): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val remainingSeconds = seconds % 60
        return "$hours h $minutes m $remainingSeconds s"
    }
}