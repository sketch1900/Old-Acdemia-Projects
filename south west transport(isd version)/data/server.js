var express = require("express");
var mysql = require("mysql");
var path = require("path");
var app = express();

var dbStop = [];
var dbId = [];
var QUERY = "SELECT * FROM stop";
/*
//connect to the db
var connection = mysql.createConnection({
						host     : 'mysql5.cems.uwe.ac.uk',
						user     : 'fet16016225',
						password : 'Es4y8p',
						database : 'fet16016225'
					});
					connection.connect(function(err){
						if(err) {
							console.error("Connection error: ", err.message);    
						} else {
							console.log("Connected as: ", connection.threadId);    
						}
					});
connection.query(QUERY, function(err, rows) {
	if (err) throw err;
	for (var i=0; i<rows.length; i++) {
	//	console.log(rows[i].stop_name + rows[i].stop_id);
		dbStop[i] = rows[i].stop_name;
		dbId[i] =  rows[i].stop_id;
		}
});
*/
		
//sets the view engine
app.set("views", path.join(__dirname, "../views"));
app.set("view engine","ejs");
//sets port to 3000 can be changed.
app.set("port", process.env.PORT || 3000);

//sets the db data to locals for the app that are persistant.
app.locals.stopName = dbStop;
app.locals.stopCode = dbId;

//the routing for the web app
require("../routes/routes.js")(app);

//server will listen on a port (3000)
app.listen(3000, function(){
	console.log("Listening on port 3000");	// will display in the cmd line on start up
});

//mocha testing
exports.app = app;
//exports.connection = connection;