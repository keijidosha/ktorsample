import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.post
import kotlinx.coroutines.experimental.runBlocking
import io.ktor.http.HeadersBuilder

fun main( args: Array<String> ) {
    /*
    val httpRequestBuilder1: HttpRequestBuilder.() -> Unit = {
        val headersBuilder: HeadersBuilder.() -> Unit = {
            append( "Content-Type", "application/x-www-form-urlencoded" )
        }
        headers( headersBuilder )
    }
    */
    /*
    val httpRequestBuilder2: HttpRequestBuilder.() -> Unit = {
        headers{
            append( "Content-Type", "application/x-www-form-urlencoded" )
        }
    }
    */
    HttpClient( CIO ).use {
        val response = runBlocking {
            it.post<String>( scheme = "http", host = "localhost", path = "/postform.php", port = 8000,
                body = "data=hoge", block = {
                    headers{
                        append( "Content-Type", "application/x-www-form-urlencoded" )
                    }
                }
                //body = "data=hoge", block = httpRequestBuilder2 )
            )
        }

        println( response )
    }
}