/// <reference path="../../../typings/tsd.d.ts"/>
import IDirective = ng.IDirective;
import IRootScopeService = angular.IRootScopeService;
import IAngularEvent = angular.IAngularEvent;
import IStateService = angular.ui.IStateService;
import IStorageService = angular.storage.IStorageService;
import {ITopMenu} from '../model/ITopMenu';

interface LocState {
  loc:ITopMenu;
}

export class StateLocDirective implements IDirective {
  public loc:ITopMenu;

  constructor(private $rootScope:IRootScopeService, public $state:IStateService) {
    function s(t:any) {
      return angular.isFunction(t) ? t() : t;
    }
    this.loc = s($state.$current.locals.globals['loc']);
    $rootScope.$on('$stateChangeSuccess', (ev:IAngularEvent, st1:any) => {
      var state = $state.current;
      this.loc = s($state.$current.locals.globals['loc']);
    });
  }

  public static factory = ['$rootScope', '$state', ($rootScope:IRootScopeService, $state:IStateService) => {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      template: '<a state-loc class="navbar-brand topnav" href="{{c.$state.href(c.loc.state, c.loc.stateParams)}}">{{c.loc.label}}</a>',
      controller: ['$rootScope', '$state', StateLocDirective],
      controllerAs: 'c',
      scope: true
    }
  }]
}