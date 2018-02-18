import io.ktor.client.HttpClient
import io.ktor.client.engine.jetty.Jetty
import io.ktor.client.request.*
import kotlinx.coroutines.experimental.*

fun main( args: Array<String> ) {
    val client = HttpClient( Jetty )

    val response = runBlocking {
        client.get<String>( scheme = "https", host = "www.evernote.com", port = 443 )
    }

    println( response )

    client.close()
}