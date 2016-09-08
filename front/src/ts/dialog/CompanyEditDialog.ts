/// <reference path="../../../typings/tsd.d.ts"/>

import IModalService = angular.ui.bootstrap.IModalService;
import IModalServiceInstance = angular.ui.bootstrap.IModalServiceInstance;
import {ICompany, ICompanyQueryParam, INewCompany, EmptyCompany} from '../model/ICompany';

export class CompanyEditDialog {
  constructor(
    private $uibModalInstance:IModalServiceInstance,
    private data:INewCompany,
    private title:string
    ){}
  
  ok() {
    this.$uibModalInstance.close(this.data);
  }

  cancel() {
    this.$uibModalInstance.dismiss('cancel')
  }

  public static showDialog($uibModal:IModalService, title:string, data:INewCompany, resultThen:(data:INewCompany) => void) {
    var modal = $uibModal.open({
      templateUrl: 'user/company/companyEdit.html',
      controller: ['$uibModalInstance', 'data', 'title', CompanyEditDialog],
      controllerAs: 'modalCtrl',
      size: 'small',
      resolve: {
        data: () => {return data;},
        title: () => {return title;}
      }
    });
    modal.result.then((data:INewCompany) => {
      resultThen(data);
    });
  }
}