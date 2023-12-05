import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ConsentService } from "../../../service/consent.service";

@Component({
  selector: 'app-show-consent',
  templateUrl: './show-consent.component.html',
  styleUrls: ['./show-consent.component.css']
})
export class ShowConsentComponent {
  consent: any;
  userId: String;

  constructor (
    private ConsentService: ConsentService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.userId = this.ConsentService.getIdFromJwt();
    this.reloadData();
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
}
