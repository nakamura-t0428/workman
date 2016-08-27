/// <reference path="../../../typings/tsd.d.ts"/>

import IStateService = angular.ui.IStateService;
import IStorageService = angular.storage.IStorageService;
import {MyInfoResource} from '../resource/MyInfoResource';
import {IMyInfoResp} from '../model/IMyInfoResp';

export class AuthService {
  constructor(
    private $state:IStateService,
    private $localStorage:IStorageService
  ) {}
  logout() {
    this.$localStorage.$reset();
    this.$state.go('guest.top');
  }
}
