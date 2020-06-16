import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAnswer, Answer } from 'app/shared/model/answer.model';
import { AnswerService } from './answer.service';
import { IAnswerStatus } from 'app/shared/model/answer-status.model';
import { AnswerStatusService } from 'app/entities/answer-status/answer-status.service';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question/question.service';

type SelectableEntity = IAnswerStatus | IQuestion;

@Component({
  selector: 'jhi-answer-update',
  templateUrl: './answer-update.component.html',
})
export class AnswerUpdateComponent implements OnInit {
  isSaving = false;
  answerstatuses: IAnswerStatus[] = [];
  questions: IQuestion[] = [];

  editForm = this.fb.group({
    id: [],
    text: [null, [Validators.required]],
    question: [null, [Validators.required]],
    status: [null, [Validators.required]],
  });

  constructor(
    protected answerService: AnswerService,
    protected answerStatusService: AnswerStatusService,
    protected questionService: QuestionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ answer }) => {
      this.updateForm(answer);

      this.answerStatusService.query().subscribe((res: HttpResponse<IAnswerStatus[]>) => (this.answerstatuses = res.body || []));

      this.questionService.query().subscribe((res: HttpResponse<IQuestion[]>) => (this.questions = res.body || []));
    });
  }

  updateForm(answer: IAnswer): void {
    this.editForm.patchValue({
      id: answer.id,
      text: answer.text,
      question: answer.question,
      status: answer.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const answer = this.createFromForm();
    if (answer.id !== undefined) {
      this.subscribeToSaveResponse(this.answerService.update(answer));
    } else {
      this.subscribeToSaveResponse(this.answerService.create(answer));
    }
  }

  private createFromForm(): IAnswer {
    return {
      ...new Answer(),
      id: this.editForm.get(['id'])!.value,
      text: this.editForm.get(['text'])!.value,
      question: this.editForm.get(['question'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnswer>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
