import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VotacioSharedModule } from 'app/shared/shared.module';
import { SectionStatusComponent } from './section-status.component';
import { SectionStatusDetailComponent } from './section-status-detail.component';
import { SectionStatusUpdateComponent } from './section-status-update.component';
import { SectionStatusDeleteDialogComponent } from './section-status-delete-dialog.component';
import { sectionStatusRoute } from './section-status.route';

@NgModule({
  imports: [VotacioSharedModule, RouterModule.forChild(sectionStatusRoute)],
  declarations: [SectionStatusComponent, SectionStatusDetailComponent, SectionStatusUpdateComponent, SectionStatusDeleteDialogComponent],
  entryComponents: [SectionStatusDeleteDialogComponent],
})
export class VotacioSectionStatusModule {}
