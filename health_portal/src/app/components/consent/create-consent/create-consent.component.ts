import { Component } from '@angular/core';
import { Router } from "@angular/router";
import { v4 as uuidv4 } from 'uuid';
import { ConsentService } from "../../../service/consent.service";

@Component({
  selector: 'app-create-consent',
  templateUrl: './create-consent.component.html',
  styleUrls: ['./create-consent.component.css']
})
export class CreateConsentComponent {
  permission: boolean;
  userId: String;
  profileId: String = "1";
  consentId: String = uuidv4();

  constructor (
    private ConsentService: ConsentService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.userId = this.ConsentService.getIdFromJwt();
  }

  createConsent() {
    const consentData = {};
    consentData["consentId"] = this.consentId;
    consentData["userId"] = this.userId;
    consentData["profileId"] = this.profileId;
    consentData["permission"] = this.permission;
    consentData["updatedAt"] = new Date();
    console.log('consent data', consentData);
    this.ConsentService.createConsent(consentData)
      .subscribe(
        (response) => {
          console.log('Consent created:', response);
        },
        (error) => {
          console.error('Error creating consent:', error);
        }
      );
    this.router.navigate(['/show-consent'])
  }

}
