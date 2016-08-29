export type AlertType = "danger" | "warning" | "info" | "success"

export interface IAlert {
  type:AlertType;
  msg:string;
}

export class MsgControllerBase {
  alerts:IAlert[]  = [];
  sending = false;

  hasMessage():boolean {
    return this.alerts && this.alerts.length > 0;
  }
  
  protected begin():void {
    this.alerts = [];
    this.sending = true;
  }
  protected finish():void {
    this.sending = false;
  }
  protected pushMessage(msg:string) {
    this.alerts.push({
      type:'success',
      msg:msg
    });
  }
  protected pushError(msg:string) {
    this.alerts.push({
      type:'warning',
      msg:msg
    });
  };
  protected pushSysError() {
    this.alerts.push(
      {
        type:'danger',
        msg:'システムエラーが発生しました。しばらく時間をおいて再度お試しください。'
      }
    );
  }
}
