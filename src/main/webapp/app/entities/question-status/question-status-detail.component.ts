import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IQuestionStatus } from 'app/shared/model/question-status.model';

@Component({
  selector: 'jhi-question-status-detail',
  templateUrl: './question-status-detail.component.html',
})
export class QuestionStatusDetailComponent implements OnInit {
  questionStatus: IQuestionStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionStatus }) => (this.questionStatus = questionStatus));
  }

  previousState(): void {
    window.history.back();
  }
}
