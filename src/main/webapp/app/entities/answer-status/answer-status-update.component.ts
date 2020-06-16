import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAnswerStatus, AnswerStatus } from 'app/shared/model/answer-status.model';
import { AnswerStatusService } from './answer-status.service';

@Component({
  selector: 'jhi-answer-status-update',
  templateUrl: './answer-status-update.component.html',
})
export class AnswerStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
  });

  constructor(protected answerStatusService: AnswerStatusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ answerStatus }) => {
      this.updateForm(answerStatus);
    });
  }

  updateForm(answerStatus: IAnswerStatus): void {
    this.editForm.patchValue({
      id: answerStatus.id,
      name: answerStatus.name,
      description: answerStatus.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const answerStatus = this.createFromForm();
    if (answerStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.answerStatusService.update(answerStatus));
    } else {
      this.subscribeToSaveResponse(this.answerStatusService.create(answerStatus));
    }
  }

  private createFromForm(): IAnswerStatus {
    return {
      ...new AnswerStatus(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnswerStatus>>): void {
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
