<?php
$data = file_get_contents( "php://input" );
$file = fopen( "hoge.bin", "w" );
fwrite( $file, $data );
fclose( $file );
echo "OK";
?>