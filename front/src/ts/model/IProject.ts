import {IMember} from './IMemberData'
import {ICompanyAbs} from './ICompany'

export interface IProject {
  prjId: string;
  name: string;
  compId:string;
  description:string;
}

export interface IProjectPlan {
  expectedDays: number;
  startDate: string;
  startDateFixed: boolean;
}

export interface INewProject {
  name:string;
  compId:string;
  description:string;
}

export interface IProjectDetail {
  prjInfo: IProject;
  prjPlan: IProjectPlan;
  company: ICompanyAbs;
  owner: IMember;
  members: IMember[];
}

export interface IProjectQueryParam{
  name: string;
  limit: number;
  page: number;
}
