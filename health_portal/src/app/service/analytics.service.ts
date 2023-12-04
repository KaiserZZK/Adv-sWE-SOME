import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

// to be changed to deployed instance 
const BASE_URL = ['http://localhost:8080/']

@Injectable({
  providedIn: 'root'
})

export class AnalyticsService {

  constructor(
    private http: HttpClient
  ) { }

  getHealthAdvice(profileId): Observable<any> {
    return this.http.get(BASE_URL + 'analytics/' + profileId, {
      headers: this.createAuthorizationHeader()
    });
  }

  private createAuthorizationHeader() {
    const jwtToken = localStorage.getItem('JWT');
    if (jwtToken) {
      return new HttpHeaders().set(
        'Authorization', 'Bearer ' + jwtToken
      )
    } else {
      console.log("JWT token not found in the Local Storage");
    }
    return null;
  }

}