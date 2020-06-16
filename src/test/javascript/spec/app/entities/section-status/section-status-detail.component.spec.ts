import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VotacioTestModule } from '../../../test.module';
import { SectionStatusDetailComponent } from 'app/entities/section-status/section-status-detail.component';
import { SectionStatus } from 'app/shared/model/section-status.model';

describe('Component Tests', () => {
  describe('SectionStatus Management Detail Component', () => {
    let comp: SectionStatusDetailComponent;
    let fixture: ComponentFixture<SectionStatusDetailComponent>;
    const route = ({ data: of({ sectionStatus: new SectionStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VotacioTestModule],
        declarations: [SectionStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SectionStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SectionStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load sectionStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.sectionStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
