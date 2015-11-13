var exchangeApp = angular.module('exchangeApp', []);

exchangeApp.controller('currencyController', function ($scope, $http) {
	
	$scope.currencies = [];
	$scope.convert = {};
	$scope.modal = $('#alertModal');

	$http.get('/currency').success(function(data) {
		$scope.currencies = data;
	});
  
  	$scope.nameCodeFilterFunction = function(element) {
	  var filter = $scope.query;
	  var regex =  new RegExp(filter, 'i');
	  return element.name.match(regex) || element.code.match(regex);
	};  

	$scope.getCurrencyValue = function(element) {
	  return element.value.toFixed(2);
	};
	
	$scope.convertCurrency = function() {
		var requiredFields = "";
		if (! $scope.convert.amount) {
			requiredFields += "'amount' "
		} 
		if (! $scope.convert.from) {
			requiredFields += "'from' "
		} 
		if (! $scope.convert.to) {
			requiredFields += "'to' "
		} 
		if (requiredFields != "") {
			$scope.modal.find('.modal-body').text(requiredFields);
			$scope.modal.modal('show');
		} else {
			  $http.get('/currency/convert?from='+$scope.convert.from + '&to=' + $scope.convert.to + '&amount=' + $scope.convert.amount).success(function(data) {
			      $scope.convert.result = data.toFixed(2);
			  });
		}
	}
	
	$scope.updateCurrencies = function() {
		$http.get('/currency/update').success(function(data) {
			$scope.currencies = data;
		});
	}
});

