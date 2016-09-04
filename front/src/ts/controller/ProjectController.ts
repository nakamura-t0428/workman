/// <reference path="../../../typings/tsd.d.ts"/>

import IResource = ng.resource.IResource;
import IStateService = angular.ui.IStateService;
import IStateParamsService = angular.ui.IStateParamsService;
import IModalService = angular.ui.bootstrap.IModalService;
import IScope = angular.IScope
import {APIEndPoint} from '../service/APIEndPoint';
import {IProjectDetail} from '../model/IProjectData';
import {IBaseRespData} from '../model/IBaseRespData';
import {UserController} from './UserController';
import {ILocState} from '../model/ILocState';
import {ITopMenu} from '../model/ITopMenu';

export interface ProjectControllerScope extends IScope {
  userCtrl: UserController
}

export class ProjectController {
  
  public static get state() {
    return {
      url: '/project/:prjId',
      templateUrl: 'user/project/projectDetail.html',
      resolve: {
        prjInfo: ['$state', '$stateParams', 'apiEndPoint', function($state:IStateService, $stateParams:IStateParamsService, apiEndPoint:APIEndPoint) {
          return apiEndPoint.projectResource.get({'prjId':$stateParams['prjId']}, (r:IProjectDetail)=>{
          }, (e:any)=>{});
        }],
        loc: ['prjInfo' ,function(prjInfo:IResource<IProjectDetail>) {
          return prjInfo.$promise.then((r:IProjectDetail)=> {
            return {
              label: r.prjInfo.name,
              state: 'user.top.project',
              desc: '',
              stateParams: {prjId: r.prjInfo.prjId}
            };
          });
        }],
        topMenu: function():Array<ITopMenu> {
          return [
            {
              label: 'サイトマップ',
              desc: '',
              state: 'user.top.project.sitemap'
            },
            {
              label: 'メンバー',
              desc: '',
              state: 'user.top.project.member'
            }
          ];
        }
      },
      controller: ['apiEndPoint', '$state', '$stateParams', '$uibModal', '$scope', 'prjInfo', ProjectController],
      controllerAs: 'prjCtrl',
    };
  }

  constructor(
    private apiEndPoint:APIEndPoint,
    private $state:IStateService,
    private $stateParams:IStateParamsService,
    private $uibModal:IModalService,
    private $scope:ProjectControllerScope,
    public project:IProjectDetail) {
  }

  isDeletable() {
    return this.project && this.$scope.userCtrl.myInfo && this.project.owner.userId == this.$scope.userCtrl.myInfo.userId;
  }

  showDeletePrjDlg() {
    if(!this.isDeletable()) return;
    let modal = this.$uibModal.open({
      templateUrl: 'user/project/projectDelete.html',
      controller: 'prjDeleteDlgController',
      controllerAs: 'modalCtrl',
      size: 'small',
      resolve: {
        project: () => {
          return this.project;
        }
      }
    });
    modal.result.then(() => {
      this.deleteProject();
    });
  }

  deleteProject() {
    this.apiEndPoint.projectResource.delete({'prjId':this.project.prjInfo.prjId}, (resp:IBaseRespData)=>{
      this.$state.go('user.top', {}, {reload: true});
    });
  }
}
