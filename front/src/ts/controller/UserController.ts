/// <reference path="../../../typings/tsd.d.ts"/>

import IState = angular.ui.IState;
import IStateService = angular.ui.IStateService;
import IStorageService = angular.storage.IStorageService;

import {APIEndPoint} from '../service/APIEndPoint';
import {IMyInfoResp} from '../model/IMyInfoResp';
import {ITopMenu} from '../model/ITopMenu';

const GUEST_TOP_ST = 'guest.top';

export class UserController {
  public topMenu:Array<ITopMenu> = [];
  
  public static get state() {
    return {
      abstract: true,
      url: '/user',
      templateUrl: 'user/base.html',
      resolve: {
        myInfo: ['$state', 'apiEndPoint', function($state:IStateService ,apiEndPoint:APIEndPoint) {
          console.log('userCtrl');
          return apiEndPoint.myinfoResource.get({},
            (r:IMyInfoResp) => {
              if(!r || !r.success) {
                $state.go(GUEST_TOP_ST);
              }
            },
            (e:any) => {
              $state.go(GUEST_TOP_ST);
            });
        }],
        loc: function() {
          return {
            label: 'ダッシュボード',
            state: 'user.top',
            desc: ''
          };
        }
      },
      controller: ['$state', '$localStorage', 'apiEndPoint', 'myInfo', UserController],
      controllerAs: 'userCtrl'
    };
  }

  public static get topState():IState {
    return {
      url: '/top',
      views: {
        'left-menu': {
          templateUrl: 'user/left-menu.html',
        },
        'main-contents': {
          templateUrl: 'user/top.html'
        },
      },
    }      
  }

  constructor(
    private $state:IStateService,
    private $localStorage:IStorageService,
    private apiEndPoint:APIEndPoint,
    public myInfo:IMyInfoResp) {}
  
  signOut() {
    this.$localStorage.$reset();
    this.$state.go(GUEST_TOP_ST);
  }
}
