import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuestionStatus, QuestionStatus } from 'app/shared/model/question-status.model';
import { QuestionStatusService } from './question-status.service';

@Component({
  selector: 'jhi-question-status-update',
  templateUrl: './question-status-update.component.html',
})
export class QuestionStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
  });

  constructor(protected questionStatusService: QuestionStatusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ questionStatus }) => {
      this.updateForm(questionStatus);
    });
  }

  updateForm(questionStatus: IQuestionStatus): void {
    this.editForm.patchValue({
      id: questionStatus.id,
      name: questionStatus.name,
      description: questionStatus.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const questionStatus = this.createFromForm();
    if (questionStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.questionStatusService.update(questionStatus));
    } else {
      this.subscribeToSaveResponse(this.questionStatusService.create(questionStatus));
    }
  }

  private createFromForm(): IQuestionStatus {
    return {
      ...new QuestionStatus(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestionStatus>>): void {
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
}
