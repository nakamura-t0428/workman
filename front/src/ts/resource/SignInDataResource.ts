/// <reference path="../../../typings/tsd.d.ts"/>

import IResource = ng.resource.IResource;
import IResourceClass = ng.resource.IResourceClass
import IResourceService = ng.resource.IResourceService;
import {ISignInData} from '../model/ISignInData'
import {ConfigService} from '../service/ConfigService';

export interface ISignInDataResource extends ISignInData, IResource<ISignInData>{}
export type SignInDataResource = IResourceClass<ISignInDataResource>
export function SignInDataResourceFactory(config:ConfigService, $resource:IResourceService) {
  return $resource<ISignInDataResource>(config.apiPref + '/signin');
}
