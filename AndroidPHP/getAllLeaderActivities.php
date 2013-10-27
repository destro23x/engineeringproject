<?php

$response = array ();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$leader = $_POST['leader'];

$query_search = "SELECT * FROM Wydarzenia WHERE Wydarzenia_Osoba = '".$leader."' ";
$result = mysql_query($query_search) or die(mysql_error());

if (mysql_num_rows($result) > 0) {
    
    $response["events"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $event = array();
        $event["pid"] = $row["Wydarzenia_ID"];
        $event["title"] = $row["Wydarzenia_Tytul"];
        $event["note"] = $row["Wydarzenia_Notka"];
        $event["startDate"] = $row["Wydarzenia_StartDate"];
        $event["endDate"] = $row["Wydarzenia_KoniecDate"];
        $event["color"] = $row["Wydarzenia_Kolor"];
		$event["person"] = $row["Wydarzenia_Osoba"];



        // push single product into final response array
        array_push($response["events"], $event);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No students found";

    // echo no users JSON
    echo json_encode($response);
}
?>
