/**
 * Created by Kristijan Cvetkovikj on 9/5/16.
 */

/**
 * Secured states
 * All inherit from root (which has data : secured)
 */
app.config([
    '$stateProvider',
    '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/');

        $stateProvider.state('root', {
            url: '/',
            views: {
                nav: {
                    templateUrl: 'src/views/nav.html'
                },
                main: {
                    templateUrl: 'src/views/main.html'
                }
            }
        });

        $stateProvider.state('root.about', {
            url: 'about',
            views: {
                'main@': {
                    templateUrl: "src/views/about.html"
                }
            }
        });

        $stateProvider.state('root.book-likes', {
            url: 'book-likes',
            views: {
                'main@': {
                    templateUrl: "src/views/book-likes.html"
                }
            }
        });

        $stateProvider.state('root.book-orders', {
            url: 'book-orders',
            views: {
                'main@': {
                    templateUrl: "src/views/book-orders.html"
                }
            }
        });


        $stateProvider.state('root.book-price', {
            url: 'book-price',
            views: {
                'main@': {
                    templateUrl: "src/views/book-price.html"
                }
            }
        });

        $stateProvider.state('root.books', {
            url: 'books',
            views: {
                'main@': {
                    templateUrl: "src/views/books.html"
                }
            }
        });

        $stateProvider.state('root.create', {
            url: 'create',
            views: {
                'main@': {
                    templateUrl: "src/views/create.html"
                }
            }
        });

        $stateProvider.state('root.dashboard', {
            url: 'dashboard',
            views: {
                'main@': {
                    templateUrl: "src/views/dashboard.html"
                }
            }
        });

        $stateProvider.state('root.form', {
            url: 'form',
            views: {
                'main@': {
                    templateUrl: "src/views/form.html"
                }
            }
        });

        $stateProvider.state('root.home', {
            url: 'home',
            views: {
                'main@': {
                    templateUrl: "src/views/home.html"
                }
            }
        });

        $stateProvider.state('root.like-stars', {
            url: 'like-stars',
            views: {
                'main@': {
                    templateUrl: "src/views/like-stars.html"
                }
            }
        });

        $stateProvider.state('root.index', {
            url: 'index',
            views: {
                'main@': {
                    templateUrl: "src/views/index.html"
                }
            }
        });

        $stateProvider.state('root.likes', {
            url: 'likes',
            views: {
                'main@': {
                    templateUrl: "src/views/likes.html"
                }
            }
        });

        $stateProvider.state('root.list', {
            url: 'list',
            views: {
                'main@': {
                    templateUrl: "src/views/list.html"
                }
            }
        });

        $stateProvider.state('root.login', {
            url: 'login',
            views: {
                'main@': {
                    templateUrl: "src/views/login.html"
                }
            }
        });

        $stateProvider.state('root.orders', {
            url: 'orders',
            views: {
                'main@': {
                    templateUrl: "src/views/orders.html"
                }
            }
        });

        $stateProvider.state('root.register', {
            url: 'register',
            views: {
                'main@': {
                    templateUrl: "src/views/register.html"
                }
            }
        });

        $stateProvider.state('root.show', {
            url: 'show',
            views: {
                'main@': {
                    templateUrl: "src/views/show.html"
                }
            }
        });

        $stateProvider.state('root.top', {
            url: 'top',
            views: {
                'main@': {
                    templateUrl: "src/views/top.html"
                }
            }
        });

        $stateProvider.state('root.update', {
            url: 'update',
            views: {
                'main@': {
                    templateUrl: "src/views/update.html"
                }
            }
        });

        $stateProvider.state('root.users', {
            url: 'users',
            views: {
                'main@': {
                    templateUrl: "src/views/users.html"
                }
            }
        });
    }])
    .run([
        '$rootScope',
        function($rootScope) {

            $rootScope.menuItems = [{
                state: 'root',
                icon: 'fa-home',
                name: 'wp.home'

            }, {
                state: 'root.students',
                icon: 'fa-users',
                name: 'wp.students'
            }, {
                state: 'root.courses',
                icon: 'fa-users',
                name: 'wp.courses'
            }];

        }]);