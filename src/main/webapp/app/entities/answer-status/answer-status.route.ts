import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAnswerStatus, AnswerStatus } from 'app/shared/model/answer-status.model';
import { AnswerStatusService } from './answer-status.service';
import { AnswerStatusComponent } from './answer-status.component';
import { AnswerStatusDetailComponent } from './answer-status-detail.component';
import { AnswerStatusUpdateComponent } from './answer-status-update.component';

@Injectable({ providedIn: 'root' })
export class AnswerStatusResolve implements Resolve<IAnswerStatus> {
  constructor(private service: AnswerStatusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAnswerStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((answerStatus: HttpResponse<AnswerStatus>) => {
          if (answerStatus.body) {
            return of(answerStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new AnswerStatus());
  }
}

export const answerStatusRoute: Routes = [
  {
    path: '',
    component: AnswerStatusComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'votacioApp.answerStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnswerStatusDetailComponent,
    resolve: {
      answerStatus: AnswerStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'votacioApp.answerStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnswerStatusUpdateComponent,
    resolve: {
      answerStatus: AnswerStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'votacioApp.answerStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnswerStatusUpdateComponent,
    resolve: {
      answerStatus: AnswerStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'votacioApp.answerStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
