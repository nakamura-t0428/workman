/// <reference path="../../../typings/tsd.d.ts"/>

import IResource = ng.resource.IResource;
import IResourceClass = ng.resource.IResourceClass

import {IBaseRespData} from '../model/IBaseRespData'
import {MsgControllerBase} from './MsgControllerBase';


export abstract class PostControllerBase<A, B extends IResourceClass<A & IResource<A>>, R extends IBaseRespData> extends MsgControllerBase {
  data: A;
  
  abstract onSubmitSuccess(resp:R, r:any):void;
  onError(resp:R, r:any):boolean {
    return false;
  };
  onSystemError(e:any):boolean {
    return false;
  };
  validate():boolean {
    return true;
  }
  
  constructor(
    private dataResource: B
  ) {
    super();
  }
  
  submit():void {
    super.begin();
    if(!this.validate()) {
      super.finish();
      return;
    }
    let resource = new this.dataResource(this.data);
    resource.$save((resp:R, r:any) => {
      if(resp.success) {
        this.onSubmitSuccess(resp, r);
      } else {
        if(!this.onError(resp, r)) {
          super.pushError(resp.msg);
        }
      }
      super.finish();
    }, (e:any) => {
      if(!this.onSystemError(e)) {
        super.pushSysError();
      }
      super.finish();
    });
  }
}
