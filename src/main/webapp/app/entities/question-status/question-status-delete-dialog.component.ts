import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuestionStatus } from 'app/shared/model/question-status.model';
import { QuestionStatusService } from './question-status.service';

@Component({
  templateUrl: './question-status-delete-dialog.component.html',
})
export class QuestionStatusDeleteDialogComponent {
  questionStatus?: IQuestionStatus;

  constructor(
    protected questionStatusService: QuestionStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.questionStatusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('questionStatusListModification');
      this.activeModal.close();
    });
  }
}
