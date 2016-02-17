/**
 * Created by Naga Krishna on 14-02-2016.
 */

var ASE = angular.module('ASE', ['ngRoute', 'ngAnimate', 'ngStorage']);

ASE.config(function($routeProvider){
    $routeProvider
        .when('/',{
            templateUrl: 'login.html',
            controller: 'loginController'
        })
        .when('/home',{
            templateUrl: 'home.html',
            controller: 'homeController'
        })
        .when('/register',{
            templateUrl: 'register.html',
            controller: 'registerController'
        });

 });

ASE.controller('loginController', function($scope, $window, $localStorage){
    $scope.pageClass = 'login';
    $scope.login = function(){
        if($scope.username == $localStorage.name && $scope.password == $localStorage.password){
            $window.location.href = "#home"

        }
        else{
            templateUrl: 'register.html';
        }
    }
    $scope.register = function(){
        $window.location.href = "#register";
    }

});

ASE.controller('registerController', function($scope, $window, $localStorage){
   $scope.pageClass = 'register';

    $scope.save = function() {
        $localStorage.name = $scope.name;
        $localStorage.password = $scope.password;
    }
    $scope.register = function(){
        $window.location.href = "#/";

    }

});

//ASE.controller('storageController', function($scope, $localStorage){
//    $scope.$storage = $localStorage.$default({
//
//    })
//});
ASE.controller('homeController', function ($scope, $http, $sce) {

    var map;
    var mapOptions;
    var directionsDisplay = new google.maps.DirectionsRenderer({
        draggable: true
    });
    var directionsService = new google.maps.DirectionsService();

    $scope.initialize = function () {
        var pos = new google.maps.LatLng(0, 0);
        var mapOptions = {
            zoom: 3,
            center: pos
        };

        map = new google.maps.Map(document.getElementById('map-canvas'),
            mapOptions);
    };
    $scope.calcRoute = function () {
        var end = document.getElementById('endlocation').value;
        var start = document.getElementById('startlocation').value;

        var request = {
            origin: start,
            destination: end,
            travelMode: google.maps.TravelMode.DRIVING
        };

        directionsService.route(request, function (response, status) {
            if (status == google.maps.DirectionsStatus.OK) {
                directionsDisplay.setMap(map);
                directionsDisplay.setDirections(response);
                console.log(status);
            }

        });


    };

    google.maps.event.addDomListener(window, 'load', $scope.initialize);

    $scope.getWeatherUpdate = function(){
        var end = document.getElementById('endlocation').value;


    }

    $scope.getWeather1 = function() {
        var start = document.getElementById('startlocation').value;
        //var s = '' +
        //    '`' + start + '.json';
        //var s = 'https://api.wunderground.com/api/36b799dc821d5836/conditions/q/MO/.json';
        //;
        $http.get("http://api.openweathermap.org/data/2.5/weather?q=" + start + "&appid=44db6a862fba0b067b1930da0d769e98").success(function(data) {
            console.log(data);
            temp = Math.round(data.main.temp);

            //icon = data.current_observation.icon_url;

            weatherArray = data.weather;
            weather1 = weatherArray[0].description;
            icon1 = weatherArray[0].icon;
            city1 = data.name;
            console.log(temp);
             $scope.currentIcon1 = "<img src='http://openweathermap.org/img/w/" + icon1 + ".png'/>";
            $scope.currentweather1 = "Temperature in " + city1 + " is " + (Math.round((temp - 273)*1.8 + 32)) + " &deg; F and " + weather1 + "";




        })
    }

    $scope.getWeather2 = function() {
        var start = document.getElementById('endlocation').value;
        //var s = '' +
        //    '`' + start + '.json';
        //var s = 'https://api.wunderground.com/api/36b799dc821d5836/conditions/q/MO/.json';
        //;
        $http.get("http://api.openweathermap.org/data/2.5/weather?q=" + start + "&appid=44db6a862fba0b067b1930da0d769e98").success(function(data) {
            console.log(data);
            temp = Math.round(data.main.temp);

            //icon = data.current_observation.icon_url;

            weatherArray = data.weather;
            weather1 = weatherArray[0].description;
            icon1 = weatherArray[0].icon;
            city1 = data.name;
            console.log(temp);
            $scope.currentIcon2 = "<img src='http://openweathermap.org/img/w/" + icon1 + ".png'/>";
            $scope.currentweather2 = "Temperature in " + city1 + " is " + (Math.round((temp - 273)*1.8 + 32)) + " &deg; F and " + weather1 + "";




        })
    }




    $scope.renderHtml = function (htmlCode) {
        return $sce.trustAsHtml(htmlCode);
    };

    $scope.formatNumber = function(i) {
        return Math.round(i * 100)/100;
    }
});


