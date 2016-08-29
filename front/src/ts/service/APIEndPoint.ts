/// <reference path="../../../typings/tsd.d.ts"/>

import IResourceService = ng.resource.IResourceService;
import {Constants} from '../config/Constants';


export class APIEndPoint {
  constructor(
    private $resource:IResourceService
  ) {}

  public get inviteResource() {
    return this.$resource(Constants.apiPref + '/invite');
  }
  public get myinfoResource() {
    return this.$resource(Constants.apiPref + '/myinfo');
  }
  public get signinResource() {
    return this.$resource(Constants.apiPref + '/signin');
  }
  public get signupResource() {
    return this.$resource(Constants.apiPref + '/signup');
  }
  public get projectResource() {
    return this.$resource(Constants.apiPref + '/project/:prjId', {prjId: '@prjId'});
  }
  public get sitemapResource() {
    return this.$resource(Constants.apiPref + '/sitemap/:siteMapId', {siteMapId: '@siteMapId'});
  }


  public static factory(
    $resource:IResourceService
  ):APIEndPoint {
    return new APIEndPoint($resource);
  }
}
