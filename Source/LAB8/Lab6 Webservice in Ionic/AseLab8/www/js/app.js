// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
angular.module('starter', ['ionic'])

    .run(function($ionicPlatform) {
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
    .controller('homeController', function($scope, $http){


        $scope.submit1 = function(){
            var cityValue = document.getElementById('city').value;
            
            $http.get("http://localhost:8075/ASE_LAB6/naga/nagaservice/" + cityValue + "").success(function(data) {
                venueArray = data.data1;
                l = data.data1.length;

                venue = [{}];
                score = [{}];
                review = [{}];
                type = [{}];


                for (i = 0; i < Number(l); i++) {
                    venue[i] = venueArray[i].name;
                    score[i] = venueArray[i].SentimentScore;
                    review[i] = venueArray[i].Review;
                    type[i] = venueArray[i].SentimentType;
                }
//          $scope.ven = {venue, score, review, type};
                $scope.chats = [{
                    id: venue[0],
                    name: score[0],
                    lastText: review[0],
                    face: type[0]
                }, {
                    id: venue[1],
                    name: score[1],
                    lastText: review[1],
                    face: type[1]
                }, {
                    id: venue[2],
                    name: score[2],
                    lastText: review[2],
                    face: type[2]
                }, {
                    id: venue[3],
                    name: score[3],
                    lastText: review[3],
                    face: type[3]
                }, {
                    id: venue[4],
                    name: score[4],
                    lastText: review[4],
                    face: type[4]
                }];
            })
        }
    });


