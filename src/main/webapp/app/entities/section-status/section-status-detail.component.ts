import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISectionStatus } from 'app/shared/model/section-status.model';

@Component({
  selector: 'jhi-section-status-detail',
  templateUrl: './section-status-detail.component.html',
})
export class SectionStatusDetailComponent implements OnInit {
  sectionStatus: ISectionStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sectionStatus }) => (this.sectionStatus = sectionStatus));
  }

  previousState(): void {
    window.history.back();
  }
}
