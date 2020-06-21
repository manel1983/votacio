import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IVote, Vote } from 'app/shared/model/vote.model';
import { Question } from 'app/shared/model/question.model';
import { Answer } from 'app/shared/model/answer.model';

type EntityResponseType = HttpResponse<IVote>;
type EntityArrayResponseType = HttpResponse<IVote[]>;

@Injectable({ providedIn: 'root' })
export class VoteService {
  public resourceUrl = SERVER_API_URL + 'api/votes';
  public resourceUrl2 = SERVER_API_URL + 'api/user_vote';

  constructor(protected http: HttpClient) {}

  create(vote: IVote): Observable<EntityResponseType> {
    return this.http.post<IVote>(this.resourceUrl, vote, { observe: 'response' });
  }

  vote(questionId: number, answerId: number, userId: number): Observable<EntityResponseType> {
    const vote: IVote = new Vote(undefined, userId, 1, new Question(questionId), new Answer(answerId));
    return this.http.post<IVote>(this.resourceUrl, vote, { observe: 'response' });
  }

  update(vote: IVote): Observable<EntityResponseType> {
    return this.http.put<IVote>(this.resourceUrl, vote, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IVote>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IVote[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  queryUserVote(questionId: number, userId: number): Observable<EntityResponseType> {
    return this.http.get<IVote>(this.resourceUrl2 + '?questionId=' + questionId + '&userId=' + userId, { observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
