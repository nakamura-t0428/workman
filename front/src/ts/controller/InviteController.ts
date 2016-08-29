/// <reference path="../../../typings/tsd.d.ts"/>

import IStateService = angular.ui.IStateService;
import IStateParamsService = angular.ui.IStateParamsService;
import {APIEndPoint} from '../service/APIEndPoint';
import {PostControllerBase} from './PostControllerBase';
import {IInviteRespData} from '../model/IInviteRespData';
import {IInviteData} from '../model/IInviteData';

export class InviteController
  extends PostControllerBase<IInviteData, IInviteRespData> {
  data: IInviteData = {
    email: ''
  };
  mailSent = false;
  
  public static get state() {
    return {
      url: '/invite',
      templateUrl: 'guest/invite.html',
    };
  }

  constructor(
    private $state:IStateService,
    private apiEndPoint: APIEndPoint
  ) {
    super(apiEndPoint.inviteResource);
  }
  
  onSubmitSuccess(resp:IInviteRespData, r:any):void {
    super.pushMessage('招待メールを送信しました');
    this.mailSent = true;
  }
}
