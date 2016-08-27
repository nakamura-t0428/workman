/// <reference path="../../../typings/tsd.d.ts"/>

import IModalServiceInstance = angular.ui.bootstrap.IModalServiceInstance;
import {INewProject} from '../model/IProjectData';
import {IProjectDetail} from '../model/IProjectData';

export class PrjDeleteDlgController {
  private confirm = false;
  constructor(
    private $uibModalInstance: IModalServiceInstance,
    private project:IProjectDetail
  ) {}
  
  ok() {
    if(confirm){
      this.$uibModalInstance.close();
    }
  }

  cancel() {
    this.$uibModalInstance.dismiss('cancel')
  }
}
