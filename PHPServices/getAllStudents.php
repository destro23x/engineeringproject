<?php

$response = array ();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$result = mysql_query("SELECT * FROM Klienci") or die(mysql_error());

if (mysql_num_rows($result) > 0) {
    
    $response["students"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $student = array();
        $student["pid"] = $row["Klienci_ID"];
        $student["name"] = $row["Klienci_Imie"];
        $student["surname"] = $row["Klienci_Nazwisko"];
        $student["pesel"] = $row["Klienci_Pesel"];
        $student["sex"] = $row["Klienci_Plec"];
        $student["phone"] = $row["Klienci_Telefon"];
		$student["mail"] = $row["Klienci_Mail"];
		$student["street"] = $row["Klienci_Ulica"];
		$student["city"] = $row["Klienci_Miasto"];
		$student["houseNumber"] = $row["Klienci_Numer_domu"];
		$student["cityCode"] = $row["Klienci_Kod"];
		$student["active"] = $row["Klienci_Aktywni"];



        // push single product into final response array
        array_push($response["students"], $student);
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
