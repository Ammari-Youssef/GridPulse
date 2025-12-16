import { of } from 'rxjs';
import { convertToParamMap } from '@angular/router';

export const ActivatedRouteMock = {
  snapshot: { params: {} },
  paramMap: of(convertToParamMap({})),
  queryParamMap: of(convertToParamMap({})),
};
