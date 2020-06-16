import { ISectionStatus } from 'app/shared/model/section-status.model';

export interface ISection {
  id?: number;
  name?: string;
  status?: ISectionStatus;
}

export class Section implements ISection {
  constructor(public id?: number, public name?: string, public status?: ISectionStatus) {}
}
