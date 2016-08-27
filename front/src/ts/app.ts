/// <reference path="../../typings/tsd.d.ts"/>

import IStateProvider = ng.ui.IStateProvider;
import IStateService = angular.ui.IStateService;
import IStateParamsService = angular.ui.IStateParamsService;
import IUrlRouterProvider= ng.ui.IUrlRouterProvider;
import IHttpProvider = angular.IHttpProvider;
import IResourceService = ng.resource.IResourceService;
import IStorageService = angular.storage.IStorageService;
import IModalService = ng.ui.bootstrap.IModalService;
import IModalServiceInstance = ng.ui.bootstrap.IModalServiceInstance;

////////////////////////////////////////////////// TypeScript

let app = angular.module('main.app', ['ngResource', 'ngStorage', 'ngAnimate', 'ui.bootstrap', 'ui.router', 'angular-loading-bar', 'ui.grid']);

//////////////////////////////////////////////////////// Config
import {ConfigService} from "./service/ConfigService";
app.factory('config', [() => new ConfigService()]);

//////////////////////////////////////////////////////// Resource
import {SignInDataResourceFactory, SignInDataResource} from './resource/SignInDataResource';
app.factory('signInDataResource', ['config', '$resource', SignInDataResourceFactory]);

import {SignUpDataResourceFactory, SignUpDataResource} from './resource/SignUpDataResource';
app.factory('signUpDataResource', ['config', '$resource', SignUpDataResourceFactory]);

import {MyInfoResourceFactory, MyInfoResource} from './resource/MyInfoResource';
app.factory('myInfoResource', ['config', '$resource', MyInfoResourceFactory]);

import {InviteDataResourceFactory, InviteDataResource} from './resource/InviteDataResource';
app.factory('inviteDataResource', ['config', '$resource', InviteDataResourceFactory]);

import {ProjectDataResourceFactory, ProjectDataResource} from './resource/ProjectDataResource';
app.factory('projectDataResource', ['config', '$resource', ProjectDataResourceFactory]);

//////////////////////////////////////////////////////// Controller
import {SignInController} from './controller/SignInController';
app.controller('signInController', ['$state', 'signInDataResource',
  ($state:IStateService, signInDataResource: SignInDataResource) =>
    new SignInController($state, signInDataResource)]);

import {SignUpController} from './controller/SignUpController';
app.controller('signUpController', ['$state', '$stateParams', 'signUpDataResource',
  ($state:IStateService, $stateParams: IStateParamsService, signUpDataResource: SignUpDataResource) =>
    new SignUpController($state, $stateParams, signUpDataResource)]);

import {InviteController} from './controller/InviteController';
app.controller('inviteController', ['$state', 'inviteDataResource',
  ($state:IStateService, inviteDataResource: InviteDataResource) =>
    new InviteController($state, inviteDataResource)]);

import {ProjectListController} from './controller/ProjectListController';
app.controller('projectListController', ['projectDataResource', '$uibModal',
  (projectDataResource: ProjectDataResource, $uibModal: IModalService) =>
    new ProjectListController(projectDataResource, $uibModal)]);

import {PrjEditDlgController} from './controller/PrjEditDlgController';
app.controller('prjEditDlgController', ['$uibModalInstance',
  ( $uibModalInstance: IModalServiceInstance) =>
    new PrjEditDlgController($uibModalInstance)]);

import {IProjectDetail} from './model/IProjectData';
import {PrjDeleteDlgController} from './controller/PrjDeleteDlgController';
app.controller('prjDeleteDlgController', ['$uibModalInstance', 'project',
  ( $uibModalInstance: IModalServiceInstance, project:IProjectDetail) =>
    new PrjDeleteDlgController($uibModalInstance, project)]);

import {ProjectController, ProjectControllerScope} from './controller/ProjectController';
app.controller('projectController', ['projectDataResource', '$state', '$stateParams', '$uibModal', '$scope',
  (projectResource: ProjectDataResource, $state:IStateService, $stateParams: IStateParamsService, $uibModal: IModalService, $scope: ProjectControllerScope) =>
    new ProjectController(projectResource, $state, $stateParams, $uibModal, $scope)]);

// import {GuestController} from './controller/GuestController';
// import {IMyInfoResp} from './model/IMyInfoResp';
// app.controller('guestController', ['$state', 'myInfo', 'isLogin',
//   ($state:IStateService, myInfo: IMyInfoResp, isLogin:boolean) =>
//     new GuestController($state, myInfo, isLogin)]);

////////////////////////////////////////////////////////// Directive
import {SitemapDirective} from './directive/SitemapDirective';
app.directive('prjSitemap', [() => new SitemapDirective()]);

////////////////////////////////////////////////////////// Config    
import {Sitemap} from './sitemap/Sitemap';
app.config(['$stateProvider', '$urlRouterProvider', '$httpProvider',
  ($stateProvider:IStateProvider, $urlRouterProvider:IUrlRouterProvider, $httpProvider:IHttpProvider) =>
    new Sitemap($stateProvider, $urlRouterProvider, $httpProvider)]);

import {AuthConfig} from './config/AuthConfig';
app.config(['$httpProvider', ($httpProvider:IHttpProvider) => new AuthConfig($httpProvider)]);

// import {AuthService} from './service/AuthService';
// app.service('authService', ['$state', '$localStorage',
//   ($state:IStateService, $localStorage:IStorageService) =>
//     new AuthService($state, $localStorage)]);
