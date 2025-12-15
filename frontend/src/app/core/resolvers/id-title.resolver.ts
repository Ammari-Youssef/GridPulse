import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class IdTitleResolver implements Resolve<string> {

  resolve(route: ActivatedRouteSnapshot): string {
    const id = route.paramMap.get('id');
    return id ? `Details – ${id}` : 'Details';
  }
  
}
