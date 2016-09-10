/// <reference path="../../../typings/tsd.d.ts"/>
import IDirective = ng.IDirective;

import IModalService = angular.ui.bootstrap.IModalService;
import {APIEndPoint} from '../service/APIEndPoint';
import {ProjectList} from '../service/ProjectList';
import {IProject, INewProject, IProjectQueryParam} from '../model/IProject';
import {CompanyEditDialog} from '../dialog/CompanyEditDialog';

export class ProjectListDirective implements IDirective {
  constructor(
    private apiEndPoint:APIEndPoint,
    private $uibModal:IModalService,
    private projectList:ProjectList
  ){
    projectList.resetList();
  }

  public get prjList():Array<IProject> {
    return this.projectList.prjList;
  }

  public static factory = [() => {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'user/project/projectList.html',
      controller: ['apiEndPoint', '$uibModal', 'projectList', ProjectListDirective],
      controllerAs: 'c',
      scope: true
    }
  }];
}
