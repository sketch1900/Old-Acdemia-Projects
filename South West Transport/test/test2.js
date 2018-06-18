var test2 = require('unit.js');
var assert = require('assert'); 
var server = require('../data/server.js');
var routes = require('../routes/routes.js'); //absolute path

it('pages', function(done){
	test2.httpAgent(server.app)
	.get('/')
	.set('Accept','text/html')
	.expect(200)
	.end(function(error, response){
		if(error) {
			done(error.message);
		}else{
			done();
		}
	});
});
