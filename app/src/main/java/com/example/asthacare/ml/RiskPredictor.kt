package com.example.asthacare.ml

import android.content.Context
import ai.onnxruntime.*
import java.nio.FloatBuffer

class RiskPredictor(context: Context) {

    private val env = OrtEnvironment.getEnvironment()
    private val session: OrtSession

    init {
        val modelBytes = context.assets
            .open("air_health_model.onnx")
            .use { it.readBytes() }

        session = env.createSession(modelBytes, OrtSession.SessionOptions())
    }

    fun predict(features: FloatArray): Float? {
        return try {

            val inputName = session.inputNames.first()

            val floatBuffer = java.nio.FloatBuffer.wrap(features)

            OnnxTensor.createTensor(
                env,
                floatBuffer,
                longArrayOf(1, features.size.toLong())
            ).use { tensor ->

                session.run(mapOf(inputName to tensor)).use { results ->

                    val value = results[0].value

                    when (value) {
                        is LongArray -> value[0].toFloat()
                        is Array<*> -> {
                            val first = value[0]
                            when (first) {
                                is LongArray -> first[0].toFloat()
                                is FloatArray -> first[0]
                                is DoubleArray -> first[0].toFloat()
                                else -> null
                            }
                        }

                        else -> null
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}