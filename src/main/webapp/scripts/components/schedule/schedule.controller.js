app.controller('ScheduleController', function ($scope) {
    $scope.courses = [
        {name: 'CSE 420'},
        {name: 'CSE 471'},
        {name: 'CSE 231'},
        {name: 'CSE 232'},
        {name: 'CSE 472'}
    ];

    $scope.students = [
        {name: 'Theo'},
        {name: 'Barend'},
        {name: 'Goksu'},
        {name: 'Joe'}
    ];

    $scope.assignments = [
        {name: 'Homework 1.2'},
        {name: 'Exam 2'},
        {name: 'Servelet'},
        {name: 'Pagination Quiz'},
        {name: 'Tiny E Render'}
    ];
});
