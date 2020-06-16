import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IVote, Vote } from 'app/shared/model/vote.model';
import { VoteService } from './vote.service';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from 'app/entities/question/question.service';
import { IAnswer } from 'app/shared/model/answer.model';
import { AnswerService } from 'app/entities/answer/answer.service';

type SelectableEntity = IQuestion | IAnswer;

@Component({
  selector: 'jhi-vote-update',
  templateUrl: './vote-update.component.html',
})
export class VoteUpdateComponent implements OnInit {
  isSaving = false;
  questions: IQuestion[] = [];
  answers: IAnswer[] = [];

  editForm = this.fb.group({
    id: [],
    userId: [null, [Validators.required]],
    questionId: [null, [Validators.required]],
    answerId: [null, [Validators.required]],
    status: [null, [Validators.required]],
    question: [],
    answer: [],
  });

  constructor(
    protected voteService: VoteService,
    protected questionService: QuestionService,
    protected answerService: AnswerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ vote }) => {
      this.updateForm(vote);

      this.questionService.query().subscribe((res: HttpResponse<IQuestion[]>) => (this.questions = res.body || []));

      this.answerService.query().subscribe((res: HttpResponse<IAnswer[]>) => (this.answers = res.body || []));
    });
  }

  updateForm(vote: IVote): void {
    this.editForm.patchValue({
      id: vote.id,
      userId: vote.userId,
      question: vote.question,
      answer: vote.answer,
      status: vote.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const vote = this.createFromForm();
    if (vote.id !== undefined) {
      this.subscribeToSaveResponse(this.voteService.update(vote));
    } else {
      this.subscribeToSaveResponse(this.voteService.create(vote));
    }
  }

  private createFromForm(): IVote {
    return {
      ...new Vote(),
      id: this.editForm.get(['id'])!.value,
      userId: this.editForm.get(['userId'])!.value,
      question: this.editForm.get(['question'])!.value,
      answer: this.editForm.get(['answer'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVote>>): void {
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
