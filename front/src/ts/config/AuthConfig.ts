/// <reference path="../../../typings/tsd.d.ts"/>

import IHttpProvider = angular.IHttpProvider;
import IQService = angular.IQService;
import IStorageService = angular.storage.IStorageService;
import IStateService = angular.ui.IStateService;
import {Constants} from "./Constants";

export class AuthConfig{
  constructor(
    $httpProvider:IHttpProvider
  ) {
    $httpProvider.interceptors.push(['$q', '$localStorage',
      ($q, $localStorage) => new AuthRequestIntercepter($q, $localStorage)]);
  }

  public static factory(
    $httpProvider:IHttpProvider
  ):AuthConfig {
    return new AuthConfig($httpProvider);
  }
}
interface AuthLocalStorage extends IStorageService {
  authToken: string;
}
class AuthRequestIntercepter {
  private storage:AuthLocalStorage;
  constructor(
    private $q:angular.IQService,
    private $localStorage: IStorageService
   ) {
     this.storage = <AuthLocalStorage> this.$localStorage.$default({
       authToken: undefined
     });
  }
  public request = (config:angular.IRequestConfig) => {
    if(config.url.indexOf(Constants.apiPref)==0) {
      config.headers = config.headers || {};
      console.log(this.storage.authToken);
      if (this.storage.authToken) {
        config.headers['Authorization'] = 'Bearer ' + this.storage.authToken;
      }
    }
    return config;
  }
  public response = (response:angular.IHttpPromiseCallbackArg<any>) => {
    if(response.config.url.indexOf(Constants.apiPref)==0 && response.headers('Auth-Token')) {
      this.storage.authToken = response.headers('Auth-Token');
      console.log('authToken updated.');
    } else if (response.status == 403 && response.data['errId'] == 'authorization_error') {
      this.storage.authToken = undefined;
      console.log('authToken deleted by AuthError.');
      // this.$window.location.href = '/';
    }
    return response;
  }
  public responseError = (response:angular.IHttpPromiseCallbackArg<any>) => {
    if (response.status === 401 || response.status === 403) {
      this.storage.authToken = undefined;
      console.log('authToken deleted by ResponseError.');
      // this.$window.location.href = '/';
    }
    return this.$q.reject(response);
  }
}
