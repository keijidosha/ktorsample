import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.*
import kotlinx.coroutines.experimental.*

fun main( args: Array<String> ) {
    val client = HttpClient( Apache )

    val response = runBlocking {
        client.get<String>( scheme = "https", host = "www.yahoo.co.jp", port = 443 )
    }

    println( response )

    client.close()
}