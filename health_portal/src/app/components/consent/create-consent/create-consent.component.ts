import { Component } from '@angular/core';
import { Router } from "@angular/router";
// import { v4 as uuidv4 } from 'uuid';
import { ConsentService } from "../../../service/consent.service";
import { Consent } from "../../../documents/consent";

@Component({
  selector: 'app-create-consent',
  templateUrl: './create-consent.component.html',
  styleUrls: ['./create-consent.component.css']
})
export class CreateConsentComponent {

  consent: Consent = new Consent();
  permission: boolean;
  userId: string;
  profileId: string = "1";
  // consentId: string = uuidv4();
  consentId: string;

  constructor (
    private ConsentService: ConsentService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.userId = this.ConsentService.getIdFromJwt();
    this.consentId = this.userId;
  }

  createConsent() {
    this.consent.consentId = this.consentId;
    this.consent.userId = this.userId;
    this.consent.profileId = this.profileId;
    this.consent.permission = this.permission;
    this.consent.updatedAt = new Date();
    console.log('consent data', this.consent);
    this.ConsentService.createConsent(this.consent)
      .subscribe(
        (response) => {
          console.log('Consent created:', response);
        },
        (error) => {
          console.error('Error creating consent:', error);
        }
      );
    alert("Consent created successfully!");
    this.router.navigate(['/show-consent'])
  }

}
