import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateConsentComponent } from './update-consent.component';

describe('ConsentComponent', () => {
  let component: UpdateConsentComponent;
  let fixture: ComponentFixture<UpdateConsentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateConsentComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateConsentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
