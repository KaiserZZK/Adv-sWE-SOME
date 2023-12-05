import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowConsentComponent } from './show-consent.component';

describe('ShowConsentComponent', () => {
  let component: ShowConsentComponent;
  let fixture: ComponentFixture<ShowConsentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ShowConsentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ShowConsentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
