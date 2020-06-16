import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { VotacioTestModule } from '../../../test.module';
import { SectionStatusUpdateComponent } from 'app/entities/section-status/section-status-update.component';
import { SectionStatusService } from 'app/entities/section-status/section-status.service';
import { SectionStatus } from 'app/shared/model/section-status.model';

describe('Component Tests', () => {
  describe('SectionStatus Management Update Component', () => {
    let comp: SectionStatusUpdateComponent;
    let fixture: ComponentFixture<SectionStatusUpdateComponent>;
    let service: SectionStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [VotacioTestModule],
        declarations: [SectionStatusUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SectionStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SectionStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SectionStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SectionStatus(123);
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
        const entity = new SectionStatus();
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
