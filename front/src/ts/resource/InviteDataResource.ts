/// <reference path="../../../typings/tsd.d.ts"/>

import IResource = ng.resource.IResource;
import IResourceClass = ng.resource.IResourceClass
import IResourceService = ng.resource.IResourceService;
import {IInviteData} from '../model/IInviteData'
import {ConfigService} from '../service/ConfigService';

export interface IInviteDataResource extends IInviteData,IResource<IInviteData> {}
export type InviteDataResource = IResourceClass<IInviteDataResource>
export function InviteDataResourceFactory(config:ConfigService, $resource:IResourceService) {
  return $resource<IInviteDataResource>(config.apiPref + '/invite');
}
