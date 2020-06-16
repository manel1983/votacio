export interface IQuestionStatus {
  id?: number;
  name?: string;
  description?: string;
}

export class QuestionStatus implements IQuestionStatus {
  constructor(public id?: number, public name?: string, public description?: string) {}
}
