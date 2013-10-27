<?php

class Services {

	private $dbnUrl = "mysql:host=mysql3.hekko.net.pl;dbname=loks_dyplom";
	private $dbnUser = "loks_dyplom";
	private $dbnPass = "pracaPGdyplom1";

	/** @var PDO */
	private $dbConnector;

	public function getDbData($table, $where = null) {
		$this->prepareConnector();

		if($where){
			$query = 'SELECT * FROM ' . $table . ' WHERE ' . $where;
		}else{
			$query = 'SELECT * FROM ' . $table;
		}
		
		
		$queryResult = $this->dbConnector->query($query);
	
		$fetchAll = $queryResult->fetchAll(PDO::FETCH_ASSOC);
		
		$result = array(
			strtolower($table) => $fetchAll
		);
		
		exit(json_encode($result));
	}

	private function prepareConnector() {
		try {
			$this->dbConnector = new PDO($this->dbnUrl, $this->dbnUser, $this->dbnPass);
			$this->dbConnector->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		} catch (PDOException $e) {
			echo $e->getMessage();
		}
	}

}

?>