<?php

class Services {

	private $dbnUrl = "mysql:host=mysql3.hekko.net.pl;dbname=loks_dyplom";
	private $dbnUser = "loks_dyplom";
	private $dbnPass = "pracaPGdyplom1";

	/** @var PDO */
	private $dbConnector;

	public function getDbData($table) {
		$this->prepareConnector();

		$queryResult = $this->dbConnector->query('SELECT * FROM '.$table);
	
		$fetchAll = $queryResult->fetchAll(PDO::FETCH_ASSOC);
		
		exit(json_encode($fetchAll));
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