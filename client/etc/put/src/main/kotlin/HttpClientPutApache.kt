import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.request
import io.ktor.client.response.HttpResponse
import io.ktor.client.response.readText
import io.ktor.http.HttpMethod
import io.ktor.content.OutgoingContent.ReadChannelContent
import io.ktor.cio.readChannel
import kotlinx.coroutines.experimental.runBlocking
import java.io.File

fun main( args: Array<String> ) {
    HttpClient( Apache ).use {
        val response = runBlocking {
            it.request<HttpResponse> {
                url {
                    host = "localhost"
                    port = 8000
                    encodedPath = "/put.php"
                }
                method = HttpMethod.Put
                body = object: ReadChannelContent() {
                    override fun readFrom() = File( "/Users/okumura/Dropbox/neix/projects/kotlin/lib/alpn-api-1.1.3.v20160715.jar" ).readChannel()
                }
            }.use { res ->
                println( res.version )
                println( res.status )
                res.headers.forEach { k, v ->
                    println( "$k: $v" )
                }
                res.readText()
            }
        }

        println( "$response" )
    }
}