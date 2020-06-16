import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAnswerStatus } from 'app/shared/model/answer-status.model';

type EntityResponseType = HttpResponse<IAnswerStatus>;
type EntityArrayResponseType = HttpResponse<IAnswerStatus[]>;

@Injectable({ providedIn: 'root' })
export class AnswerStatusService {
  public resourceUrl = SERVER_API_URL + 'api/answer-statuses';

  constructor(protected http: HttpClient) {}

  create(answerStatus: IAnswerStatus): Observable<EntityResponseType> {
    return this.http.post<IAnswerStatus>(this.resourceUrl, answerStatus, { observe: 'response' });
  }

  update(answerStatus: IAnswerStatus): Observable<EntityResponseType> {
    return this.http.put<IAnswerStatus>(this.resourceUrl, answerStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnswerStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnswerStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
