import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { IQuiz } from 'app/shared/model/quiz.model';
import { ISection } from 'app/shared/model/section.model';
import { QuizService } from './quiz.service';
import { IQuestion } from 'app/shared/model/question.model';
import { QuestionService } from '../question/question.service';
import { IAnswer } from 'app/shared/model/answer.model';
import { AnswerService } from '../answer/answer.service';
import { IVote } from 'app/shared/model/vote.model';
import { VoteService } from '../vote/vote.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';

@Component({
  selector: 'jhi-quiz',
  templateUrl: './quiz.component.html',
})
export class QuizComponent implements OnInit, OnDestroy {
  quiz?: IQuiz;
  questions: IQuestion[] = [];
  answers: IAnswer[] = [];
  eventSubscriber?: Subscription;
  selectedSection?: number;
  selectedQuestion?: number;
  selectedAnswer?: number;
  account: Account | null = null;
  vote?: IVote;

  constructor(
    protected quizService: QuizService,
    protected questionService: QuestionService,
    protected answerService: AnswerService,
    protected voteService: VoteService,
    protected accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {}

  ngOnInit(): void {
    this.loadPage();
    this.accountService.identity(true).subscribe(account => (this.account = account));
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  loadPage(): void {
    this.quizService.find(1).subscribe(
        (res: HttpResponse<IQuiz>) => this.onSuccess(res.body),
        () => this.onError()
      );
  }

  setSection(_index: number): void {
    this.selectedQuestion = undefined;
    this.answers = [];
    this.selectedAnswer = undefined;
    this.selectedSection= _index;
    if(this.quiz && this.quiz.sections) {
      const sectionId = this.quiz.sections[_index].id;
      if (sectionId) {
        this.questionService.querySection(sectionId).subscribe((res: HttpResponse<IQuestion[]>) => this.onSuccessQuestion(res.body),
        () => this.onError()
      );
      }
    }
  }

  setQuestion(_index: number): void {
    this.selectedAnswer = undefined;
    this.selectedQuestion = _index;
    if(this.questions) {
      const questionId = this.questions[_index].id;
      if(questionId){
        // Find question answers
        this.answerService.queryQuestion(questionId).subscribe((res: HttpResponse<IAnswer[]>) => this.onSuccessAnswer(res.body),
          () => this.onError()
        );
        if (this.account) {
          // Find question vote
          this.voteService.queryUserVote(questionId, this.account.id).subscribe((res: HttpResponse<IVote>) => this.onSuccessVote(res.body),
            () => this.onError()
          );
        }
      }
    }
  }

  setAnswer(e: any) : void {
    alert('3');
    if (e.target.checked) {
      this.selectedAnswer = e.target.value;
      if (this.selectedAnswer && this.account && this.selectedQuestion) {
        const questionId = this.questions[this.selectedQuestion].id;
        if (questionId) {
          this.voteService.vote(questionId, this.selectedAnswer, this.account.id).subscribe((res: HttpResponse<IVote>) => this.onSuccessVote(res.body),
            () => this.onError()
          );
        }
      }
    } else {
      this.selectedAnswer = undefined;
    }
  }

  protected onSuccess(data: IQuiz | null): void {
    if (data) {
      this.quiz = data;
    }
  }

  protected onSuccessQuestion(data: IQuestion[] | null): void {
    if (data) {
      this.questions = data;
    }
  }

  protected onSuccessAnswer(data: IAnswer[] | null): void {
    if (data) {
      this.answers = data;
    }
  }

  protected onSuccessVote(data: IVote | null): void {
    if (data) {
      this.vote = data;
    }
  }

  protected onError(): void {
    // console.log("error");
  }

  trackId(index: number, item: ISection): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

}
