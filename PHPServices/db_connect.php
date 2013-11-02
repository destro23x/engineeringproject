<?php

class DB_CONNECT {

	function __construct(){
		$this->connect();
	}
	function __destruct(){
		$this->disconnect();
	}
	
	function connect(){
		require_once __DIR__ . '/db_config.php';
		
		$con = mysql_connect(hostname_localhost, username_localhost, password_localhost) or die(mysql_error());
		
		$db = mysql_select_db(database_localhost) or die(mysql_error()) or die(mysql_error());
		
		return $con;
	}
	
	function disconnect(){
		mysql_close();
	}

}

?>
