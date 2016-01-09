function dateDistanceLong(d1, d2) {
    return Math.abs(d1.getTime() - d2.getTime()) > 1000 * 60 * 60 * 24;
}

appCtrllers
    .controller('accountsCtrl', ['$scope', '$state', 'FinApp', '$rootScope',
        function ($scope, $state, FinApp, $rootScope) {
            $scope.groupId = $state.params.groupId;
            $scope.doRefresh = function () {
                FinApp.getAccountsReport($scope.groupId).then(function (res) {
                    $scope.accounts = res.data;
                    $scope.sumAll = 0;
                    if (!$scope.accounts) {
                        $scope.accounts = [];
                    }
                    var now = new Date();
                    $scope.accounts.forEach(function (t) {
                        t.lastValidated = new Date(t.lastValidated);
                        t.notUpdated = dateDistanceLong(t.lastValidated, now);
                        $scope.sumAll += t.balance;
                    })

                })
            };
            $scope.doRefresh();

        }]);
