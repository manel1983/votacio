import { IQuestionStatus } from 'app/shared/model/question-status.model';
import { ISection } from 'app/shared/model/section.model';

export interface IQuestion {
  id?: number;
  text?: string;
  status?: IQuestionStatus;
  section?: ISection;
}

export class Question implements IQuestion {
  constructor(public id?: number, public text?: string, public status?: IQuestionStatus, public section?: ISection) {}
}
