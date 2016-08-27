/// <reference path="../../../typings/tsd.d.ts"/>

import IStateService = angular.ui.IStateService;
import IStateParamsService = angular.ui.IStateParamsService;
import {ISignUpData} from '../model/ISignUpData';
import {ISignUpRespData} from '../model/ISignUpRespData';
import {SignUpDataResource} from '../resource/SignUpDataResource';
import {PostControllerBase} from './PostControllerBase';

export class SignUpController
  extends PostControllerBase<ISignUpData, SignUpDataResource, ISignUpRespData> {
    
  data: ISignUpData = {
    token: '',
    name: '',
    passwd: ''
  };
  passwdRe: string = '';
  
  constructor(
    private $state:IStateService,
    private $stateParams:IStateParamsService,
    private signUpDataResource:SignUpDataResource
  ) {
    super(signUpDataResource);
    this.data.token = $stateParams['token'];
  }
  
  onSubmitSuccess(resp:ISignUpRespData, r:any):void {
    super.pushMessage('登録しました');
    this.$state.go('user.top');
  }
  
  validate():boolean {
    if(this.data.passwd != this.passwdRe) {
      super.pushError('パスワードが一致しません');
      return false;
    }
    return true;
  }
}
