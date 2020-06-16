import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IQuestionStatus, QuestionStatus } from 'app/shared/model/question-status.model';
import { QuestionStatusService } from './question-status.service';
import { QuestionStatusComponent } from './question-status.component';
import { QuestionStatusDetailComponent } from './question-status-detail.component';
import { QuestionStatusUpdateComponent } from './question-status-update.component';

@Injectable({ providedIn: 'root' })
export class QuestionStatusResolve implements Resolve<IQuestionStatus> {
  constructor(private service: QuestionStatusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IQuestionStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((questionStatus: HttpResponse<QuestionStatus>) => {
          if (questionStatus.body) {
            return of(questionStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new QuestionStatus());
  }
}

export const questionStatusRoute: Routes = [
  {
    path: '',
    component: QuestionStatusComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'votacioApp.questionStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: QuestionStatusDetailComponent,
    resolve: {
      questionStatus: QuestionStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'votacioApp.questionStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: QuestionStatusUpdateComponent,
    resolve: {
      questionStatus: QuestionStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'votacioApp.questionStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: QuestionStatusUpdateComponent,
    resolve: {
      questionStatus: QuestionStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'votacioApp.questionStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
