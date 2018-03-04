import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.post
import kotlinx.coroutines.experimental.runBlocking
//import io.ktor.http.HeadersBuilder
import java.io.File
//import java.io.FileInputStream
import io.ktor.content.OutgoingContent.ReadChannelContent
import kotlinx.coroutines.experimental.io.ByteReadChannel
//import io.ktor.http.ContentType
//import io.ktor.http.Headers
import io.ktor.cio.readChannel

fun main( args: Array<String> ) {

    HttpClient( Apache ).use {
        val file = File( "/Users/okumura/Dropbox/neix/projects/kotlin/lib/ktor-client-core-0.9.2-SNAPSHOT.jar" )

        println( runBlocking {
            it.post<String>( scheme = "http", host = "localhost", path = "/postbin.php", port = 8000,
                body = object: ReadChannelContent() {
                    override fun readFrom(): ByteReadChannel = file.readChannel()
                }
                //, block = {
                //    headers {
                //        append( "Transfer-Encoding", "chunked" )
                //    }
                //}
            )
        })

    }
}