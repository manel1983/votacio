export interface IAnswerStatus {
  id?: number;
  name?: string;
  description?: string;
}

export class AnswerStatus implements IAnswerStatus {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
