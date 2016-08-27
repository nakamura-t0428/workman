/// <reference path="../../../typings/tsd.d.ts"/>

import IResource = ng.resource.IResource;
import IResourceClass = ng.resource.IResourceClass
import IResourceService = ng.resource.IResourceService;
import {ISignUpData} from '../model/ISignUpData'
import {ConfigService} from '../service/ConfigService';

interface ISignUpDataResource extends ISignUpData,IResource<ISignUpData> {}
export type SignUpDataResource = IResourceClass<ISignUpDataResource>
export function SignUpDataResourceFactory(config:ConfigService, $resource:IResourceService) {
  return $resource<ISignUpDataResource>(config.apiPref + '/signup');
}