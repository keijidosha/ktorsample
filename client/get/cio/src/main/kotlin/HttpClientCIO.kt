import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.*
import io.ktor.client.response.*
import kotlinx.coroutines.experimental.*

import io.ktor.client.features.cookies.*
//import io.ktor.http.*

fun main( args: Array<String> ) {
    val client = HttpClient( CIO )

    val response = runBlocking {
        val res = client.get<HttpResponse>( host = "www.yahoo.co.jp" )
        client.cookies( "www.yahoo.co.jp" ).forEach { k, v ->
            println( "${k}: ${v}" )
        }
        println( res.version )
        println( res.status )
        res.headers.forEach{ k, v ->
            println( "$k: $v" )
        }
        println()
        res.readText()
    }

    println( response )

    client.close()
}