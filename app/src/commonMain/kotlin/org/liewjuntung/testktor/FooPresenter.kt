package org.liewjuntung.testktor

import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.url
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.*
import kotlinx.serialization.internal.StringDescriptor
import kotlinx.serialization.json.Json
import org.liewjuntung.testdb.Comment
import org.liewjuntung.testdb.KtDb

@Serializable
data class HeaderResponse(
    @SerialName("Accept") val accept: String,
    @SerialName("Accept-Encoding") val acceptEncoding: String,
    @SerialName("Host") val host: String
)

@Serializable
data class FooResponse(val headers: HeaderResponse)

@Serializable
data class FooComment(val postId: Int, val id: Int, val name: String, val email: String, val body: String)

@Serializable
data class FooCommentList(
    @SerialName("fooComentList") val fooCommentList: List<FooComment>? = null
)

interface FooView {
    fun fooResult(fooCommentList: List<Comment>)
}

expect val dbDriver: SqlDriver

enum class FooEnum(val weather: String, val number: Int) {
    BAR("raining", 123)
}

class FooPresenter(var fooView: FooView? = null) {
    private val dispatcher = CommonCoroutineContext
    private val db = KtDb(dbDriver)

    val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json.nonstrict).apply {
                setMapper(FooCommentList::class, FooCommentList.serializer())
            }
        }
    }

    val httpGetUrl = "https://api.myjson.com/bins/zu96n"
    fun callNetwork() {
        GlobalScope.launch(dispatcher) {
            val result = client.get<FooCommentList> {
                url(httpGetUrl)
                header(HttpHeaders.Authorization, "JWT xaoeuaoekaeuaoeuaoeuaokeu")
            }

            result.fooCommentList?.apply {
                forEach {
                    db.dbCommentQueries.insertComment(it.postId.toLong(), it.name, it.name, it.body)
                }
            }
        }
    }

    fun loadDb() {
        GlobalScope.launch(dispatcher) {
            val list = db.dbCommentQueries.getAllComments().executeAsList()
            val result = client.get<FooCommentList> {
                url(httpGetUrl)
                header(HttpHeaders.Authorization, "JWT xaoeuaoekaeuaoeuaoeuaokeu")
            }
            fooView?.fooResult(list)
        }
    }

    fun uploadStuff(uploadUrl: String, byteArray: ByteArray, filename: String) {
        GlobalScope.launch(dispatcher) {

            client.submitForm {
                url(uploadUrl)
                method = HttpMethod.Post
                body = MultiPartFormDataContent(
                    formData {
                        append(
                            key = "file",
                            value = byteArray,
                            headers = Headers.build {
                                append(HttpHeaders.ContentDisposition, "filename=${filename}")
                            }
                        )

                    }
                )
            }
        }
    }
}
