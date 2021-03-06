import { Injectable } from '@angular/core';
import {
    HttpEvent,
    HttpInterceptor,
    HttpHandler,
    HttpRequest,
    HttpResponse,
    HttpResponseBase,    
    HttpErrorResponse
} from '@angular/common/http';

import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

declare var acp: string;

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {

    intercept( request: HttpRequest<any>, next: HttpHandler ): Observable<HttpEvent<any>> {

        return next.handle( request ).pipe(tap(( event: HttpEvent<any> ) => {
            if ( event instanceof HttpResponseBase ) {
                const response = event as HttpResponseBase;
                if ( response.status === 302 ) {
                    window.location.href = acp + '/cgr/manage#/login';
                    return;
                }
            }
        }, ( err: any ) => {
            if ( err instanceof HttpErrorResponse ) {
                if ( err.status === 401 || err.status === 403 ) {
                    // redirect to the login route
                    // or show a modal
                    window.location.href = acp + '/cgr/manage#/login';
                }
            }
        } ));
    }
}