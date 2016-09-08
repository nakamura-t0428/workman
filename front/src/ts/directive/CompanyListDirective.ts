/// <reference path="../../../typings/tsd.d.ts"/>
import IDirective = ng.IDirective;

import IModalService = angular.ui.bootstrap.IModalService;
import {APIEndPoint} from '../service/APIEndPoint';
import {ICompany, ICompanyQueryParam, INewCompany, EmptyCompany} from '../model/ICompany';
import {CompanyEditDialog} from '../dialog/CompanyEditDialog';

export class CompanyListDirective implements IDirective {
  private searchOpt:ICompanyQueryParam = {
    name: "",
    limit: 10,
    page: 0,
  };
  private compList:Array<ICompany> = [];

  constructor(
    private apiEndPoint:APIEndPoint,
    private $uibModal:IModalService
  ){
    this.resetList()
  }

  public resetList() {
    this.apiEndPoint.companyResource.query(this.searchOpt, (resp:Array<ICompany>) => {
      this.compList = resp;
    })
  }

  createCompany(data:INewCompany) {
    this.apiEndPoint.companyResource.save(data, (resp:ICompany,r:any) => {
      if(resp.compId) {
        this.compList.push(resp);
      } else {
        console.log(`Failed to create company: ${data.name}`);
      }
    }, (e:any) =>{
      console.log('System error');
    });
  }

  showCreateCompDlg() {
    CompanyEditDialog.showDialog(this.$uibModal, 'New Company', new EmptyCompany, (data) => {this.createCompany(data)});
  }

  public static factory = [() => {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      templateUrl: 'user/company/companyList.html',
      controller: ['apiEndPoint', '$uibModal', CompanyListDirective],
      controllerAs: 'c',
      scope: true
    }
  }];
}
