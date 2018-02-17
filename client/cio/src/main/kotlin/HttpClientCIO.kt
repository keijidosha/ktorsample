import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.*
import kotlinx.coroutines.experimental.*

fun main( args: Array<String> ) {
    val client = HttpClient( CIO )

    val response = runBlocking {
        client.get<String>( host = "www.yahoo.co.jp" )
    }

    println( response )

    client.close()
}