import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VotacioTestModule } from '../../../test.module';
import { AnswerStatusUpdateComponent } from 'app/entities/answer-status/answer-status-update.component';
import { AnswerStatusService } from 'app/entities/answer-status/answer-status.service';
import { AnswerStatus } from 'app/shared/model/answer-status.model';

describe('Component Tests', () => {
  describe('AnswerStatus Management Update Component', () => {
    let comp: AnswerStatusUpdateComponent;
    let fixture: ComponentFixture<AnswerStatusUpdateComponent>;
    let service: AnswerStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VotacioTestModule],
        declarations: [AnswerStatusUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(AnswerStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnswerStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AnswerStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AnswerStatus(123);
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
        const entity = new AnswerStatus();
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
