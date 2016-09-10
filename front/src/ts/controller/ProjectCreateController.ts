/// <reference path="../../../typings/tsd.d.ts"/>

import IResource = ng.resource.IResource;
import IStateService = angular.ui.IStateService;
import IStateParamsService = angular.ui.IStateParamsService;
import IWindowService = ng.IWindowService;

import {APIEndPoint} from '../service/APIEndPoint';
import {ProjectList} from '../service/ProjectList';
import {INewProject, IProject} from '../model/IProject';
import {ICompany, ICompanyQueryParam} from '../model/ICompany';

export class ProjectCreateController {
  private project:INewProject = {
    name: "",
    compId: "",
    description: ""
  }
  private selectedComp:ICompany;

  constructor(
    private apiEndPoint:APIEndPoint,
    private projectList:ProjectList,
    private $state:IStateService,
    private $stateParams:IStateParamsService,
    private $window:IWindowService
  ) {}

  getCompList(keyword:string) {
    return this.apiEndPoint.companyResource.query({
      name:keyword,
      limit:0,
      page:0
    }).$promise;
  }

  ok() {
    this.project.compId = this.selectedComp.compId
    let resource = new this.apiEndPoint.projectResource(this.project);
    resource.$save((resp:IProject,r:any) => {
      if(resp.prjId) {
        // Update Project List.
        this.projectList.resetList();
        // Jump to Detail.
        this.$state.go('user.top.project', {prjId: resp.prjId});
      } else {
        console.log(`Failed to create company: ${this.project}`);
      }
    }, (e:any) =>{
      console.log('System error');
    });
  }

  cancel() {
    this.$window.history.back();
  }

  public static get state() {
    return {
      url: '/project-create',
      templateUrl: 'user/project/projectCreate.html',
      controller: ['apiEndPoint', 'projectList', '$state', '$stateParams', '$window', ProjectCreateController],
      controllerAs: 'c',
    };
  }
}