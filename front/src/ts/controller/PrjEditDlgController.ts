/// <reference path="../../../typings/tsd.d.ts"/>

import IModalServiceInstance = angular.ui.bootstrap.IModalServiceInstance;
import {INewProject} from '../model/IProjectData';

export class PrjEditDlgController {
  data:INewProject = {
    name: ''
  };
  
  constructor(
    private $uibModalInstance: IModalServiceInstance
  ) {}
  
  ok() {
    this.$uibModalInstance.close(this.data);
  }

  cancel() {
    this.$uibModalInstance.dismiss('cancel')
  }
}
