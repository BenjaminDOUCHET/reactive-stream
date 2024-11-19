import { Injectable } from '@angular/core';
import {HttpClient, HttpResponse} from "@angular/common/http";
import {catchError, map, Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class DataService {

  constructor(private http: HttpClient) {}
  fetchData(url: string): Observable<{data: Blob, size: number}> {
    return this.http.get(url, { responseType: 'blob', observe: 'response' })
      .pipe(
        map((response: HttpResponse<Blob>) => {
          if (response.body) {
            return { data: response.body, size: response.body.size };
          } else {
            throw new Error('Body is null');
          }
        }),
        catchError(error => {
          console.error('An error occurred:', error);
          return throwError(error);
        })
      );
  }

}
