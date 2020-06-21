export interface IUser {
  id?: any;
  login?: string;
  firstName?: string;
  surname1?: string;
  surname2?: string;
  nifnie?: string;
  email?: string;
  activated?: boolean;
  validated?: boolean;
  langKey?: string;
  authorities?: string[];
  createdBy?: string;
  createdDate?: Date;
  lastModifiedBy?: string;
  lastModifiedDate?: Date;
  password?: string;
}

export class User implements IUser {
  constructor(
    public id?: any,
    public login?: string,
    public firstName?: string,
    public surname1?: string,
    public surname2?: string,
    public nifnie?: string,
    public email?: string,
    public activated?: boolean,
    public validated?: boolean,
    public langKey?: string,
    public authorities?: string[],
    public createdBy?: string,
    public createdDate?: Date,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Date,
    public password?: string
  ) {}
}
