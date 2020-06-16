import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IQuestion, Question } from 'app/shared/model/question.model';
import { QuestionService } from './question.service';
import { IQuestionStatus } from 'app/shared/model/question-status.model';
import { QuestionStatusService } from 'app/entities/question-status/question-status.service';
import { ISection } from 'app/shared/model/section.model';
import { SectionService } from 'app/entities/section/section.service';

type SelectableEntity = IQuestionStatus | ISection;

@Component({
  selector: 'jhi-question-update',
  templateUrl: './question-update.component.html',
})
export class QuestionUpdateComponent implements OnInit {
  isSaving = false;
  questionstatuses: IQuestionStatus[] = [];
  sections: ISection[] = [];

  editForm = this.fb.group({
    id: [],
    text: [null, [Validators.required]],
    section: [null, [Validators.required]],
    status: [null, [Validators.required]],
  });

  constructor(
    protected questionService: QuestionService,
    protected questionStatusService: QuestionStatusService,
    protected sectionService: SectionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ question }) => {
      this.updateForm(question);

      this.questionStatusService.query().subscribe((res: HttpResponse<IQuestionStatus[]>) => (this.questionstatuses = res.body || []));

      this.sectionService.query().subscribe((res: HttpResponse<ISection[]>) => (this.sections = res.body || []));
    });
  }

  updateForm(question: IQuestion): void {
    this.editForm.patchValue({
      id: question.id,
      text: question.text,
      section: question.section,
      status: question.status,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const question = this.createFromForm();
    if (question.id !== undefined) {
      this.subscribeToSaveResponse(this.questionService.update(question));
    } else {
      this.subscribeToSaveResponse(this.questionService.create(question));
    }
  }

  private createFromForm(): IQuestion {
    return {
      ...new Question(),
      id: this.editForm.get(['id'])!.value,
      text: this.editForm.get(['text'])!.value,
      section: this.editForm.get(['section'])!.value,
      status: this.editForm.get(['status'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IQuestion>>): void {
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
