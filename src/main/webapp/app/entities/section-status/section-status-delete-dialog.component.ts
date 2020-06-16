import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISectionStatus } from 'app/shared/model/section-status.model';
import { SectionStatusService } from './section-status.service';

@Component({
  templateUrl: './section-status-delete-dialog.component.html',
})
export class SectionStatusDeleteDialogComponent {
  sectionStatus?: ISectionStatus;

  constructor(
    protected sectionStatusService: SectionStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sectionStatusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('sectionStatusListModification');
      this.activeModal.close();
    });
  }
}
