<?php
$method = $_SERVER['REQUEST_METHOD'];

if( $method == 'PUT' ) {
    $data = file_get_contents( "php://input" );
    $file = fopen( "hoge.bin", "w" );
    fwrite( $file, $data );
    fclose( $file );
    echo "OK";
} else {
    echo $method . " is not allowed here";
}
?>