import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.post
import kotlinx.coroutines.experimental.runBlocking
import io.ktor.http.HeadersBuilder
import java.io.File
import java.io.FileInputStream
import io.ktor.content.OutgoingContent.WriteChannelContent
import kotlinx.coroutines.experimental.io.ByteWriteChannel
import io.ktor.http.ContentType
import io.ktor.http.Headers

fun main( args: Array<String> ) {

    HttpClient( CIO ).use {
        val file = File( "/Users/okumura/Dropbox/neix/projects/kotlin/lib/kotlinx-coroutines-jdk8-0.22.1.jar" )

        class FileWriteContent: WriteChannelContent() {
            override val contentType = ContentType( "application", "octet-stream" )
            override val headers: Headers
                get() = HeadersBuilder().apply {
                        append( "Transfer-Encoding", "chunked" )
                    }.build()

            override suspend fun writeTo(channel: ByteWriteChannel) {
                val bufSize = 64
                val buf = ByteArray( bufSize )
                var readedBytes = 0
                FileInputStream( file ).use {
                    while( run{ readedBytes = it.read( buf ); readedBytes >= 0 } ) {
                        channel.writeFully( buf, 0, readedBytes )
                    }
                }
            }
        }

        println( runBlocking {
            it.post<String>( scheme = "http", host = "localhost", path = "/postbin.php", port = 8000,
                body = FileWriteContent()
            )
        })

    }
}