/// <reference path="../../../typings/tsd.d.ts"/>

import IStateService = angular.ui.IStateService;
import {MyInfoResource} from '../resource/MyInfoResource';
import {IMyInfoResp} from '../model/IMyInfoResp';

export class GuestController {
  public static get state() {
    return { //ゲスト画面のベース
      abstract: true,
      url: '/guest',
      templateUrl: 'guest/base.html',
      controller: ['$state', 'myInfoResource', GuestController],
      controllerAs: 'guestCtrl'
    };
  }
  constructor(
    private $state:IStateService,
    private myInfoResource:MyInfoResource) {
      myInfoResource.get(function(resp:IMyInfoResp) {
        if(resp && resp.success) {
          $state.go('user.top');
        }
      });
  }
}
