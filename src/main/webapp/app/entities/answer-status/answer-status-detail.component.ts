import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnswerStatus } from 'app/shared/model/answer-status.model';

@Component({
  selector: 'jhi-answer-status-detail',
  templateUrl: './answer-status-detail.component.html',
})
export class AnswerStatusDetailComponent implements OnInit {
  answerStatus: IAnswerStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ answerStatus }) => (this.answerStatus = answerStatus));
  }

  previousState(): void {
    window.history.back();
  }
}
