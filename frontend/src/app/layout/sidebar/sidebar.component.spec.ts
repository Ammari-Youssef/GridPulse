import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SidebarComponent } from './sidebar.component';
import { ActivatedRoute } from '@angular/router';
import { ActivatedRouteMock } from '@testing/mock/activated-route.mock';
import { CommonModule } from '@angular/common';
import { Apollo } from 'apollo-angular';
import { ApolloMock } from '@testing/mock/apollo.mock';
import { MaterialModule } from '@shared/ui/material/material.module';

describe('SidebarComponent', () => {
  let component: SidebarComponent;
  let fixture: ComponentFixture<SidebarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommonModule, MaterialModule, Apollo],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: ActivatedRouteMock,
        },
        {
          provide: Apollo,
          useValue: ApolloMock,
        },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(SidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
