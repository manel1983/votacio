import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISection, Section } from 'app/shared/model/section.model';
import { SectionService } from './section.service';
import { ISectionStatus } from 'app/shared/model/section-status.model';
import { SectionStatusService } from 'app/entities/section-status/section-status.service';

@Component({
  selector: 'jhi-section-update',
  templateUrl: './section-update.component.html',
})
export class SectionUpdateComponent implements OnInit {
  isSaving = false;
  sectionstatuses: ISectionStatus[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    status: [null, [Validators.required]],
  });

  constructor(
    protected sectionService: SectionService,
    protected sectionStatusService: SectionStatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ section }) => {
      this.updateForm(section);

      this.sectionStatusService.query().subscribe((res: HttpResponse<ISectionStatus[]>) => (this.sectionstatuses = res.body || []));
    });
  }

  updateForm(section: ISection): void {
    this.editForm.patchValue({
      id: section.id,
      name: section.name,
      status: section.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const section = this.createFromForm();
    if (section.id !== undefined) {
      this.subscribeToSaveResponse(this.sectionService.update(section));
    } else {
      this.subscribeToSaveResponse(this.sectionService.create(section));
    }
  }

  private createFromForm(): ISection {
    return {
      ...new Section(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISection>>): void {
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

  trackById(index: number, item: ISectionStatus): any {
    return item.id;
  }
}
