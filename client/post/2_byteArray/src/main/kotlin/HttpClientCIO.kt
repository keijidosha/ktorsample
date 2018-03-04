import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.post
import kotlinx.coroutines.experimental.runBlocking
import io.ktor.http.HeadersBuilder
import java.io.File
import java.io.FileInputStream
import io.ktor.content.OutgoingContent.ByteArrayContent

fun main( args: Array<String> ) {

    HttpClient( CIO ).use {
        val file = File( "build.gradle" )
        val buf = ByteArray( file.length().toInt())
        FileInputStream( file ).use {
            it.read( buf )
        }

        val response = runBlocking {
            it.post<String>( scheme = "http", host = "localhost", path = "/postbin.php", port = 8000,
                body = object: ByteArrayContent() {
                    override val contentLength get() = file.length()
                    override fun bytes() = buf
                },
                block = {
                    headers{
                        append( "Transfer-Encoding", "chunked" )
                    }
                }
            )
        }

        println( response )
    }
}