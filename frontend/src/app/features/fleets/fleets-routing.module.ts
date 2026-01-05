import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FleetListComponent } from './fleet-list/fleet-list.component';

const routes: Routes = [
  { path: '', component: FleetListComponent, title: 'Fleet List' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class FleetsRoutingModule {}
