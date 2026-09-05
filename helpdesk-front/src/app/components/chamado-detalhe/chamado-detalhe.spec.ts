import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChamadoDetalhe } from './chamado-detalhe';

describe('ChamadoDetalhe', () => {
  let component: ChamadoDetalhe;
  let fixture: ComponentFixture<ChamadoDetalhe>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ChamadoDetalhe]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChamadoDetalhe);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
