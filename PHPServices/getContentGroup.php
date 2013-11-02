<?php

$response = array ();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$groupNumber = $_POST['groupNumber'];
$query_search = "SELECT t2.Klienci_ID, t2.Klienci_Imie, t2.Klienci_Nazwisko, t2.Klienci_Pesel, t2.Klienci_Plec, t2.Klienci_Telefon, t2.Klienci_Mail, t2.Klienci_Ulica, t2.Klienci_Miasto, t2.Klienci_Numer_domu, t2.Klienci_Kod, t2.Klienci_Aktywni FROM GrupyNalezenie AS t1, Klienci AS t2 WHERE t1.Klienci_ID = t2.Klienci_ID AND t1.Grupy_ID = '".$groupNumber."'";
$result = mysql_query($query_search) or die(mysql_error());

if (mysql_num_rows($result) > 0) {
    
    $response["clients"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $client = array();
        $client["pid"] = $row["Klienci_ID"];
        $client["name"] = $row["Klienci_Imie"];
        $client["surname"] = $row["Klienci_Nazwisko"];
        $client["pesel"] = $row["Klienci_Pesel"];
        $client["sex"] = $row["Klienci_Plec"];
		$client["phone"] = $row["Klienci_Telefon"];
		$client["mail"] = $row["Klienci_Mail"];
		$client["street"] = $row["Klienci_Ulica"];
		$client["city"] = $row["Klienci_Miasto"];
		$client["housenumber"] = $row["Klienci_Numer_domu"];
		$client["code"] = $row["Klienci_Kod"];
		$client["active"] = $row["Klienci_Aktywni"];
        // push single product into final response array
        array_push($response["clients"], $client);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No clients found";

    // echo no users JSON
    echo json_encode($response);
}
?>
