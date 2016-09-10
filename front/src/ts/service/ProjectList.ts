/// <reference path="../../../typings/tsd.d.ts"/>

import {APIEndPoint} from '../service/APIEndPoint';
import {IProject, IProjectQueryParam} from '../model/IProject';

export class ProjectList {
  public searchOpt:IProjectQueryParam = {
    name: "",
    limit: 10,
    page: 0,
  };
  constructor(
    private apiEndPoint:APIEndPoint
  ) {}
  public prjList:Array<IProject> = [];

  public resetList() {
    this.apiEndPoint.projectResource.query(this.searchOpt, (resp:Array<IProject>) => {
      this.prjList = resp;
    })
  }

  public static factory(
    apiEndPoint:APIEndPoint
  ):ProjectList {
    return new ProjectList(apiEndPoint);
  }
}
