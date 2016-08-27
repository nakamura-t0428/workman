/// <reference path="../../../typings/tsd.d.ts"/>

import IStateService = angular.ui.IStateService;
import IStateParamsService = angular.ui.IStateParamsService;
import IModalService = angular.ui.bootstrap.IModalService;
import IScope = angular.IScope
import {IProjectDetail} from '../model/IProjectData';
import {ProjectDataResource} from '../resource/ProjectDataResource';
import {IBaseRespData} from '../model/IBaseRespData';
import {UserController} from './UserController';

export interface ProjectControllerScope extends IScope {
  userCtrl: UserController
}

export class ProjectController {
  public project:IProjectDetail;
  
  constructor(
    private projectResource:ProjectDataResource,
    private $state:IStateService,
    private $stateParams:IStateParamsService,
    private $uibModal:IModalService,
    private $scope:ProjectControllerScope) {
      projectResource.get({'prjId':$stateParams['prjId']}, (resp:IProjectDetail)=>{
        this.project = resp;
        $scope['userCtrl'].topMenu = [
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
        ]
        $scope['userCtrl'].loc = {
          label: this.project.prjInfo.name,
          state: 'user.top.project',
          desc: '',
          stateParams: {prjId: this.project.prjInfo.prjId}
        };
      })
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
    this.projectResource.delete({'prjId':this.project.prjInfo.prjId}, (resp:IBaseRespData)=>{
      this.$state.go('user.top', {}, {reload: true});
    });
  }
}
