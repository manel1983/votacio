import { IAnswerStatus } from 'app/shared/model/answer-status.model';
import { IQuestion } from 'app/shared/model/question.model';

export interface IAnswer {
  id?: number;
  text?: string;
  status?: IAnswerStatus;
  question?: IQuestion;
}

export class Answer implements IAnswer {
  constructor(public id?: number, public text?: string, public status?: IAnswerStatus, public question?: IQuestion) {}
}
