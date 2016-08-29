/// <reference path="../../../typings/tsd.d.ts"/>

import IStateProvider = ng.ui.IStateProvider;
import IStateService = angular.ui.IStateService;
import IUrlRouterProvider= ng.ui.IUrlRouterProvider;
import IHttpProvider = angular.IHttpProvider;
import IStorageService = angular.storage.IStorageService;

import {IMyInfoResp} from "../model/IMyInfoResp";
import {InviteController} from "../controller/InviteController";
import {SignUpController} from "../controller/SignUpController";
import {GuestController} from '../controller/GuestController';
import {UserController} from '../controller/UserController';
import {ProjectController} from '../controller/ProjectController';

export class Sitemap {
  constructor(
    private $stateProvider:IStateProvider,
    private $urlRouterProvider:IUrlRouterProvider,
    private $httpProvider:IHttpProvider
  ) {
    $urlRouterProvider.otherwise('/guest/top')
    $stateProvider
    .state('guest', GuestController.state)
    .state('guest.top', { //ゲスト画面トップ（ログイン）
      url: '/top',
      templateUrl: 'guest/top.html',
    })
    .state('guest.invite', InviteController.state) //ゲスト画面 登録申請
    .state('guest.signup', SignUpController.state) //ゲスト画面 本登録
    .state('user', UserController.state) //ユーザ 基礎
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
    .state('user.top.project', ProjectController.state) // ユーザ プロジェクト
    .state('user.top.project.sitemap', { // ユーザ プロジェクト
      url: '/sitemap',
      templateUrl: 'user/project/projectSitemap.html',
    })
    .state('user.top.project.member', { // ユーザ プロジェクト
      url: '/member',
      templateUrl: 'user/project/projectMember.html',
    })
  }

  public static factory(
    $stateProvider:IStateProvider,
    $urlRouterProvider:IUrlRouterProvider,
    $httpProvider:IHttpProvider
  ):Sitemap {
    return new Sitemap($stateProvider, $urlRouterProvider, $httpProvider);
  }
}
