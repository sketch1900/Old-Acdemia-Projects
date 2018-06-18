<?php

/* twig is the extension that allows slim to display templates or html files.*/

use Slim\Container;

$container = $app->getContainer();

/* Routing */
//Activate the routes
$container['environment'] = function (){
	$scriptName = $_SERVER['SCRIPT_NAME'];
	$_SERVER['SCRIPT_NAME'] = dirname(dirname($scriptName)) . '/' . basename($scriptName);
	return new Slim\Http\Environment($_SERVER);
};

/* HTTP caching */
$container['cache'] = function () {
    return new \Slim\HttpCache\CacheProvider();
};

/* Template and html file views */
//activate the twig view helper container
$container['view'] = function (Container $container){
	$settings = $container->get('settings');
	$viewPath = $settings['twig']['path'];

	$twig = new \Slim\Views\Twig($viewPath, [
		'cache' => $settings['twig']['cache_enabled'] ? $settings['twig']['cache_path'] : false
	]);

	/*@var Twig_Loader_Filesystem $Loader */
	$loader = $twig->getLoader();
	$loader->addPath($settings['public'], 'public');

	//instantiate and add the slim specific extension
	$basePath = rtrim(str_ireplace('index.php', '', $container->get('request')->getUri()->getBasePath()), '/');
	$twig->addExtension(new Slim\Views\TwigExtension($container->get('router'), $basePath));

	return $twig;
};

/* database Connection PDO pulling in the connections from the settings file. */
$container['db'] = function (Container $container){
	$settings = $container->get('settings');
	$host = $settings['db']['host'];
	$dbname = $settings['db']['database'];
	$username = $settings['db']['username'];
	$password = $settings['db']['password'];
	$charset = $settings['db']['charset'];
	$collate = $settings['db']['collation'];
	$dsn = "mysql:host=$host;dbname=$dbname;charset=$charset";
	$options = [
		PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
		PDO::ATTR_PERSISTENT => false,
		PDO::ATTR_EMULATE_PREPARES => false,
		PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
		PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES $charset COLLATE $collate"
	];

	return new PDO($dsn, $username, $password, $options);
};