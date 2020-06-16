export interface ISectionStatus {
  id?: number;
  name?: string;
  description?: string;
}

export class SectionStatus implements ISectionStatus {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
