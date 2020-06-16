import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VotacioSharedModule } from 'app/shared/shared.module';
import { QuestionStatusComponent } from './question-status.component';
import { QuestionStatusDetailComponent } from './question-status-detail.component';
import { QuestionStatusUpdateComponent } from './question-status-update.component';
import { QuestionStatusDeleteDialogComponent } from './question-status-delete-dialog.component';
import { questionStatusRoute } from './question-status.route';

@NgModule({
  imports: [VotacioSharedModule, RouterModule.forChild(questionStatusRoute)],
  declarations: [
    QuestionStatusComponent,
    QuestionStatusDetailComponent,
    QuestionStatusUpdateComponent,
    QuestionStatusDeleteDialogComponent,
  ],
  entryComponents: [QuestionStatusDeleteDialogComponent],
})
export class VotacioQuestionStatusModule {}
