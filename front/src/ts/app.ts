/// <reference path="../../typings/tsd.d.ts"/>

////////////////////////////////////////////////// TypeScript

let app = angular.module('main.app', ['ngResource', 'ngStorage', 'ngAnimate', 'ngSanitize', 'ui.bootstrap', 'ui.router', 'angular-loading-bar', 'ui.grid', 'ui.select']);

//////////////////////////////////////////////////////// Service
import {APIEndPoint} from './service/APIEndPoint';
app.factory('apiEndPoint', ['$resource', APIEndPoint.factory]);
import {ProjectList} from './service/ProjectList';
app.factory('projectList', ['apiEndPoint', ProjectList.factory]);

//////////////////////////////////////////////////////// Controller
import {SignInController} from './controller/SignInController';
app.controller('signInController', ['$state', 'apiEndPoint', SignInController]);

import {SignUpController} from './controller/SignUpController';
app.controller('signUpController', ['$state', '$stateParams', 'apiEndPoint', SignUpController]);

import {InviteController} from './controller/InviteController';
app.controller('inviteController', ['$state', 'apiEndPoint', InviteController]);

import {PrjEditDlgController} from './controller/PrjEditDlgController';
app.controller('prjEditDlgController', ['$uibModalInstance', PrjEditDlgController]);

import {IProjectDetail} from './model/IProject';
import {PrjDeleteDlgController} from './controller/PrjDeleteDlgController';
app.controller('prjDeleteDlgController', ['$uibModalInstance', 'project', PrjDeleteDlgController]);

import {ProjectController, ProjectControllerScope} from './controller/ProjectController';
app.controller('projectController', ['apiEndPoint', '$state', '$stateParams', '$uibModal', '$scope', ProjectController]);

////////////////////////////////////////////////////////// Directive
import {StateLocDirective} from './directive/StateLocDirective';
app.directive('stateLoc', StateLocDirective.factory);
import {TopMenuDirective} from './directive/TopMenuDirective';
app.directive('topMenu', TopMenuDirective.factory);
import {CompanyListDirective} from './directive/CompanyListDirective';
app.directive('companyList', CompanyListDirective.factory);
import {ProjectListDirective} from './directive/ProjectListDirective';
app.directive('projectList', ProjectListDirective.factory);

////////////////////////////////////////////////////////// Config
import {Sitemap} from './sitemap/Sitemap';
app.config(['$stateProvider', '$urlRouterProvider', '$httpProvider', Sitemap.factory]);

import {AuthConfig} from './config/AuthConfig';
app.config(['$httpProvider', AuthConfig.factory]);

////////////////////////////////////////////////////////// Filter
import {propsFilter} from './filter/PropsFilter'
app.filter('propsFilter', propsFilter);
