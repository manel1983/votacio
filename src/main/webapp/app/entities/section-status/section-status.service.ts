import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ISectionStatus } from 'app/shared/model/section-status.model';

type EntityResponseType = HttpResponse<ISectionStatus>;
type EntityArrayResponseType = HttpResponse<ISectionStatus[]>;

@Injectable({ providedIn: 'root' })
export class SectionStatusService {
  public resourceUrl = SERVER_API_URL + 'api/section-statuses';

  constructor(protected http: HttpClient) {}

  create(sectionStatus: ISectionStatus): Observable<EntityResponseType> {
    return this.http.post<ISectionStatus>(this.resourceUrl, sectionStatus, { observe: 'response' });
  }

  update(sectionStatus: ISectionStatus): Observable<EntityResponseType> {
    return this.http.put<ISectionStatus>(this.resourceUrl, sectionStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISectionStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISectionStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
