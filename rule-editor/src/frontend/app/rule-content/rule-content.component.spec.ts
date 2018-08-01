import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RuleContentComponent } from './rule-content.component';

describe('RuleContentComponent', () => {
  let component: RuleContentComponent;
  let fixture: ComponentFixture<RuleContentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RuleContentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RuleContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
