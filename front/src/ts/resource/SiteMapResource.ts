/// <reference path="../../../typings/tsd.d.ts"/>

import IResource = ng.resource.IResource;
import IResourceClass = ng.resource.IResourceClass
import IResourceService = ng.resource.IResourceService;
import {ConfigService} from '../service/ConfigService';
import {ISiteMap} from '../model/ISiteMapData';

export interface ISiteMapResource extends ISiteMap,IResource<ISiteMap> {}
export type SiteMapResource = IResourceClass<ISiteMapResource>
export function SiteMapResourceFactory(config:ConfigService, $resource:IResourceService) {
  return $resource<ISiteMapResource>(config.apiPref + '/sitemap/:siteMapId', {siteMapId: '@siteMapId'});
}
