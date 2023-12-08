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
    const consentData = {};
    consentData["consentId"] = this.consentId;
    consentData["userId"] = this.userId;
    consentData["profileId"] = this.profileId;
    consentData["permission"] = this.permission;
    consentData["updatedAt"] = new Date();
    console.log('consent data', consentData);
    if (this.permission === undefined){
      alert("Permission cannot be null.");
    } else {
      this.ConsentService.createConsent(consentData).subscribe((response) => {});
      alert("Consent created successfully!");
      this.router.navigate(['/show-consent'])
    }
  }

}
