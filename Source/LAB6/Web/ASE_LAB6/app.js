var myApp = angular.module('Venues',['ngSanitize']);

myApp.controller('loginController', function($scope, $http, $sce){

    $scope.getSentiment = function(){
        var place = document.getElementById('place').value;
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