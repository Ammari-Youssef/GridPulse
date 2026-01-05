import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '@shared/shared.module';
import { MaterialModule } from '@shared/ui/material/material.module';
import { DeviceListComponent } from './device-list/device-list.component';
import { DevicesRoutingModule } from './devices-routing.module';

@NgModule({
  declarations: [DeviceListComponent],
  imports: [CommonModule, SharedModule, MaterialModule, DevicesRoutingModule],
})
export class DevicesModule {}
