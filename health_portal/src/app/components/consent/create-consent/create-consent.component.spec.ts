import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateConsentComponent } from './create-consent.component';

describe('CreateConsentComponent', () => {
  let component: CreateConsentComponent;
  let fixture: ComponentFixture<CreateConsentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateConsentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateConsentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
