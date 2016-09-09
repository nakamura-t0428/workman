/// <reference path="../../../typings/tsd.d.ts"/>
import IDirective = ng.IDirective;

import IModalService = angular.ui.bootstrap.IModalService;
import {APIEndPoint} from '../service/APIEndPoint';
import {IProject, INewProject, IProjectQueryParam} from '../model/IProject';
import {CompanyEditDialog} from '../dialog/CompanyEditDialog';

export class ProjectListDirective implements IDirective {
  private searchOpt:IProjectQueryParam = {
    name: "",
    limit: 10,
    page: 0,
  };
  private prjList:Array<IProject> = [];

  constructor(
    private apiEndPoint:APIEndPoint,
    private $uibModal:IModalService
  ){
    this.resetList()
  }

  public resetList() {
    this.apiEndPoint.projectResource.query(this.searchOpt, (resp:Array<IProject>) => {
      this.prjList = resp;
    })
  }

  public static factory = [() => {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'user/project/projectList.html',
      controller: ['apiEndPoint', '$uibModal', ProjectListDirective],
      controllerAs: 'c',
      scope: true
    }
  }];
}
