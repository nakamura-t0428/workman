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

  // For Test
  availableColors = ['Red','Green','Blue','Yellow','Magenta','Maroon','Umbra','Turquoise'];
  colors:Array<string> = [];

  people:Array<IPerson> = [
    {userId:'1', name:'中村 丈洋', email:'nakamura@test.test.com'},
    {userId:'2', name:'山田 太郎', email:'taro.yamada@test.test.com'},
    {userId:'3', name:'山田 テスト用', email:'yamada@test.test.com'},
  ]
  selected:IPerson = undefined;
  workerSelected(item:IPerson, model:any) {
    console.log(item);
    console.log(model);
  }
}

interface IPerson {
  userId:string;
  name:string;
  email:string;
}
