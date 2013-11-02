<?php

$response = array ();
require_once __DIR__ . '/db_connect.php';
$db = new DB_CONNECT();
$leader = $_POST['leader'];

$query_search = "SELECT t1.Grupy_ID, t1.Kursy_ID, t1.Grupy_Nazwa, t1.Grupy_Aktywny, t1.Grupy_Stopien FROM Grupy AS t1, TrenerGrupy AS t2, Konta AS t3 WHERE t2.Konta_ID = t3.Konta_ID AND t2.Grupy_ID = t1.Grupy_ID AND t3.Konta_Uzytkownik = '".$leader."'";
$result = mysql_query($query_search) or die(mysql_error());

if (mysql_num_rows($result) > 0) {
    
    $response["groups"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $group = array();
        $group["pidgroup"] = $row["Grupy_ID"];
        $group["pidcourse"] = $row["Kursy_ID"];
        $group["groupname"] = $row["Grupy_Nazwa"];
        $group["groupactive"] = $row["Grupy_Aktywny"];
        $group["grouplevel"] = $row["Grupy_Stopien"];

        // push single product into final response array
        array_push($response["groups"], $group);
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
