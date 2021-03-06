import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAnswerStatus } from 'app/shared/model/answer-status.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AnswerStatusService } from './answer-status.service';
import { AnswerStatusDeleteDialogComponent } from './answer-status-delete-dialog.component';

@Component({
  selector: 'jhi-answer-status',
  templateUrl: './answer-status.component.html',
})
export class AnswerStatusComponent implements OnInit, OnDestroy {
  answerStatuses: IAnswerStatus[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected answerStatusService: AnswerStatusService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.answerStatuses = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.answerStatusService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IAnswerStatus[]>) => this.paginateAnswerStatuses(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.answerStatuses = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAnswerStatuses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAnswerStatus): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAnswerStatuses(): void {
    this.eventSubscriber = this.eventManager.subscribe('answerStatusListModification', () => this.reset());
  }

  delete(answerStatus: IAnswerStatus): void {
    const modalRef = this.modalService.open(AnswerStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.answerStatus = answerStatus;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAnswerStatuses(data: IAnswerStatus[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.answerStatuses.push(data[i]);
      }
    }
  }
}
