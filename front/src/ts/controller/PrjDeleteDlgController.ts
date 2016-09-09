/// <reference path="../../../typings/tsd.d.ts"/>

import IModalServiceInstance = angular.ui.bootstrap.IModalServiceInstance;
import {INewProject, IProjectDetail} from '../model/IProject';

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
