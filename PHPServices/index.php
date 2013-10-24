<?php 

require_once 'Services.class.php';

$controller = new Services();

$action = $_GET['action'];

switch ($action) {
	case "getData":
		$table = $_GET['table'];
		if ($table == null) exit;
		$controller->getDbData($table);
		break;

	default:
		break;
}



?>