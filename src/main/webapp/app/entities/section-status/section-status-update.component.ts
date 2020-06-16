import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISectionStatus, SectionStatus } from 'app/shared/model/section-status.model';
import { SectionStatusService } from './section-status.service';

@Component({
  selector: 'jhi-section-status-update',
  templateUrl: './section-status-update.component.html',
})
export class SectionStatusUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    description: [],
  });

  constructor(protected sectionStatusService: SectionStatusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sectionStatus }) => {
      this.updateForm(sectionStatus);
    });
  }

  updateForm(sectionStatus: ISectionStatus): void {
    this.editForm.patchValue({
      id: sectionStatus.id,
      name: sectionStatus.name,
      description: sectionStatus.description,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sectionStatus = this.createFromForm();
    if (sectionStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.sectionStatusService.update(sectionStatus));
    } else {
      this.subscribeToSaveResponse(this.sectionStatusService.create(sectionStatus));
    }
  }

  private createFromForm(): ISectionStatus {
    return {
      ...new SectionStatus(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISectionStatus>>): void {
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
