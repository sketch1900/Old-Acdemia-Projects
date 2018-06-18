"use strict";

$(document).ready(function(){	

  var weatherApi = "";

  //Navigation:
    /* navbar collapse */
  $('.navbar-toggle').click(function(){
    $('#myNavbar').slideToggle('5000',"linear", function () {
        // Animation complete.
    });
  });

  //when a navbar menu item is selected, take to the page or link then close the navbar
 $('.shut').click(function () {
        $('#myNavbar').delay('500').slideToggle('5000', 'linear', function(){});
    });

	//factButton
	$('#factButton').click(function(){
    $('#birminghamFact').empty();
    $('#chicagoFact').empty();

    let fact = {
      "facts": [
        {
          Birmingham:"<p>Birimingham tourism in 2017 Tourist visitor numbers 39 million</p>",
          Chicago: "<p>Chicago tourism in 2017 Tourist visitor numbers 54.1 million</p>"
        },
        {
         Birmingham:"<p>Birmingham was established as a city in 1871.</p>",
          Chicago: "<p>Chicago was established as a city in 1837.</p>"
        },
        {
         Birmingham:"<p>Birmingham has five universities and a total estimate of 73,000 students</p>",
         Chicago:"<p>Chicago has 28 universities in the city totaling over a 100,000 to 300,000 students</p>"
        },
        {
          Birmingham:"<p>Birmingham University has a student experience rank of 86.3 2017</p>",
          Chicago:"<p>Chicago University has a student experience rank of 73rd 2017</p>"
        }]
      };

    let rand = Math.ceil(Math.random() * fact.facts.length);

    if(rand == 4){
      rand = 0;
    }

     // console.log(rand + " " + fact.facts[rand].Birmingham + " " + fact.facts[rand].Chicago);

    $('#birminghamFact').append(fact.facts[rand].Birmingham);
    $('#chicagoFact').append(fact.facts[rand].Chicago);
	});
  
  /* 
  Birmingham Map and map markers 
  database ajax call function creating google map markers on success and the poi information such as pictures location name and url
 */
  $.ajax({
    url: "http://localhost/html/Frameworks/Slim/project/src/poi",
    data: {
      format: 'json'
    },
    cache: true,
    headers: {
      'Cache-Control': 'max-age=10000000' 
    },
    error: function(){
      $('#output').append('An Error has occured');
    },
      dataType: 'json',
      cache: true,
      success: function(json){
        // Creating a new map
        let map = new google.maps.Map(document.getElementById("birminghamMap"), {
          center: new google.maps.LatLng(52.489471, -1.898575),
          zoom: 12,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        });

        // Looping through the JSON data
        for (let i = 0; i < json.length; i++) {
          let data = json[i];

          if(data.city_woeID === 12723){
            let latLng = new google.maps.LatLng(data.poiLat, data.poiLong);
            let info =  "<div class='infoBox' id='" + data.name + "'><p>" + data.name + "</br>" + data.poi_description + "<br></br> Click the marker for more information.</p>";

            // Creating a marker and putting it on the map
           let marker = new google.maps.Marker({
             position: latLng,
              map: map,
              title: data.name
           });

            marker.content = info;

            //for the hover information
            let infoWindow = new google.maps.InfoWindow();
            google.maps.event.addListener(marker, 'mouseover', function () {
              infoWindow.setContent(this.content);
              infoWindow.open(this.getMap(), this);
            });
            //redirects to a new page on clicking the link.  
            google.maps.event.addListener(marker, 'click', function () {
              //to keep from showing a past entry we have to refer to this markers content, then split it to get the data.name
              let w = this.content.split("'");
              let i = w[3].split("=");
              //console.log(i[0]);
            window.location = 'http://localhost/html/Frameworks/Slim/project/src/infoPage?id='+ i[0];
            });
          }
        }

        /* Get poi urls and names for the picture slideshow*/  
        /* Taken from W3 schools and modified to be a automatic slide show that will take picture from the json recieved*/         
        for(let i = 0; i < json.length; i++){

         // console.log(json[i].poi_img_url);
          if(json[i].poi_img_url !== null && json[i].city_woeID == '12723'){
            $('.birmingham').append("<div class='mySlides1'><img src='" + json[i].poi_img_url +"' height='400px' alt='" + json[i].name + " chicago' style='width:100%'>" +
           "<div class='text'>" + json[i].name + "</div></div>");
            $('#counter1').append("<span class='dot1'></span>"); 
          }
        }

        let slideIndex = 0;
        //creates a counter for the dots to cycle through and cycle through the images to create a automatic slide show
        function showSlides1() {
          let i;
          let slides = document.getElementsByClassName("mySlides1");
          let dots = document.getElementsByClassName("dot1");

          for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";  
          }

          slideIndex++;

          if (slideIndex > slides.length) {
            slideIndex = 1
          }    

          for (i = 0; i < dots.length; i++) {
            dots[i].className = dots[i].className.replace(" active1", "");
          }

          slides[slideIndex-1].style.display = "block";  
          dots[slideIndex-1].className += " active1";
          setTimeout(showSlides1, 5000); // Change image every 5 seconds
        }

        showSlides1();
        },
    type: 'GET'
  });

  /* 
    Chicago Map and markers 
    database ajax call function creating google map markers on success
  */
  $.ajax({
    url: "http://localhost/html/Frameworks/Slim/project/src/poi",
    data: {
      format: 'json',
    },
    cache: true,
    headers: {
     'Cache-Control': 'max-age=1000' 
    },
    error: function(){
      $('#output').append('An Error has occured');
    },
    dataType: 'json',
    success: function(json){
       
      // Creating a new map
      let map = new google.maps.Map(document.getElementById("chicagoMap"), {
        center: new google.maps.LatLng(41.881832, -87.623177),
        zoom: 13,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      });

      // Looping through the JSON data
      for (let i = 0, length = json.length; i < length; i++) {
        // Creating a global infoWindow object that will be reused by all markers
        let data = json[i];

        let info = "<div class='infoBox' id='" + data.name + "'><p>" + data.name + "</br>" + data.poi_description + "<br></br> Click the marker for more information.</p>";
        
        if(data.city_woeID === 2379574){

        let latLng = new google.maps.LatLng(data.poiLat, data.poiLong);
        //console.log(data.lat + " " + data.long + "  " + data.name);

         // Creating a marker and putting it on the map
           let marker = new google.maps.Marker({
             position: latLng,
              map: map,
              title: data.name
           });

            marker.content = info;

             //for the hover information
            let infoWindow = new google.maps.InfoWindow();
            google.maps.event.addListener(marker, 'mouseover', function () {
              infoWindow.setContent(this.content);
              infoWindow.open(this.getMap(), this);
            });
            //redirects to a new page on clicking the link.  
            google.maps.event.addListener(marker, 'click', function () {
              //to keep from showing a past entry we have to refer to this markers content, then split it to get the data.name
              let w = this.content.split("'");
              let i = w[3].split("=");
              //console.log(i[0]);
              window.location = 'http://localhost/html/Frameworks/Slim/project/src/infoPage?id='+ i[0];
            });
      }
    }

    /* Get poi urls and names for the picture slideshow*/  
    /* Taken from W3 schools and modified to be a automatic slide show that will take picture from the json recieved*/    
    for(let i = 0; i < json.length; i++){

      //console.log(json[i].poi_img_url);
      if(json[i].poi_img_url !== null && json[i].city_woeID == '2379574'){
        $('.chicago').append("<div class='mySlides2'><img src='" + json[i].poi_img_url +"' height='400px' alt='" + json[i].name + " chicago' style='width:100%'>" +
        "<div class='text'>" + json[i].name + "</div></div>");
        $('#counter2').append("<span class='dot2'></span>"); 
      }
    }

    let slideIndex = 0;
    //creates a counter for the dots to cycle through and cycle through the images to create a automatic slide show
    function showSlides2() {
      let i;
      let slides = document.getElementsByClassName("mySlides2");
      let dots = document.getElementsByClassName("dot2");

      for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";  
      }

      slideIndex++;

      if (slideIndex > slides.length) {
        slideIndex = 1
      }    

      for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active2", "");
      }

      slides[slideIndex-1].style.display = "block";  
      dots[slideIndex-1].className += " active2";
      setTimeout(showSlides2, 5000); // Change image every 2 seconds
    }
      showSlides2();
    },
    type: 'GET'
  });

  /* 
  Call weather based on city *birmingham  
 */
function callApi(weatherApi){
  $.ajax({
    //                                           replace for 5 day forcast: forcast?
    url: "http://api.openweathermap.org/data/2.5/weather?lat=52.489471&lon=-1.898575&APPID=" + weatherApi,
     dataType: 'json',
     cache: false,
    error: function(){
      $('#output').append('An Error has occured');
    },
     
      success: function(data){
        //console.log(data.weather[0].main + " " + data.weather[0].description + "\n" + data.main.humidity + " " + data.main.temp + " K " + data.main.pressure + " B"); 
        let temp = Math.ceil(data.main.temp - 273.15);

        $('#birWeather').append("<li><img name='rain' class='icon' alt='weather picture' src='http://openweathermap.org/img/w/" + data.weather[0].icon + ".png'> <//li>");
        $('#birWeather').append("<li>" + data.weather[0].description + "<//li>");
        $('#birWeather').append("<li>" + temp + "<sup>o</sup>C " + data.main.pressure + " bar" + "<//li>");
    },
    type: 'GET'
  });

   /* 
  Call weather based on city *BirmingHam calls the next days weather
 */
  $.ajax({
    url: "http://api.openweathermap.org/data/2.5/forecast?lat=52.489471&lon=-1.898575&APPID=" + weatherApi,
     dataType: 'json',
     cache: false,
    error: function(){
      $('#output').append('An Error has occured');
    },
      success: function(data){
       let json = data.list;
       let i = 0;

       //console.log(data);

        //loop through forcasts taking the first of the 3 day forecast
        // since it will find the first in the list then it takes the next days weather forecast
        for(let i = 0; i < json.length; i++){
        	let time = json[i].dt_txt.split(" ");
        	
        	if(time[1] == "06:00:00"){
        		let temp = Math.ceil(json[0].main.temp - 273.15);

		        $('#futureBirWeather').append("<div><img name='rain' class='icon' alt='weather picture' src='http://openweathermap.org/img/w/" + json[0].weather[0].icon + ".png'>"
		        + "<p>" + json[0].weather[0].description + "<//p>"
		       	+ "<p>" + temp + "<sup>o</sup>C " + json[0].main.pressure + " bar" + "<//p>");
		       	break;
        	}
        }
    },
    type: 'GET'
  });
}

function callApi2(weatherApi){
  //current forecast for the city of chicago.
   $.ajax({
    url: "http://api.openweathermap.org/data/2.5/weather?lat=41.881832&lon=-87.623177&APPID=" + weatherApi,
     dataType: 'json',
     cache: false,
    error: function(){
      $('#output').append('An Error has occured');
    },
     
      success: function(data){
        //console.log(data.weather[0].main + " " + data.weather[0].description + "\n" + data.main.humidity + " " + data.main.temp + " K " + data.main.pressure + " B"); 
        let temp = Math.ceil(data.main.temp - 273.15);

        $('#chicWeather').append("<li><img name='rain' class='icon' alt='weather picture' src='http://openweathermap.org/img/w/" + data.weather[0].icon + ".png'> <//li>");
        $('#chicWeather').append("<li>" + data.weather[0].description + "<//li>");
        $('#chicWeather').append("<li>" + temp + "<sup>o</sup>C " + data.main.pressure + " bar" + "<//li>");
    },
    type: 'GET'
  });

    /* 
  Call weather based on city *chicago calls the next days weather
 */
  $.ajax({
    url: "http://api.openweathermap.org/data/2.5/forecast?lat=41.881832&lon=-87.623177&APPID=" + weatherApi,
     dataType: 'json',
     cache: false,
    error: function(){
      $('#output').append('An Error has occured');
    },
      success: function(data){
       let json = data.list;
       let i = 0;

       //console.log(data);

        //loop through forcasts taking the first of the 3 day forecast
        // since it will find the first in the list then it takes the next days weather forecast
        for(let i = 0; i < json.length; i++){
        	let time = json[i].dt_txt.split(" ");
        	
        	if(time[1] == "06:00:00"){
        		let temp = Math.ceil(json[0].main.temp - 273.15);

		        $('#futureChicWeather').append("<div><img name='rain' class='icon' alt='weather picture' src='http://openweathermap.org/img/w/" + json[0].weather[0].icon + ".png'>"
		        + "<p>" + json[0].weather[0].description + "<//p>"
		       	+ "<p>" + temp + "<sup>o</sup>C " + json[0].main.pressure + " bar" + "<//p>");
		       	break;
        	}
        }
    },
    type: 'GET'
  });
}

/* Weather api clocks */
// Birmingham Clock
  function clock1(){
    let canvas = document.getElementById("canvas");
    let ctx = canvas.getContext("2d");
    let radius = canvas.height / 2;
    ctx.translate(radius, radius);
    radius = radius * 0.90
    setInterval(drawClock, 1000);

    function drawClock() {
      drawFace(ctx, radius);
      drawNumbers(ctx, radius);
      drawTime(ctx, radius);
    }

    function drawFace(ctx, radius) {
      let grad;
      ctx.beginPath();
      ctx.arc(0, 0, radius, 0, 2*Math.PI);
      ctx.fillStyle = 'white';
      ctx.fill();
      grad = ctx.createRadialGradient(0,0,radius*0.95, 0,0,radius*1.05);
      grad.addColorStop(0, '#333');
      grad.addColorStop(0.5, 'white');
      grad.addColorStop(1, '#333');
      ctx.strokeStyle = grad;
      ctx.lineWidth = radius*0.1;
      ctx.stroke();
      ctx.beginPath();
      ctx.arc(0, 0, radius*0.1, 0, 2*Math.PI);
      ctx.fillStyle = '#333';
      ctx.fill();
    }

    function drawNumbers(ctx, radius) {
      let ang;
      let num;
      ctx.font = radius*0.15 + "px arial";
      ctx.textBaseline="middle";
      ctx.textAlign="center";
      for(num = 1; num < 13; num++){
        ang = num * Math.PI / 6;
        ctx.rotate(ang);
        ctx.translate(0, -radius*0.85);
        ctx.rotate(-ang);
        ctx.fillText(num.toString(), 0, 0);
        ctx.rotate(ang);
        ctx.translate(0, radius*0.85);
        ctx.rotate(-ang);
      }
    }

    function drawTime(ctx, radius){
        let now = new Date();
        let hour = now.getHours();
        let minute = now.getMinutes();
        let second = now.getSeconds();
        //hour
        hour=hour%12;
        hour=(hour*Math.PI/6)+
        (minute*Math.PI/(6*60))+
        (second*Math.PI/(360*60));
        drawHand(ctx, hour, radius*0.5, radius*0.07);
        //minute
        minute=(minute*Math.PI/30)+(second*Math.PI/(30*60));
        drawHand(ctx, minute, radius*0.8, radius*0.07);
        // second
        second=(second*Math.PI/30);
        drawHand(ctx, second, radius*0.9, radius*0.02);
    }

    function drawHand(ctx, pos, length, width) {
        ctx.beginPath();
        ctx.lineWidth = width;
        ctx.lineCap = "round";
        ctx.moveTo(0,0);
        ctx.rotate(pos);
        ctx.lineTo(0, -length);
        ctx.stroke();
        ctx.rotate(-pos);
    }
  }

// chicago Clock
    function clock2(){
    let canvas = document.getElementById("canvas2");
    let ctx = canvas.getContext("2d");
    let radius = canvas.height / 2;
    ctx.translate(radius, radius);
    radius = radius * 0.90
    setInterval(drawClock, 1000);

    function drawClock() {
      drawFace(ctx, radius);
      drawNumbers(ctx, radius);
      drawTime(ctx, radius);
    }

    function drawFace(ctx, radius) {
      let grad;
      ctx.beginPath();
      ctx.arc(0, 0, radius, 0, 2*Math.PI);
      ctx.fillStyle = 'white';
      ctx.fill();
      grad = ctx.createRadialGradient(0,0,radius*0.95, 0,0,radius*1.05);
      grad.addColorStop(0, '#333');
      grad.addColorStop(0.5, 'white');
      grad.addColorStop(1, '#333');
      ctx.strokeStyle = grad;
      ctx.lineWidth = radius*0.1;
      ctx.stroke();
      ctx.beginPath();
      ctx.arc(0, 0, radius*0.1, 0, 2*Math.PI);
      ctx.fillStyle = '#333';
      ctx.fill();
    }

    function drawNumbers(ctx, radius) {
      let ang;
      let num;
      ctx.font = radius*0.15 + "px arial";
      ctx.textBaseline="middle";
      ctx.textAlign="center";
      for(num = 1; num < 13; num++){
        ang = num * Math.PI / 6;
        ctx.rotate(ang);
        ctx.translate(0, -radius*0.85);
        ctx.rotate(-ang);
        ctx.fillText(num.toString(), 0, 0);
        ctx.rotate(ang);
        ctx.translate(0, radius*0.85);
        ctx.rotate(-ang);
      }
    }

    function drawTime(ctx, radius){
        let now = new Date();
        let hour = now.getHours();
        let minute = now.getMinutes();
        let second = now.getSeconds();

        //-6 to make it match the utc time for chicago
        hour=(hour%12)-6;
        hour=(hour*Math.PI/6)+
        (minute*Math.PI/(6*60))+
        (second*Math.PI/(360*60));
        
        drawHand(ctx, hour, radius*0.5, radius*0.07);
        //minute
        minute=(minute*Math.PI/30)+(second*Math.PI/(30*60));
        drawHand(ctx, minute, radius*0.8, radius*0.07);
        // second
        second=(second*Math.PI/30);
        drawHand(ctx, second, radius*0.9, radius*0.02);
    }

    function drawHand(ctx, pos, length, width) {
        ctx.beginPath();
        ctx.lineWidth = width;
        ctx.lineCap = "round";
        ctx.moveTo(0,0);
        ctx.rotate(pos);
        ctx.lineTo(0, -length);
        ctx.stroke();
        ctx.rotate(-pos);
    }
  }

  /* SHould load the xml from there tags */
  function loadDoc(fileName, tagName) {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        return myFunction(this, tagName);
      }
    };
    xhttp.open("GET", fileName, true);
    xhttp.send();
  }
  //call the xml api the use the result to call the weather api
  function myFunction(xml, tagName) {
    let xmlDoc = xml.responseXML;
    let x = xmlDoc.getElementsByTagName(tagName);
    
    weatherApi = x[0].childNodes[0].data;
    //console.log(weatherApi);
    callApi(weatherApi);
    callApi2(weatherApi);
  }

  clock1();
  clock2();
  loadDoc('config.xml','appid');
});