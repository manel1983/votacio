import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ISectionStatus, SectionStatus } from 'app/shared/model/section-status.model';
import { SectionStatusService } from './section-status.service';
import { SectionStatusComponent } from './section-status.component';
import { SectionStatusDetailComponent } from './section-status-detail.component';
import { SectionStatusUpdateComponent } from './section-status-update.component';

@Injectable({ providedIn: 'root' })
export class SectionStatusResolve implements Resolve<ISectionStatus> {
  constructor(private service: SectionStatusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISectionStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((sectionStatus: HttpResponse<SectionStatus>) => {
          if (sectionStatus.body) {
            return of(sectionStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new SectionStatus());
  }
}

export const sectionStatusRoute: Routes = [
  {
    path: '',
    component: SectionStatusComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'votacioApp.sectionStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SectionStatusDetailComponent,
    resolve: {
      sectionStatus: SectionStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'votacioApp.sectionStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SectionStatusUpdateComponent,
    resolve: {
      sectionStatus: SectionStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'votacioApp.sectionStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SectionStatusUpdateComponent,
    resolve: {
      sectionStatus: SectionStatusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'votacioApp.sectionStatus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
