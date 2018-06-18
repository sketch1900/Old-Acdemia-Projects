<?php

//import the autoload
require_once __DIR__ . '/../vendor/autoload.php';
require_once __DIR__ . '/../config/settings.php';

//instantiate the app object with settings from the config file
$app = new \Slim\App(['settings' => $config]);

//setup the dependencies
require __DIR__ . '/../config/container.php';

//Register middleware
require __DIR__ .'/../config/middleware.php';

// Register routes
require __DIR__ . '/../config/routes.php';

//run the app
return $app;