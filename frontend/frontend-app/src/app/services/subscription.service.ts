import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { ISubscription } from '../models/ISubscription';
@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private apiUrl = 'http://localhost:8080/subscriptions';

  constructor(private http: HttpClient) { }

  getAllSubscriptions(): Observable<ISubscription[]> {
    return this.http.get<ISubscription[]>(this.apiUrl)
      .pipe(catchError(this.handleError));
  }

  createSubscription(subscription: Omit<ISubscription, 'id'>): Observable<ISubscription> {
    return this.http.post<ISubscription>(this.apiUrl, subscription)
      .pipe(catchError(this.handleError));
  }

  updateSubscription(id: number, changes: Partial<ISubscription>): Observable<ISubscription> {
    return this.http.put<ISubscription>(`${this.apiUrl}/${id}`, changes)
      .pipe(catchError(this.handleError));
  }

  deleteSubscription(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    let errorMessage = 'Произошла неизвестная ошибка';
    if (error.error instanceof ErrorEvent) {
      // Клиентская ошибка
      errorMessage = `Ошибка: ${error.error.message}`;
    } else {
      // Серверная ошибка
      errorMessage = `Ошибка сервера: ${error.status}\nСообщение: ${error.message}`;
      if (error.error && typeof error.error === 'string') {
        errorMessage += `\nДетали: ${error.error}`;
      }
    }
    console.error(errorMessage);
    return throwError(errorMessage);
  }
}