import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChamadoLista } from './chamado-lista';

describe('ChamadoLista', () => {
  let component: ChamadoLista;
  let fixture: ComponentFixture<ChamadoLista>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChamadoLista]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChamadoLista);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
