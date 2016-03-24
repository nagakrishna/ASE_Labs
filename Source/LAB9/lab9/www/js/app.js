// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
var app = angular.module('starter', ['ionic', 'ngCordova', 'ngStorage'])

app.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    if(window.cordova && window.cordova.plugins.Keyboard) {
      // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
      // for form inputs)
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);

      // Don't remove this line unless you know what you are doing. It stops the viewport
      // from snapping when text inputs are focused. Ionic handles this internally for
      // a much nicer keyboard experience.
      cordova.plugins.Keyboard.disableScroll(true);
    }
    if(window.StatusBar) {
      StatusBar.styleDefault();
    }
  });
})
app.config(function($stateProvider, $urlRouterProvider) {

  // Ionic uses AngularUI Router which uses the concept of states
  // Learn more here: https://github.com/angular-ui/ui-router
  // Set up the various states which the app can be in.
  // Each state's controller can be found in controllers.js
  $stateProvider

  // setup an abstract state for the tabs directive
  .state('login', {
      url: '/login',
      templateUrl: 'templates/login.html',
      controller: 'LoginController'
      
  })
  .state('home',{
      url: '/home',
      templateUrl: 'templates/home.html',
      controller: 'HomeController'
  })
  // Each tab has its own nav history stack:

  .state('Register',{
      url: '/register',
      templateUrl: 'templates/register.html',
      controller: 'registerController'
  })
  
  .state('maps',{
      url: '/maps',
      templateUrl: 'templates/maps.html',
      controller: 'mapsController'
  });

  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/login');

});

app.controller('LoginController', function($scope, $state, $localStorage, $cordovaToast) {
   $scope.data = {};
 
    $scope.login = function() {
        //  if($scope.data.username == "naga" && $scope.data.password=="password"){
            if($scope.data.username == $localStorage.username && $scope.data.password==$localStorage.password){
            $state.go('home')  
         }
         else{
                $cordovaToast.show('Please check your username and password', 'long', 'bottom').then(function(success) {
            console.log("The toast was shown");
        }, function (error) {
            console.log("The toast was not shown due to " + error);
        });
         }
       
    }
    
    $scope.Register = function(){
        $state.go('Register')
    }
});

app.controller('registerController', function($scope, $state, $localStorage, $cordovaToast){
   $scope.pageClass = 'register';

    $scope.register = function() {
        $localStorage.username = $scope.username;
        $localStorage.password = $scope.password;
        $localStorage.email = $scope.email;
        $state.go('login');
               $cordovaToast.show('User successfully registered', 'long', 'bottom').then(function(success) {
            console.log("The toast was shown");
        }, function (error) {
            console.log("The toast was not shown due to " + error);
        });
    }
   

});

app.controller('HomeController', function($scope, $state, $cordovaBarcodeScanner, $cordovaGeolocation, $ionicLoading, $ionicPlatform){
    
    
    $scope.mapsButton = function(){
        $state.go('maps')        
    };
    $scope.scanBarcode = function() {
        $cordovaBarcodeScanner.scan().then(function(imageData) {
            alert(imageData.text);
            console.log("Barcode Format -> " + imageData.format);
            console.log("Cancelled -> " + imageData.cancelled);
        }, function(error) {
            console.log("An error happened -> " + error);
        });
    };
});



app.controller('mapsController', function($scope, $cordovaGeolocation, $ionicLoading, $ionicPlatform){
    
        $ionicPlatform.ready(function() {    
 
        $ionicLoading.show({
            template: '<ion-spinner icon="bubbles"></ion-spinner><br/>Getting location!'
        });
         
        var posOptions = {
            enableHighAccuracy: true,
            timeout: 20000,
            maximumAge: 0
        };
 
        $cordovaGeolocation.getCurrentPosition(posOptions).then(function (position) {
            var lat  = position.coords.latitude;
            var long = position.coords.longitude;
             
            var myLatlng = new google.maps.LatLng(lat, long);
             
            var mapOptions = {
                center: myLatlng,
                zoom: 16,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };          
             
            var map = new google.maps.Map(document.getElementById("map"), mapOptions);
            var myLatlng = new google.maps.LatLng(lat, long);
            var geocoder = new google.maps.Geocoder;
            
            var infowindow = new google.maps.InfoWindow;
            
            var marker = new google.maps.Marker({
            position: myLatlng,
            map: map,
            // title: 'Uluru (Ayers Rock)'
            });

  geocoder.geocode({ 'location': myLatlng }, function (results, status) {
                if (status === google.maps.GeocoderStatus.OK) {
                    if (results[0]) {
                        map.setZoom(11);
                        var marker = new google.maps.Marker({
                        position: myLatlng,
                        map: map
                        });
                    infowindow.setContent(results[0].formatted_address);
                    infowindow.open(map, marker);
                    } else {
                        window.alert('No results found');
                    }
                } else {
                 window.alert('Geocoder failed due to: ' + status);
                }
            google.maps.event.addListener(marker, 'click', function() {
            infowindow.open(map,marker);
            });
             
            $scope.map = map;   
            $ionicLoading.hide();           
             
        }, function(err) {
            $ionicLoading.hide();
            console.log(err);
        });
            
            google.maps.event.addListener(marker, 'click', function() {
            infowindow.open(map,marker);
            });
             
            $scope.map = map;   
            $ionicLoading.hide();           
             
        }, function(err) {
            $ionicLoading.hide();
            console.log(err);
        });
    });    
    
});


