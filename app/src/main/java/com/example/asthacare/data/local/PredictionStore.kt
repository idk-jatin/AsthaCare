package com.example.asthacare.data.local

import android.content.Context
import com.example.asthacare.data.model.PredictionResult
import com.google.gson.Gson

class PredictionStore(context: Context) {

    private val prefs =
        context.getSharedPreferences("asthacare_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun save(result: PredictionResult) {
        prefs.edit()
            .putString("last_prediction", gson.toJson(result))
            .apply()
    }

    fun load(): PredictionResult? {
        val json = prefs.getString("last_prediction", null)
        return json?.let {
            gson.fromJson(it, PredictionResult::class.java)
        }
    }
}