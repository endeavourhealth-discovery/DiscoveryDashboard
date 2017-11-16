import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PayloadViewerComponent } from './payload-viewer.component';

describe('PayloadViewerComponent', () => {
  let component: PayloadViewerComponent;
  let fixture: ComponentFixture<PayloadViewerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PayloadViewerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PayloadViewerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
