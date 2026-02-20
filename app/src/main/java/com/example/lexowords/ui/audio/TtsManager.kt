package com.example.lexowords.ui.audio

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale
import java.util.UUID

class TtsManager(
    context: Context,
    private val locale: Locale = Locale.US,
) {
    private var tts: TextToSpeech? = null
    private var isReady = false

    init {
        tts =
            TextToSpeech(context.applicationContext) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    tts?.language = locale
                    isReady = true
                }
            }
    }

    fun speak(text: String) {
        if (!isReady) return
        val utteranceId = UUID.randomUUID().toString()
        tts?.speak(text.trim(), TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        isReady = false
    }
}
