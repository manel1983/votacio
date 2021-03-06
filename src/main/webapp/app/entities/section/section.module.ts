import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VotacioSharedModule } from 'app/shared/shared.module';
import { SectionComponent } from './section.component';
import { SectionDetailComponent } from './section-detail.component';
import { SectionUpdateComponent } from './section-update.component';
import { SectionDeleteDialogComponent } from './section-delete-dialog.component';
import { sectionRoute } from './section.route';

@NgModule({
  imports: [VotacioSharedModule, RouterModule.forChild(sectionRoute)],
  declarations: [SectionComponent, SectionDetailComponent, SectionUpdateComponent, SectionDeleteDialogComponent],
  entryComponents: [SectionDeleteDialogComponent],
})
export class VotacioSectionModule {}
