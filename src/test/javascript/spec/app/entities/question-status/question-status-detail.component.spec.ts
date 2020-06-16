import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { VotacioTestModule } from '../../../test.module';
import { QuestionStatusDetailComponent } from 'app/entities/question-status/question-status-detail.component';
import { QuestionStatus } from 'app/shared/model/question-status.model';

describe('Component Tests', () => {
  describe('QuestionStatus Management Detail Component', () => {
    let comp: QuestionStatusDetailComponent;
    let fixture: ComponentFixture<QuestionStatusDetailComponent>;
    const route = ({ data: of({ questionStatus: new QuestionStatus(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VotacioTestModule],
        declarations: [QuestionStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(QuestionStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(QuestionStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load questionStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.questionStatus).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
