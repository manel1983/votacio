import { ISection } from 'app/shared/model/section.model';

export interface IQuiz {
  id?: number;
  sections?: ISection[];
}

export class Quiz implements IQuiz {
  constructor(public id?: number, public sections?: ISection[]) {}
}
