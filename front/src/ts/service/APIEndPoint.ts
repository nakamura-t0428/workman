/// <reference path="../../../typings/tsd.d.ts"/>

import IResourceService = ng.resource.IResourceService;
import {ConfigService} from '../service/ConfigService';


export class APIEndPoint {
  constructor(
    private config:ConfigService,
    private $resource:IResourceService
  ) {}

  public get inviteResource() {
    return this.$resource(this.config.apiPref + '/invite');
  }
  public get myinfoResource() {
    return this.$resource(this.config.apiPref + '/myinfo');
  }
  public get signinResource() {
    return this.$resource(this.config.apiPref + '/signin');
  }
  public get signupResource() {
    return this.$resource(this.config.apiPref + '/signup');
  }
  public get projectResource() {
    return this.$resource(this.config.apiPref + '/project/:prjId', {prjId: '@prjId'});
  }
  public get sitemapResource() {
    return this.$resource(this.config.apiPref + '/sitemap/:siteMapId', {siteMapId: '@siteMapId'});
  }


  public static factory(
    config:ConfigService,
    $resource:IResourceService
  ):APIEndPoint {
    return new APIEndPoint(config, $resource);
  }
}
