import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'section-status',
        loadChildren: () => import('./section-status/section-status.module').then(m => m.VotacioSectionStatusModule),
      },
      {
        path: 'question-status',
        loadChildren: () => import('./question-status/question-status.module').then(m => m.VotacioQuestionStatusModule),
      },
      {
        path: 'answer-status',
        loadChildren: () => import('./answer-status/answer-status.module').then(m => m.VotacioAnswerStatusModule),
      },
      {
        path: 'section',
        loadChildren: () => import('./section/section.module').then(m => m.VotacioSectionModule),
      },
      {
        path: 'question',
        loadChildren: () => import('./question/question.module').then(m => m.VotacioQuestionModule),
      },
      {
        path: 'answer',
        loadChildren: () => import('./answer/answer.module').then(m => m.VotacioAnswerModule),
      },
      {
        path: 'vote',
        loadChildren: () => import('./vote/vote.module').then(m => m.VotacioVoteModule),
      },
      {
        path: 'quiz',
        loadChildren: () => import('./quiz/quiz.module').then(m => m.VotacioQuizModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class VotacioEntityModule {}
