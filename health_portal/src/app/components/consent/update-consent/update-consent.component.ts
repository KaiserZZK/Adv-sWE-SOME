import { Component } from '@angular/core';
import { ActivatedRoute, Router} from "@angular/router";
import { ConsentService } from "../../../service/consent.service";
import { Consent } from "../../../documents/consent";

@Component({
  selector: 'app-consent',
  templateUrl: './update-consent.component.html',
  styleUrls: ['./update-consent.component.css']
})
export class UpdateConsentComponent {
  consent: Consent;
  permission: boolean;
  userId: String;
  profileId: string = "1";
  consentId: String;

  constructor (
    private ConsentService: ConsentService,
    private route: ActivatedRoute,
    private router: Router,
  ) {}

  ngOnInit() {
    this.userId = this.ConsentService.getIdFromJwt();
    this.consentId = this.userId;
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
    consentData["consentId"] = this.consentId;
    consentData["userId"] = this.userId;
    consentData["profileId"] = this.profileId;
    consentData["permission"] = this.permission;
    console.log('consent data', consentData);
    if (this.permission === undefined) {
      alert("Permission cannot be null.");
    } else {
      this.ConsentService.updateConsent(this.consentId, consentData)
        .subscribe(
          (response) => {
            console.log('Consent updated:', response);
          },
          (error) => {
            console.error('Error updating consent:', error);
          }
        );
      alert("Consent updated successfully!");
      this.router.navigate(['/show-consent'])
    }
  }

  deleteConsent() {
    this.ConsentService.deleteConsent(this.consentId)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
    alert("Consent deleted successfully!");
    this.router.navigate(['/show-consent'])
  }

}
