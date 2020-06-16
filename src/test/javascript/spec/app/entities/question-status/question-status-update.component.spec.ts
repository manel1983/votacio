import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VotacioTestModule } from '../../../test.module';
import { QuestionStatusUpdateComponent } from 'app/entities/question-status/question-status-update.component';
import { QuestionStatusService } from 'app/entities/question-status/question-status.service';
import { QuestionStatus } from 'app/shared/model/question-status.model';

describe('Component Tests', () => {
  describe('QuestionStatus Management Update Component', () => {
    let comp: QuestionStatusUpdateComponent;
    let fixture: ComponentFixture<QuestionStatusUpdateComponent>;
    let service: QuestionStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VotacioTestModule],
        declarations: [QuestionStatusUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(QuestionStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(QuestionStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(QuestionStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuestionStatus(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new QuestionStatus();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
