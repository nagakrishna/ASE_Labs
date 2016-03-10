var myApp = angular.module('Venues',['ngSanitize']);

myApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
}
]);
myApp.controller('loginController', function($scope, $http, $sce, $window){

    $http.get("http://localhost:8075/ASE_LAB6/MongoService/user").success(function(data5){
        venueArray1 = data5;
        l = data5.length;
        $scope.operators = data5;
        venue1 = [{}];

        $scope.selectedCity = angular.copy($scope.operators[0]);
        for(i=0;i<Number(l);i++){
            venue1[i] = venueArray1[i].city;
        }
        $scope.selected = venue1[0];




    });

    $scope.update = function(){
        var cityValue = document.getElementById('selectedCity').value;
        var city1 = venue1[cityValue];
        var newCity = document.getElementById('updateValue').value;;
        $http.get("http://localhost:8075/ASE_LAB6/MongoUpdate/MongoUpdate/?city1=" + city1 + "&city2=" + newCity).success(function(data){

            $window.alert("" + city1 + " is now updated as " + newCity);
        });
    }

    $scope.delete = function(){
        var cityValue = document.getElementById('selectedCity').value;
        var city = venue1[cityValue];
        //var deleteCity = document.getElementById('deleteValue').value;
        $http.get("http://localhost:8075/ASE_LAB6/MongoDelete/MongoDelete/?city=" + city).success(function(data){
            $window.alert("" + city + " is Deleted");
        });
    }


    $scope.createCity = function(data){
        var cityCreate = document.getElementById('addCity').value;
        var url = "http://localhost:8075/ASE_LAB6/MongoService/user";
        var res = $http.post(url, data);
        $window.alert("" + cityCreate + " is Added");
    }

    $scope.getSentiment = function(){
        //var place = document.getElementById('place').value;
        var cityValue = document.getElementById('selectedCity').value;
        var place = venue1[cityValue];
        $scope.selected = place;
        $http.get("http://localhost:8075/ASE_LAB6/naga/nagaservice/" + place + "").success(function(data){
            venueArray = data.data1;
            l = data.data1.length;

            venue = [{}];
            score = [{}];
            review = [{}];
            type = [{}];
            for(i=0;i<Number(l);i++){
                venue[i] = venueArray[i].name;
                score[i] = venueArray[i].SentimentScore;
                review[i] = venueArray[i].Review;
                type[i] = venueArray[i].SentimentType;
            }

            $scope.ReviewWithSentiment1 = {"venue" : venue[0],
                "review":review[0],
                "score":score[0],
                "type":type[0]};
            document.getElementById('div_ReviewList1').style.display = 'block';

            $scope.ReviewWithSentiment2 = {"venue" : venue[1],
                "review":review[1],
                "score":score[1],
                "type":type[1]};
            document.getElementById('div_ReviewList2').style.display = 'block';

            $scope.ReviewWithSentiment3 = {"venue" : venue[2],
                "review":review[2],
                "score":score[2],
                "type":type[2]};
            document.getElementById('div_ReviewList3').style.display = 'block';

            $scope.ReviewWithSentiment4 = {"venue" : venue[3],
                "review":review[3],
                "score":score[3],
                "type":type[3]};
            document.getElementById('div_ReviewList4').style.display = 'block';

            $scope.ReviewWithSentiment5 = {"venue" : venue[4],
                "review":review[4],
                "score":score[4],
                "type":type[4]};
            document.getElementById('div_ReviewList5').style.display = 'block';
        });

    }

    $scope.renderHtml = function (htmlCode) {
        return $sce.trustAsHtml(htmlCode);
    };
});

//myApp.controller('homeController', function($scope, $http, $window){
//
//    $http.get("http://localhost:8075/ASE_LAB6/MongoService/user").success(function(data){
//        venueArray = data;
//        l = data.length;
//        $scope.operators = data;
//        venue = [{}];
//
//        for(i=0;i<Number(l);i++){
//            venue[i] = venueArray[i].city;
//        }
//
//
//
//    });
//
//    $scope.update = function(){
//        var cityValue = document.getElementById('selectedCity').value;
//        var city = venue[cityValue];
//        var newCity = document.getElementById('updateValue').value;;
//        $http.get("http://localhost:8075/ASE_LAB6/MongoUpdate/MongoUpdate/?city1=" + city + "&city2=" + newCity).success(function(data){
//
//            $window.alert("" + city + " is now updated as " + newCity);
//        });
//    }
//
//    $scope.delete = function(){
//        var cityValue = document.getElementById('selectedCity').value;
//        var city = venue[cityValue];
//        //var deleteCity = document.getElementById('deleteValue').value;
//        $http.get("http://localhost:8075/ASE_LAB6/MongoUpdate/MongoDelete/?city=" + city).success(function(data){
//            $window.alert("" + city + " is Deleted");
//        });
//    }
//
//
//    $scope.createCity = function(data){
//        var cityCreate = document.getElementById('addCity').value;
//        var url = "http://localhost:8075/ASE_LAB6/MongoService/user";
//        var res = $http.post(url, data);
//        $window.alert("" + cityCreate + " is Added");
//    }
//
//
//
//});