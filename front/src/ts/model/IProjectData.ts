import {IMember} from './IMemberData'

export interface IProject {
  name: string;
  prjId: string;
}

export interface INewProject {
  name:string;
}

export interface IProjectDetail {
  prjInfo: IProject;
  owner: IMember;
  members: IMember[];
}
