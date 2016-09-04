/// <reference path="../../../typings/tsd.d.ts"/>
import IDirective = ng.IDirective;
import IRootScopeService = angular.IRootScopeService;
import IAngularEvent = angular.IAngularEvent;
import IStateService = angular.ui.IStateService;
import {ITopMenu} from '../model/ITopMenu';

export class TopMenuDirective implements IDirective {
  topMenu:Array<ITopMenu> = [];

  constructor(private $rootScope:IRootScopeService, public $state:IStateService) {
    function s(t:any) {
      return angular.isFunction(t) ? t() : t;
    }
    this.topMenu = s($state.$current.locals.globals['topMenu']);
    $rootScope.$on('$stateChangeSuccess', (ev:IAngularEvent, st1:any) => {
      var state = $state.current;
      this.topMenu = s($state.$current.locals.globals['topMenu']);
    });
  }

  public static factory = ['$rootScope', '$state', ($rootScope:IRootScopeService, $state:IStateService) => {
    return {
      restrict: 'E',
      replace: true,
      transclude: true,
      template: `<li ng-repeat="menu in c.topMenu" ui-sref-active="active" >
        <a ui-sref="{{menu.state}}">{{menu.label}}</a>
      </li>`,
      controller: ['$rootScope', '$state', TopMenuDirective],
      controllerAs: 'c',
      scope: true
    }
  }]

}