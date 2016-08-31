/// <reference path="../../../typings/tsd.d.ts"/>

import IState = angular.ui.IState;
import {ITopMenu} from './ITopMenu';

export interface ILocState extends IState {
  loc: ITopMenu;
}