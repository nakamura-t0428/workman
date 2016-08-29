/// <reference path="../../../typings/tsd.d.ts"/>

import IStateService = angular.ui.IStateService;
import IStateParamsService = angular.ui.IStateParamsService;
import {APIEndPoint} from '../service/APIEndPoint';
import {ISignInData} from '../model/ISignInData'
import {ISignInRespData} from '../model/ISignInRespData';
import {PostControllerBase} from './PostControllerBase';


export class SignInController
  extends PostControllerBase<ISignInData, ISignInRespData> {
    
  data: ISignInData = {
    email: '',
    passwd: ''
  }
  
  constructor(
    private $state:IStateService,
    private apiEndPoint: APIEndPoint
  ) {
    super(apiEndPoint.signinResource);
  }
  
  onSubmitSuccess(resp:ISignInRespData, r:any):void {
    // 状態を更新
    super.pushMessage('サインインしました');
    this.$state.reload();
  }
}
