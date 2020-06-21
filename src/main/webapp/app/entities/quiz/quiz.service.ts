import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { IQuiz } from 'app/shared/model/quiz.model';

type EntityResponseType = HttpResponse<IQuiz>;

@Injectable({ providedIn: 'root' })
export class QuizService {
  public resourceUrl = SERVER_API_URL + 'api/quiz';

  constructor(protected http: HttpClient) {}

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IQuiz>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

}
