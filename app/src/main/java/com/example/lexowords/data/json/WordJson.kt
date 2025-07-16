package com.example.lexowords.data.json

data class WordJson(
    val word: String,
    val translation: String,
    val transcription: String,
    val tags: List<String>?
)
