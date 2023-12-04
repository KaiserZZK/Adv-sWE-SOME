import { Component } from '@angular/core';
import {Router} from "@angular/router";
import { ConsentService } from "../../service/consent.service";

@Component({
  selector: 'app-consent',
  templateUrl: './update-consent.component.html',
  styleUrls: ['./update-consent.component.css']
})
export class UpdateConsentComponent {
  permission: boolean;
  consent: any;
  userId: String;
  consentId: String;

  constructor (
    private ConsentService: ConsentService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.userId = this.ConsentService.getIdFromJwt();
  }

  reloadData() {
    this.ConsentService.getConsentByUid(this.userId)
      .subscribe(
        data => {
          this.consent = data;
        },
        error => {
          console.log(error)
        }
      );
  }

  updateConsent() {
    const consentData = {};
    consentData["permission"] = this.permission;
    console.log('consent data', consentData);
    this.ConsentService.updateConsent(this.consent["consentId"], consentData)
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

  deleteConsent(consentId) {
    this.ConsentService.deleteConsent(consentId)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
  }

}
