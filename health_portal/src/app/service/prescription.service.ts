import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASE_URL = ['http://localhost:8080/']

@Injectable({
  providedIn: 'root'
})

export class PrescriptionService {

  constructor(
    private http: HttpClient
  ) { }

  getPrescriptionList(profileId): Observable<any> {
    return this.http.get(BASE_URL + 'prescriptions/profile/' + profileId, {
      headers: this.createAuthorizationHeader()
    });
  }

  getPrescription(prescriptionId): Observable<any> {
    return this.http.get(BASE_URL + 'prescriptions/' + prescriptionId, {
      headers: this.createAuthorizationHeader()
    });
  }

  createPrescription(prescription): Observable<any> {
    return this.http.post(BASE_URL + 'prescriptions', prescription, {
      headers: this.createAuthorizationHeader()
    });
  }

  updatePrescription(prescriptionId, prescription): Observable<any> {
    return this.http.put(BASE_URL + 'prescriptions/' + prescriptionId, prescription, {
      headers: this.createAuthorizationHeader()
    });
  }

  deletePrescription(prescriptionId): Observable<any> {
    return this.http.delete(BASE_URL + 'prescriptions/' + prescriptionId, {
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
