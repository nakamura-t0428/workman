export interface ISiteMap {
  name: string;
  siteMapId: string;
}

export interface INewSiteMap {
  name:string;
}

export interface ISiteRequestParamName {
  name:string;
  selector:string;
}

export interface ISiteURL {
  protocol:string;
  host:string;
  path:string;
  port:string;
}

export interface ISiteRequest {
  url:ISiteURL;
  method:string;
  urlParams:ISiteRequestParamName[];
  bodyParams:ISiteRequestParamName[];
}

export interface ISitePage {
  name:string;
  requests:ISiteRequest[];
  wait:number;
}

export interface ISiteMapDetail {
  siteMap: ISiteMap;
  pages:ISiteMap;
}
