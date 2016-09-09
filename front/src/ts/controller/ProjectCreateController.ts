/// <reference path="../../../typings/tsd.d.ts"/>

import IResource = ng.resource.IResource;
import IStateService = angular.ui.IStateService;
import IStateParamsService = angular.ui.IStateParamsService;

import {APIEndPoint} from '../service/APIEndPoint';
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
    private $state:IStateService,
    private $stateParams:IStateParamsService
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
        // Jump to last page.
      } else {
        console.log(`Failed to create company: ${this.project}`);
      }
    }, (e:any) =>{
      console.log('System error');
    });
  }

  cancel() {
    // Jump to last page.
  }

  public static get state() {
    return {
      url: '/project-create',
      templateUrl: 'user/project/projectCreate.html',
      controller: ['apiEndPoint', '$state', '$stateParams', ProjectCreateController],
      controllerAs: 'c',
    };
  }
}