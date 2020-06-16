import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISectionStatus } from 'app/shared/model/section-status.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SectionStatusService } from './section-status.service';
import { SectionStatusDeleteDialogComponent } from './section-status-delete-dialog.component';

@Component({
  selector: 'jhi-section-status',
  templateUrl: './section-status.component.html',
})
export class SectionStatusComponent implements OnInit, OnDestroy {
  sectionStatuses: ISectionStatus[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sectionStatusService: SectionStatusService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sectionStatuses = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sectionStatusService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ISectionStatus[]>) => this.paginateSectionStatuses(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.sectionStatuses = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSectionStatuses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISectionStatus): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSectionStatuses(): void {
    this.eventSubscriber = this.eventManager.subscribe('sectionStatusListModification', () => this.reset());
  }

  delete(sectionStatus: ISectionStatus): void {
    const modalRef = this.modalService.open(SectionStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sectionStatus = sectionStatus;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSectionStatuses(data: ISectionStatus[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sectionStatuses.push(data[i]);
      }
    }
  }
}
