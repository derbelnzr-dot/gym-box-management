import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContactCoachsComponent } from './contact-coachs.component';

describe('ContactCoachsComponent', () => {
  let component: ContactCoachsComponent;
  let fixture: ComponentFixture<ContactCoachsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContactCoachsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContactCoachsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
