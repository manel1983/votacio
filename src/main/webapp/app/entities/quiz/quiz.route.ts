import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuiz, Quiz } from 'app/shared/model/quiz.model';
import { QuizService } from './quiz.service';
import { QuizComponent } from './quiz.component';

@Injectable({ providedIn: 'root' })
export class QuizResolve implements Resolve<IQuiz> {
  constructor(private service: QuizService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuiz> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((vote: HttpResponse<Quiz>) => {
          if (vote.body) {
            return of(vote.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Quiz());
  }
}

export const quizRoute: Routes = [
  {
    path: '',
    component: QuizComponent,
    data: {
      authorities: [Authority.USER],
      defaultSort: 'id,asc',
      pageTitle: 'votacioApp.quiz.home.title',
    },
    canActivate: [UserRouteAccessService],
  }
];
