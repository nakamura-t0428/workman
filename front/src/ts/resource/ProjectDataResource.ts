/// <reference path="../../../typings/tsd.d.ts"/>

import IResource = ng.resource.IResource;
import IResourceClass = ng.resource.IResourceClass
import IResourceService = ng.resource.IResourceService;
import {IProject} from '../model/IProjectData'
import {ConfigService} from '../service/ConfigService';

export interface IProjectDataResource extends IProject,IResource<IProject> {}
export type ProjectDataResource = IResourceClass<IProjectDataResource>
export function ProjectDataResourceFactory(config:ConfigService, $resource:IResourceService) {
  return $resource<IProjectDataResource>(config.apiPref + '/project/:prjId', {prjId: '@prjId'});
}
