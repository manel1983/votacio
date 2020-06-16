import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VotacioSharedModule } from 'app/shared/shared.module';
import { AnswerStatusComponent } from './answer-status.component';
import { AnswerStatusDetailComponent } from './answer-status-detail.component';
import { AnswerStatusUpdateComponent } from './answer-status-update.component';
import { AnswerStatusDeleteDialogComponent } from './answer-status-delete-dialog.component';
import { answerStatusRoute } from './answer-status.route';

@NgModule({
  imports: [VotacioSharedModule, RouterModule.forChild(answerStatusRoute)],
  declarations: [AnswerStatusComponent, AnswerStatusDetailComponent, AnswerStatusUpdateComponent, AnswerStatusDeleteDialogComponent],
  entryComponents: [AnswerStatusDeleteDialogComponent],
})
export class VotacioAnswerStatusModule {}
