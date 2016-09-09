import {IMember} from './IMemberData'

export interface IProject {
  prjId: string;
  name: string;
}

export interface INewProject {
  name:string;
  compId:string;
  description:string;
}

export interface IProjectDetail {
  prjInfo: IProject;
  owner: IMember;
  members: IMember[];
}

export interface IProjectQueryParam{
  name: string;
  limit: number;
  page: number;
}