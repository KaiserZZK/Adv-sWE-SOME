import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

// to be changed to deployed instance 
const BASE_URL = ['http://localhost:8080/']

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(
    private http: HttpClient
  ) { }

  getProfiles(userId): Observable<any> {
    return this.http.get(BASE_URL + 'profiles/user/' + userId, {
      headers: this.createAuthorizationHeader()
    });
  }

  getProfile(profileId): Observable<any> {
    return this.http.get(BASE_URL + 'profiles/' + profileId, {
      headers: this.createAuthorizationHeader()
    });
  }

  createProfile(profile): Observable<any> {
    return this.http.post(BASE_URL + 'profiles', profile, {
      headers: this.createAuthorizationHeader()
    });
  }

  updateProfile(profileId, profile): Observable<any> {
    return this.http.post(BASE_URL + 'profiles/' + profileId, profile, {
      headers: this.createAuthorizationHeader()
    });
  }

  deleteProfile(profileId): Observable<any> {
    return this.http.delete(BASE_URL + 'profiles/' + profileId, {
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