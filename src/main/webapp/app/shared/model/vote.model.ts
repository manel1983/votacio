import { IQuestion } from 'app/shared/model/question.model';
import { IAnswer } from 'app/shared/model/answer.model';

export interface IVote {
  id?: number;
  userId?: number;
  status?: number;
  question?: IQuestion;
  answer?: IAnswer;
}

export class Vote implements IVote {
  constructor(public id?: number, public userId?: number, public status?: number, public question?: IQuestion, public answer?: IAnswer) {}
}
