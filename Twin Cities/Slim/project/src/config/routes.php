<?php
//pulls the request and response classes into this script
use Slim\Http\Request;
use Slim\Http\Response;

//displays a html file and tests the db and displays it
$app->get('/', function(Request $req, Response $res){
	return $this->view->render($res, 'home.twig', array(
		'apiKey' => 'insert your api key for weather api here'
	));
});

//displays a html file and tests the db and displays it
$app->get('/infoPage', function(Request $req, Response $res){
	$dbhandler = $this->db;
	$stmt = $dbhandler->prepare("SELECT poi_description, poi_img_url, poi_url FROM poi where name = :name"); 
	
		$name = $req->getParam('id');
		$stmt->bindValue(':name', $name);

		try{
			$stmt->execute();
			$poi = $stmt->fetchAll();
		}catch(PDOException $e){
			echo "404 Server Error";
		}

		foreach($poi as $row){
			$description = $row['poi_description'];
			$img = $row['poi_img_url'];
			$url = $row['poi_url'];
		}

	return $this->view->render($res, 'infoPage.twig', array(
		'description' => $description,
		'img' => $img,
		'url' => $url,
		'name' => $name
	));
});

/*using 2 calls untill the database is completed and the fields are set.
returns an api call for the db's poi's this is called on the page resfresh */
$app->get('/poi', function(Request $req, Response $res){

		$dbhandler = $this->db;
		$stmt = $dbhandler->prepare("SELECT * FROM poi"); 
		
		try{
			$stmt->execute();
			$poi = $stmt->fetchAll();

		}catch(\PDOException $e){
			//do nothing
		}

	return $this->response->withJson($poi);
});

/* 
	rss feed Route built into this project and calls the db hidden behind the route it is safer then a php file doing this and is also stand a lone
	However as this calls a route it can be seen as more resource heavy
*/
$app->get('/rss', function(Request $req, Response $res){
	$dbhandler = $this->db;
	$stmt = $dbhandler->prepare("SELECT * FROM poi"); 
	
	try{
		$stmt->execute();
		$rs_post = $stmt->fetchAll();

	}catch(\PDOException $e){
		//do nothing
	}

	// The XML structure
	$data = '<?xml version="1.0" encoding="UTF-8" ?>';
	$data .= '<rss version="2.0">';
	$data .= '<channel>';
	$data .= '<title>Twin Cities RSS</title>';
	$data .= '<link>http://localhost/html/Frameworks/slim/src/rss</link>';
	$data .= '<description>This is the Twin cities RSS feed about the points of interest in the cities</description>';
	$data .= '<language>en-gb</language>';
	foreach ($rs_post as $row) {
	    $data .= '<item>';
	    $data .= '<title>'.$row['name'].'</title>';
	    $data .= '<pubDate>' . date("Y-m-d H:i:s") .'</pubDate>';
	    $data .= '<link>'. $row['poi_url'].'</link>';
	    $data .= '<description>'. $row['poi_description'].'</description>';
	    $data .= '</item>';
	}
	$data .= '</channel>';
	$data .= '</rss> ';
	 
	header('Content-Type: application/xml');
	echo $data;
});
