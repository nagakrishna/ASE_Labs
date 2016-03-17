describe('passwordCheck', function() {
  beforeEach(module('starter'));

  var $controller;

  beforeEach(inject(function(_$controller_){
    // The injector unwraps the underscores (_) from around the parameter names when matching
    $controller = _$controller_;
  }));

  describe('$scope.grade', function() {
    var $scope, controller;

    beforeEach(function() {
      $scope = {};
      controller = $controller('passwordCheck', { $scope: $scope });
    });

    it('Strong - if greater than 8 character', function() {
      $scope.password = 'longerthaneightchars';
      $scope.grade();
      expect($scope.strength).toEqual('strong');
    });

    it('Weak - If less than 3 character', function() {
      $scope.password = 'a';
      $scope.grade();
      expect($scope.strength).toEqual('weak');
    });
      
      it('Medium - If greater than 3 characters and less than 8 characters', function() {
      $scope.password = 'nagakr';
      $scope.grade();
      expect($scope.strength).toEqual('weak');
    });
  });
});