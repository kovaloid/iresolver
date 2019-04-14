import { TestBed, inject } from '@angular/core/testing';

import { RuleCollectionService } from './rule-collection.service';

describe('RuleCollectionService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RuleCollectionService]
    });
  });

  it('should be created', inject([RuleCollectionService], (service: RuleCollectionService) => {
    expect(service).toBeTruthy();
  }));
});
