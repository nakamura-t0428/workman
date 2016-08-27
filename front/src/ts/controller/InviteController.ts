/// <reference path="../../../typings/tsd.d.ts"/>

import IStateService = angular.ui.IStateService;
import IStateParamsService = angular.ui.IStateParamsService;
import {InviteDataResource} from '../resource/InviteDataResource'
import {PostControllerBase} from './PostControllerBase';
import {IInviteRespData} from '../model/IInviteRespData';
import {IInviteData} from '../model/IInviteData';

export class InviteController
  extends PostControllerBase<IInviteData, InviteDataResource, IInviteRespData> {
  data: IInviteData = {
    email: ''
  };
  mailSent = false;
  
  constructor(
    private $state:IStateService,
    private inviteDataResource: InviteDataResource
  ) {
    super(inviteDataResource);
  }
  
  onSubmitSuccess(resp:IInviteRespData, r:any):void {
    super.pushMessage('招待メールを送信しました');
    this.mailSent = true;
  }
}
