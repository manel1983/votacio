import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IQuestionStatus } from 'app/shared/model/question-status.model';

type EntityResponseType = HttpResponse<IQuestionStatus>;
type EntityArrayResponseType = HttpResponse<IQuestionStatus[]>;

@Injectable({ providedIn: 'root' })
export class QuestionStatusService {
  public resourceUrl = SERVER_API_URL + 'api/question-statuses';

  constructor(protected http: HttpClient) {}

  create(questionStatus: IQuestionStatus): Observable<EntityResponseType> {
    return this.http.post<IQuestionStatus>(this.resourceUrl, questionStatus, { observe: 'response' });
  }

  update(questionStatus: IQuestionStatus): Observable<EntityResponseType> {
    return this.http.put<IQuestionStatus>(this.resourceUrl, questionStatus, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuestionStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IQuestionStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
