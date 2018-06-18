<?php

/* file containing the main core settings of the app.*/

$config = [];

//slims settings
$config['displayErrorDetails'] = true;
$config['determineRouteBeforeAppMiddleware'] = true;
$config['addContentLenghtHeader'] = false;

//path settings
$config['root'] = dirname(__DIR__);
$config['temp'] = $config['root'] . '/tmp';
$config['public'] = $config['root'] . '/public';

//view settings
$config['twig'] = [
	'path' => $config['root'] . '/templates',
	'cache_enabled' => false,
	'cache_path' => $config['temp'] . '/twig-cache'
];

//database settings
$config['db']['host'] = 'localhost';
$config['db']['username'] = 'paul';
$config['db']['password'] = 'password';
$config['db']['database'] = 'mydb';
$config['db']['charset'] = 'utf8';
$config['db']['collation'] = 'utf8_unicode_ci';

return $config;