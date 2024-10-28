package com.example.simplecustomview

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

// Не очень технически грамотное решение, лишь для наглядности и упрощения примера

const val HAPPY_STATE = 1
const val SAD_STATE = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Определим переменную для обращения к View
        val emojiView = findViewById<View>(R.id.emotionalFaceView)

        //Назначим слушатель на нажатие нашей View
        emojiView.setOnClickListener {
            happinessState = if (happinessState == HAPPY_STATE) {
                SAD_STATE
            } else {
                HAPPY_STATE
            }
        }
    }

    companion object {
        // Добавим изменяемую переменную для хранения состояния нашей View
        var happinessState: Int = HAPPY_STATE
    }
}

