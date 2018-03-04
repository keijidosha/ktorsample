import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.post
import kotlinx.coroutines.experimental.runBlocking
//import io.ktor.http.HeadersBuilder
import java.io.File
import java.io.FileInputStream
import java.util.Random
import io.ktor.content.OutgoingContent.WriteChannelContent
import kotlinx.coroutines.experimental.io.ByteWriteChannel
import kotlinx.coroutines.experimental.io.writeStringUtf8
import io.ktor.http.ContentType
//import io.ktor.http.Headers

fun main( args: Array<String> ) {

    HttpClient( Apache ).use {
        val file = File( "/Users/okumura/Dropbox/neix/projects/kotlin/lib/kotlinx-coroutines-jdk8-0.22.1.jar" )

        /*
        class FileWriteContent: WriteChannelContent() {
            private val boundary = generateBoundary()
            override val contentType = ContentType( "multipart", "form-data; boundary=$boundary" )
            //override val headers: Headers
            //    get() = HeadersBuilder().apply {
            //            append( "Transfer-Encoding", "chunked" )
            //        }.build()
            override suspend fun writeTo( channel: ByteWriteChannel ) {
                channel.writeStringUtf8( "--$boundary\r\n" )
                channel.writeStringUtf8( "Content-Disposition: form-data; name=\"file\"; filename=\"hoge.jar\"\r\n" )
                channel.writeStringUtf8( "Content-Type: application/octet-stream\r\n\r\n" )

                val bufSize = 4096
                val buf = ByteArray( bufSize )
                var readedBytes = 0
                FileInputStream( file ).use {
                    while( run{ readedBytes = it.read( buf ); readedBytes >= 0 } ) {
                        channel.writeFully( buf, 0, readedBytes )
                    }
                }

                channel.writeStringUtf8( "\r\n--$boundary--\r\n" )
            }
        }
        */

        println( runBlocking {
            it.post<String>( scheme = "http", host = "localhost", path = "/postfile.php", port = 8000,
                /*
                body = FileWriteContent()
                , block = {
                    headers {
                        append( "Content-Type", "multipart/form-data; boundary=" )
                    }
                }
                */
                body = object: WriteChannelContent() {
                    private val boundary = generateBoundary()
                    override val contentType = ContentType( "multipart", "form-data; boundary=$boundary" )
                    override suspend fun writeTo( channel: ByteWriteChannel ) {
                        channel.writeStringUtf8( "--$boundary\r\n" )
                        channel.writeStringUtf8( "Content-Disposition: form-data; name=\"file\"; filename=\"hoge.jar\"\r\n" )
                        channel.writeStringUtf8( "Content-Type: application/octet-stream\r\n\r\n" )

                        val bufSize = 4096
                        val buf = ByteArray( bufSize )
                        var readedBytes = 0
                        FileInputStream( file ).use {
                            while( run{ readedBytes = it.read( buf ); readedBytes >= 0 } ) {
                                channel.writeFully( buf, 0, readedBytes )
                            }
                        }

                        channel.writeStringUtf8( "\r\n--$boundary--\r\n" )
                    }

                    private fun generateBoundary(): String {
                        val chars = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_"
                        val rand = Random()
                        val boundary = StringBuilder( 40 )

                        repeat( 40 ) {
                            val r = rand.nextInt( chars.length )
                            boundary.append( chars.substring( r, r + 1 ))
                        }

                        return boundary.toString()
                    }
                }
            )
        })

    }
}
