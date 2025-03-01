package com.test.presentation.test

import android.content.Context
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import java.io.File

@Serializable
data class ImpData(
    val name: String,
    val dob: String,
    val email: String,
    val address: String
)

@Serializable
data class UserData(
    val user_id: Int,
    val channel: String
)

class FikerFreeHelper {
    private val ktorHelper = KtorHelper()

    private val TAG = "FikerFreeHelper"

    suspend fun fetchProfilesDataInRange(start: Int, end: Int): List<ImpData> {
        return withContext(Dispatchers.IO) {
            val dataListDef = mutableListOf<Deferred<String?>>()
            for (i in start..end) {
                dataListDef.add(
                    async {
                        val response = fetchProfileDataCore(i)
                        Log.d("SingleFetchedData", "Single response: $response")
                        response
                    })
            }
            dataListDef.awaitAll().mapNotNull {
                try {
                    val data = ktorHelper.getHelper().decodeFromString<Fikr>(it ?: "")
                    ImpData(
                        data.result.name ?: "",
                        data.result.dob ?: "",
                        data.result.email ?: "",
                        data.result.residential_address ?: ""
                    )
                } catch (_: Exception) {
                    null
                }
            }
        }
    }

    //    110564
    fun fetchProfileData(context: Context) {
        Log.d(TAG, "fetchProfileData: called")
        CoroutineScope(Dispatchers.IO).launch {
            val idsList = List(110564) {
//            val idsList = List(20) {
                it
            }
            var fetchedData = ""
            idsList.chunked(2000).forEach {
//            idsList.chunked(5).forEach {
                val start = it[0]
                val end = it[it.lastIndex]
                Log.d(TAG, "Looping: ${it[0]}-${it[it.lastIndex]}")
                val response = fetchProfilesDataInRange(start, end)
                Log.d(TAG, "Chunk Response : ${response.size} ")
                fetchedData += ktorHelper.getHelper().encodeToString(response)
                createTextFile(context = context, fileName = "${it[0]}.txt", content = fetchedData)
            }
        }
    }

    private suspend fun createTextFile(context: Context, fileName: String, content: String) {
        withContext(Dispatchers.IO) {
            val file = File(context.filesDir, fileName)
            file.writeText(content)
        }
    }

    private suspend fun fetchProfileDataCore(id: Int): String? {
        val response = ktorHelper.hitAndGetResponse(
            url = "https://fikrfree.com.pk/api/user/profile",
            headers = mapOf(
                "authorization" to "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYXp6ZmlrcmZyZWVNQVBQIiwiaWF0IjoxNzQwNzcxOTc2LCJleHAiOjE3NDA4MTUxNzZ9.krD1pJPY8jwYADhq7Ei-JDSz9m5eHEIKZLbNzG2He9jo7CkJ53s0hkF5fKJHrXL7EqmhaL0E78LI39qYecE7_Q",
                "Content-Type" to "application/json"
            ),
            body = UserData(id, "App")
        )
        return response
    }
}