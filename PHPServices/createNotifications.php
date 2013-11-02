<?php

$response = array();
if (isset($_POST['title']) && isset($_POST['description'])) {
    
    $title = $_POST['title'];
    $description = $_POST['description'];
    require_once __DIR__ . '/db_connect.php';
    $db = new DB_CONNECT();
    $result = mysql_query("INSERT INTO Powiadomienia(Powiadomienia_Tytul, Powiadomienia_Opis) VALUES('$title','$description')");
    if ($result) {
        $response["success"] = 1;
        $response["message"] = "Product successfully created.";
        echo json_encode($response);
    } else {
        $response["success"] = 0;
        $response["message"] = "Oops! An error occurred.";
        echo json_encode($response);
    }
} else {
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
    echo json_encode($response);
}
?>