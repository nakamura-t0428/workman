/// <reference path="../../../typings/tsd.d.ts"/>

import IResource = ng.resource.IResource;
import IStateService = angular.ui.IStateService;
import IStateParamsService = angular.ui.IStateParamsService;
import IWindowService = ng.IWindowService;
import ii = angular.ui.bootstrap.IDatepickerConfig;

import {APIEndPoint} from '../service/APIEndPoint';
import {IProjectDetail} from '../model/IProject';

interface DtPickerClassEv {
  date:Date;
  mode:string;
}

export class ProjectPlanController {
  public datePickerOptions = {
    customClass: this.dayClass,
    showWeeks: true
  };
  public dt = new Date();
  constructor(
    private apiEndPoint:APIEndPoint,
    private project:IProjectDetail,
    private $state:IStateService,
    private $stateParams:IStateParamsService,
    private $window:IWindowService
  ) {}

  dayClass(ev:DtPickerClassEv) {
    if(ev.mode == "day") {
      switch(ev.date.getDay()) {
        case 0:
          return 'day-sun';
        case 6:
          return 'day-sat';
      }
      return '';
    }

    return '';
  }

  public static get state() {
    return {
      url: '/plan',
      templateUrl: 'user/project/projectPlan.html',
      controller: ['apiEndPoint', 'project', '$state', '$stateParams', '$window', ProjectPlanController],
      controllerAs: 'c',
    };
  }
}