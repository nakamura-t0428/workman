/// <reference path="../../../typings/tsd.d.ts"/>

import IModalService = angular.ui.bootstrap.IModalService;
import {IProject, INewProject} from '../model/IProjectData';
import {ProjectDataResource} from '../resource/ProjectDataResource';
import {ILimit} from '../model/ILimit';

const LIMIT_IN_MENU:ILimit = {
  limit: 10,
  page: 0
}
export class ProjectListController {
  projects:IProject[];
  
  constructor(
    private projectResource:ProjectDataResource,
    private $uibModal:IModalService) {
      projectResource.query(LIMIT_IN_MENU, (resp:IProject[])=>{
        this.projects = resp;
      })
  }

  createProject(prjName:String) {
    let resource = new this.projectResource({'name' : prjName});
    resource.$save((resp:IProject,r:any) => {
      if(resp.prjId) {
        this.projects.push(resp);
      } else {
        console.log(`Failed to create project: ${prjName}`);
      }
    }, (e:any) =>{
      console.log('System error');
    });
  }

  showCreatePrjDlg() {
    let modal = this.$uibModal.open({
      templateUrl: 'user/projectEdit.html',
      controller: 'prjEditDlgController',
      controllerAs: 'modalCtrl',
      size: 'small',
    });
    modal.result.then((data:INewProject) => {
      this.createProject(data.name);
    })
  }
}
