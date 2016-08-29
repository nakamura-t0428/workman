/// <reference path="../../../typings/tsd.d.ts"/>

import IStateService = angular.ui.IStateService;
import IStateParamsService = angular.ui.IStateParamsService;
import {APIEndPoint} from '../service/APIEndPoint';
import {ISignUpData} from '../model/ISignUpData';
import {ISignUpRespData} from '../model/ISignUpRespData';
import {PostControllerBase} from './PostControllerBase';

export class SignUpController
  extends PostControllerBase<ISignUpData, ISignUpRespData> {
    
  data: ISignUpData = {
    token: '',
    name: '',
    passwd: ''
  };
  passwdRe: string = '';
  
  public static get state() {
    return {
      url: '/signup/:token',
      templateUrl: 'guest/signup.html',
    };
  }

  constructor(
    private $state:IStateService,
    private $stateParams:IStateParamsService,
    private apiEndPoint:APIEndPoint
  ) {
    super(apiEndPoint.signupResource);
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
