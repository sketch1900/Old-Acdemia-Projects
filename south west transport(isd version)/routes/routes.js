var express = require('express');
var path = require('path');
var https = require('https');

//var app = express(); //required for mocha testing

// rate limiter to avoid "API calls quota exceeded! maximum admitted 5 per Second."
var RateLimiter = require('limiter').RateLimiter;
var limiter = new RateLimiter(2, 'second');

var stopName, vehicleId, direction, primary = "", times = [];

//same as having router.get and then having module.exports = router() at the end of the file
//router() is also part of express
module.exports = function(app){
				
function invoke(path) {
  return new Promise(function(resolve, reject) {
    options = {
      host: "bristol.api.urbanthings.io",
      headers: { 'X-Api-Key': 'Your api Key here (sign up for one!)' },
      accept: 'application/json'
    };	
    options.path = path;
    var callback = function(response) { // streamed response
      var body = '';
      response.on('data', function(d) { body += d; });
      response.on('end', function() {
        var json = JSON.parse(body);
        resolve(json);
     });
    };
    try { 
	limiter.removeTokens(1, function(err, remainingRequests){
	https.get(options,callback); 
	});
	}
    catch (error) { reject(error.message); }
  });
}


//gets timetable from the json received
function timetable(json) {
  var a = [];
  var b = "";
  //dismantles the json from the api call
  for (var i=0; i<json.data.stopBoards.length; i++){
	  for(var i =0; i< json.data.stopBoards[0].rows.length; i++){
			b = json.data.stopBoards[0].rows[i];
			a[i] =  b.idLabel + "     Due in: " + b.timeLabel;
	  }
}
  return a;
}


//sends first page
app.get('/', function(req, res,next) {
	res.sendFile(path.join(__dirname, '../views/page1.html'));
	
	//retrieves the data from the locals
	var dbId = req.app.locals.stopCode;
	var dbStop = req.app.locals.stopName;
	
	//retrieves the query String and 
	if("stopId" in req.query){
		stopName = req.query.stopId;
		direction = req.query.myDirection;	
		stopName.toLowerCase();
		
		for(var i =0; i < dbStop.length; i++){
			var names = dbStop[i].toLowerCase();
			if(names.includes(stopName) && dbStop[i].includes(direction) > -1){	
			
			primary = dbId[i];
			}
		}
		//primary = '0100BRA16910'; //for when there is no db connection outside of uwe and testing.
	
		//timetable callbalk and vechile id.	
		invoke("https://bristol.api.urbanthings.io/api/2.0/rti/stopboard?stopID=" + primary
		+ "&vehiclePassingType=StartingHereOnly&use24clock=true")
			.then(
		function(response) {
			times = timetable(response);
		//	console.log(times);
		},			
		function(error) { console.error("ERROR:", error); });	
		res.redirect('vehicleId');
		res.end();	
	}
});

//sends to the 2nd page
app.get('/vehicleId', function(req,res){
	res.sendFile(path.join(__dirname,'../views/page2.html'));
	if("vehicleId" in req.query){
		vehicleId = req.query.vehicleId;
		res.redirect('/timetable');
		res.end();
	}
});

//gets the route for the final page (results)
app.get('/timetable', function(req,res){
	var done = [];
	var space= "";
	
	for(var i = 0; i < times.length; i++){
		var str = times[i].substring(0,5);
		//console.log(str);
		if(str.includes(vehicleId)){ 
			space = times[i];
			if(space.length != 0 || space != ""){	//attempt to remove the spaces created from the loop
				done[i] = space;
			}
		//console.log(done);
		}
	}
	//if done is empty
	if(done.length == 0){ 
		done[0] = "The bus that you have entered either does not stop here," +
		" have upcoming stops or no bus was entered.";		
	}
	//sends the variables to the client as part of the page.
	res.render('result',{
		stopName : stopName,
		direction : direction,
		done : done
	});
});
}

//mocha testing this needs to be turned on and module.exports = function(app) turned off
//exports.timetable = timetable;
