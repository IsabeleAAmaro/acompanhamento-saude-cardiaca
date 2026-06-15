import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RegistroSaudePage } from './registro-saude.page';

describe('RegistroSaudePage', () => {
  let component: RegistroSaudePage;
  let fixture: ComponentFixture<RegistroSaudePage>;

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistroSaudePage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
