export interface ICompany{
  compId: string;
  name: string;
  description: string;
}

export interface ICompanyQueryParam{
  name: string;
  limit: number;
  page: number;
}

export interface INewCompany{
  name: string;
  description: string;
}

export class EmptyCompany implements INewCompany{
  name = "";
  description = ""
}