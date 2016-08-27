/// <reference path="../../../typings/tsd.d.ts"/>

import IStateService = angular.ui.IStateService;
import IStateParamsService = angular.ui.IStateParamsService;
import {ISignInData} from '../model/ISignInData'
import {ISignInRespData} from '../model/ISignInRespData';
import {SignInDataResource} from '../resource/SignInDataResource'
import {PostControllerBase} from './PostControllerBase';


export class SignInController
  extends PostControllerBase<ISignInData, SignInDataResource, ISignInRespData> {
    
  data: ISignInData = {
    email: '',
    passwd: ''
  }
  
  constructor(
    private $state:IStateService,
    private signInDataResource: SignInDataResource
  ) {
    super(signInDataResource);
  }
  
  onSubmitSuccess(resp:ISignInRespData, r:any):void {
    // 状態を更新
    super.pushMessage('サインインしました');
    this.$state.reload();
  }
}
