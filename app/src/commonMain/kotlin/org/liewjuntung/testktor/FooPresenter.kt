package org.liewjuntung.testktor

import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.url
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FooPresenter {
    private val dispatcher = CommonCoroutineContext

    val client = HttpClient {
        install(JsonFeature) {

        }
    }

    fun uploadStuff(uploadUrl: String, fileUrl: String, filename: String) {
        GlobalScope.launch(dispatcher) {
            val byteArray = readFile(fileUrl)

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
