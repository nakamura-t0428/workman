/// <reference path="../../../typings/tsd.d.ts"/>

import IResource = ng.resource.IResource;
import IResourceClass = ng.resource.IResourceClass
import IResourceService = ng.resource.IResourceService;
import {ConfigService} from '../service/ConfigService';

export interface IMyInfoResource extends IResource<IMyInfoResource> {}
export type MyInfoResource = IResourceClass<IMyInfoResource>
export function MyInfoResourceFactory(config:ConfigService, $resource:IResourceService) {
  return $resource<IMyInfoResource>(config.apiPref + '/myinfo');
}
