import {AuthService} from "../service/AuthService";
import {MyInfoResource} from '../resource/MyInfoResource';
import {IMyInfoResp} from "../model/IMyInfoResp";

export function MyInfoFactory(myInfoResource:MyInfoResource){
  return myInfoResource.get(
    function(resp:IMyInfoResp): IMyInfoResp {
      if(resp.success) {
        console.log(resp);
        return resp;
      } else {
        console.log('failed to get myInfo');
      }
    },
    function(e:any) {
      console.log(e);
    }
  );
}
