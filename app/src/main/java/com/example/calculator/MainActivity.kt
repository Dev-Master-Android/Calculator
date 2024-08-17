
package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editTextTime1: EditText
    private lateinit var editTextTime2: EditText
    private lateinit var buttonAdd: Button
    private lateinit var buttonSubtract: Button
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        }

        buttonSubtract.setOnClickListener {
            val time1InSeconds = convertToSeconds(editTextTime1.text.toString())
            val time2InSeconds = convertToSeconds(editTextTime2.text.toString())
            val differenceSeconds = time1InSeconds - time2InSeconds
            textViewResult.text = if (differenceSeconds < 0) {
                "Результат не может быть отрицательным."
            } else {
                convertToTimeFormat(differenceSeconds)
            }
        }
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