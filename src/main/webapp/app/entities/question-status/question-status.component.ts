import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IQuestionStatus } from 'app/shared/model/question-status.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { QuestionStatusService } from './question-status.service';
import { QuestionStatusDeleteDialogComponent } from './question-status-delete-dialog.component';

@Component({
  selector: 'jhi-question-status',
  templateUrl: './question-status.component.html',
})
export class QuestionStatusComponent implements OnInit, OnDestroy {
  questionStatuses: IQuestionStatus[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected questionStatusService: QuestionStatusService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.questionStatuses = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.questionStatusService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IQuestionStatus[]>) => this.paginateQuestionStatuses(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.questionStatuses = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInQuestionStatuses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IQuestionStatus): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInQuestionStatuses(): void {
    this.eventSubscriber = this.eventManager.subscribe('questionStatusListModification', () => this.reset());
  }

  delete(questionStatus: IQuestionStatus): void {
    const modalRef = this.modalService.open(QuestionStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.questionStatus = questionStatus;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateQuestionStatuses(data: IQuestionStatus[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.questionStatuses.push(data[i]);
      }
    }
  }
}
