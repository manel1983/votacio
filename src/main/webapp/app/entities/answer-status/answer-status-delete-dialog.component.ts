import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAnswerStatus } from 'app/shared/model/answer-status.model';
import { AnswerStatusService } from './answer-status.service';

@Component({
  templateUrl: './answer-status-delete-dialog.component.html',
})
export class AnswerStatusDeleteDialogComponent {
  answerStatus?: IAnswerStatus;

  constructor(
    protected answerStatusService: AnswerStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.answerStatusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('answerStatusListModification');
      this.activeModal.close();
    });
  }
}
