import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { VotacioSharedModule } from 'app/shared/shared.module';
import { QuizComponent } from './quiz.component';
import { quizRoute } from './quiz.route';

@NgModule({
  imports: [VotacioSharedModule, RouterModule.forChild(quizRoute)],
  declarations: [QuizComponent],
  entryComponents: [],
})
export class VotacioQuizModule {}
