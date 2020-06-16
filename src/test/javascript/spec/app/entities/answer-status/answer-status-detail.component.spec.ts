import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VotacioTestModule } from '../../../test.module';
import { AnswerStatusDetailComponent } from 'app/entities/answer-status/answer-status-detail.component';
import { AnswerStatus } from 'app/shared/model/answer-status.model';

describe('Component Tests', () => {
  describe('AnswerStatus Management Detail Component', () => {
    let comp: AnswerStatusDetailComponent;
    let fixture: ComponentFixture<AnswerStatusDetailComponent>;
    const route = ({ data: of({ answerStatus: new AnswerStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VotacioTestModule],
        declarations: [AnswerStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(AnswerStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnswerStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load answerStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.answerStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
