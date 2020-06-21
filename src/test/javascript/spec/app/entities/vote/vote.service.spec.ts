import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { VoteService } from 'app/entities/vote/vote.service';
import { IVote, Vote } from 'app/shared/model/vote.model';
import { IAnswer } from 'app/shared/model/answer.model';
import { IQuestion } from 'app/shared/model/question.model';

describe('Service Tests', () => {
  describe('Vote Service', () => {
    let injector: TestBed;
    let service: VoteService;
    let httpMock: HttpTestingController;
    let elemDefault: IVote;
    let expectedResult: IVote | IVote[] | boolean | null;
    const question: IQuestion = { };
    const answer: IAnswer = { };

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(VoteService);
      httpMock = injector.get(HttpTestingController);

      question.id = 0;
      answer.id = 0;
      elemDefault = new Vote(0, 0, 0, question, answer);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Vote', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Vote()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Vote', () => {
        const returnedFromService = Object.assign(
          {
            userId: 1,
            questionId: 1,
            answerId: 1,
            status: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Vote', () => {
        const returnedFromService = Object.assign(
          {
            userId: 1,
            questionId: 1,
            answerId: 1,
            status: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Vote', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
