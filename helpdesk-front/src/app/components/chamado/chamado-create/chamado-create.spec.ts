import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChamadoCreate } from './chamado-create';

describe('ChamadoCreate', () => {
  let component: ChamadoCreate;
  let fixture: ComponentFixture<ChamadoCreate>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChamadoCreate]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChamadoCreate);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
