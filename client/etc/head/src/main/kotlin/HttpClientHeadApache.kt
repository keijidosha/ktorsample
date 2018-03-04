import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.request
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import kotlinx.coroutines.experimental.runBlocking
import io.ktor.http.URLBuilder
import io.ktor.http.HttpMethod
import java.nio.charset.StandardCharsets

fun main( args: Array<String> ) {
    HttpClient( Apache ).use {
        val response = runBlocking {
            it.request<HttpResponse> {
                url { host = "localhost" }
                method = HttpMethod.Head
            }.use { res ->
                println( res.version )
                println( res.status )
                res.headers.forEach { k, v ->
                    println( "$k: $v" )
                }
                res.readText()
            }
        }

        println( "body=$response" )
    }
}