import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

const BASE_URL = ['http://localhost:8080/']

@Injectable({
  providedIn: 'root'
})
export class ConsentService {

  constructor(
    private http: HttpClient
  ) { }

  getConsentByUid(userId): Observable<any> {
    return this.http.get(BASE_URL + 'consents/user/' + userId, {
      headers: this.createAuthorizationHeader()
    });
  }

  getConsentByPid(profileId): Observable<any> {
    return this.http.get(BASE_URL + 'consents/profile/' + profileId, {
      headers: this.createAuthorizationHeader()
    });
  }

  getConsentByCid(consentId): Observable<any> {
    return this.http.get(BASE_URL + 'consents/' + consentId, {
      headers: this.createAuthorizationHeader()
    });
  }

  createConsent(consentData): Observable<any> {
    return this.http.post(BASE_URL + 'consents', consentData, {
      headers: this.createAuthorizationHeader()
    });
  }

  deleteConsent(consentId): Observable<any> {
    return this.http.delete(BASE_URL + 'consents/' + consentId, {
      headers: this.createAuthorizationHeader()
    });
  }

  updateConsent(consentId, consentData): Observable<any> {
    return this.http.put(BASE_URL + 'consents/' + consentId, consentData, {
      headers: this.createAuthorizationHeader()
    });
  }

  getIdFromJwt(): any {
    const jwtToken = localStorage.getItem('JWT');
    try {
      return jwtDecode(jwtToken)['sub'];
    } catch(Error) {
      return null;
    }
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
