import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

// to be changed to deployed instance
const BASE_URL = ['http://localhost:8080/']

@Injectable({
  providedIn: 'root'
})
export class PrescriptionService {

  constructor(
    private http: HttpClient
  ) { }

  getPrescriptions(profileId): Observable<any> {
    return this.http.get(BASE_URL + 'prescriptions/profile/' + profileId, {
      headers: this.createAuthorizationHeader()
    });
  }

  getPrescription(prescriptionId): Observable<any> {
    return this.http.get(BASE_URL + 'prescriptions/' + prescriptionId, {
      headers: this.createAuthorizationHeader()
    });
  }

  deletePrescription(prescriptionId): Observable<any> {
    return this.http.delete(BASE_URL + 'prescriptions/' + prescriptionId, {
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
