/*Put me in the test folder in the app */
var test = require('unit.js');
var assert = require('assert'); 
var mysql = require('mysql');
var routes = require('../routes/routes.js'); //absolute path
var server = require('../data/server.js');

//json for the test.
var json = {"success": true,
  "data": {
    "stopID": "0170SGA01533",
    "timeStamp": 636256043590000000,
    "sourceName": "BCC/Transix",
    "stopBoards": [
      {
        "headerLabel": "STOPBOARD",
        "rows": [
          {
            "isrti": true,
            "groupID": "UWE",
            "vehicleMode": 3,
            "serverRouteToken": "U3RvcElEQW5kTGluZU5hbWUsMDE3MFNHQTAxNTMzLFVXRQ==",
            "idLabel": "UWE",
            "mainLabel": "City Centre Express",
            "timeLabel": "due",
            "timeMajorLabel": "due",
            "timeMinorLabel": ""
          }
        ],
        "groups": [
          {
            "groupID": "UWE",
            "label": "UWE"
          }
        ],
        "messages": [],
        "idLabelWidth": 6,
        "platformLabelWidth": 4,
        "timeLabelWidth": 8,
        "hideSecondary": true,
        "hidePlatform": true,
        "enableGroupFiltering": true,
        "attributionLabel": "BCC",
        "color": "#DD2222",
        "colorCompliment": "#FFFFFF",
        "colors": [
          {
            "color": "#DD2222",
            "colorCompliment": "#FFFFFF"
          }
        ],
        "interstitialPermitted": true
      }
    ],
    "enableAutoRefresh": true,
    "autoRefreshDuration": 30
  }
};

//the test function
it('timetable()', function(){
	var result = timetable(json); // have to remove the module.exports for the routes
	test.object(result);
	test.number(result.length).isGreaterThan(0);
	//console.log(result); //gets the result to the screen
});

it('test query', function(){
	var connection = mysql.createConnection({
						host     : 'mysql5.cems.uwe.ac.uk',
						user     : 'fet16016225',
						password : 'Es4y8p',
						database : 'fet16016225'
	});
	test.object(connection);
	//console.log(connection.threadId);
});
