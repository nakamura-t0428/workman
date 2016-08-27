/// <reference path="../../../typings/tsd.d.ts"/>

import IStateProvider = ng.ui.IStateProvider;
import IStateService = angular.ui.IStateService;
import IUrlRouterProvider= ng.ui.IUrlRouterProvider;
import IHttpProvider = angular.IHttpProvider;
import IStorageService = angular.storage.IStorageService;

import {MyInfoResource} from '../resource/MyInfoResource';
import {AuthService} from '../service/AuthService';
import {MyInfoFactory} from '../factory/MyInfoFactory'
import {IMyInfoResp} from "../model/IMyInfoResp";
import {GuestController} from '../controller/GuestController';
import {UserController} from '../controller/UserController';

export class Sitemap {
  constructor(
    private $stateProvider:IStateProvider,
    private $urlRouterProvider:IUrlRouterProvider,
    private $httpProvider:IHttpProvider
  ) {
    $urlRouterProvider.otherwise('/guest/top')
    $stateProvider
    .state('guest', { //ゲスト画面のベース
      abstract: true,
      url: '/guest',
      templateUrl: 'guest/base.html',
      controller: ['$state', 'myInfoResource', ($state:IStateService, myInfoResource:MyInfoResource) => new GuestController($state, myInfoResource)],
      controllerAs: 'guestCtrl'
    })
    $stateProvider
    .state('guest.top', { //ゲスト画面トップ（ログイン）
      url: '/top',
      templateUrl: 'guest/top.html',
    })
    $stateProvider
    .state('guest.invite', { //ゲスト画面 登録申請
      url: '/invite',
      templateUrl: 'guest/invite.html',
    })
    $stateProvider
    .state('guest.signup', { //ゲスト画面 本登録
      url: '/signup/:token',
      templateUrl: 'guest/signup.html',
    })
    $stateProvider
    .state('user', { //ユーザ 基礎
      abstract: true,
      url: '/user',
      templateUrl: 'user/base.html',
      controller: ['$state', '$localStorage', 'myInfoResource',
       ($state:IStateService, $localStorage:IStorageService, myInfoResource:MyInfoResource) =>
        new UserController($state, $localStorage, myInfoResource)],
      controllerAs: 'userCtrl'
    })
    $stateProvider
    .state('user.top', { // ユーザ トップ
      url: '/top',
      views: {
        'left-menu': {
          templateUrl: 'user/left-menu.html',
        },
        'main-contents': {
          templateUrl: 'user/top.html'
        },
      },
    })
    $stateProvider
    .state('user.top.project', { // ユーザ プロジェクト
      url: '/project/:prjId',
      templateUrl: 'user/project/projectDetail.html',
    })
    $stateProvider
    .state('user.top.project.sitemap', { // ユーザ プロジェクト
      url: '/sitemap',
      templateUrl: 'user/project/projectSitemap.html',
    })
    $stateProvider
    .state('user.top.project.member', { // ユーザ プロジェクト
      url: '/member',
      templateUrl: 'user/project/projectMember.html',
    })
  }
}
