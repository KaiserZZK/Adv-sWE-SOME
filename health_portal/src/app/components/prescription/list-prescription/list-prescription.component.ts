import { Component } from '@angular/core';
import { Observable } from "rxjs";
import { Prescription } from "./../../../documents/prescription";
import { PrescriptionService } from "./../../../service/prescription.service";
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-list-prescription',
  templateUrl: './list-prescription.component.html',
  styleUrls: ['./list-prescription.component.css']
})

export class ListPrescriptionComponent {

  profileId: String;
  prescriptionList: Observable<Prescription[]>;

  constructor (
    private route: ActivatedRoute,
    private prescriptionService: PrescriptionService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.reloadData();
  }

  reloadData() {
    this.profileId = this.route.snapshot.params['profileId'];
    this.prescriptionList = this.prescriptionService.getPrescriptionList(this.profileId);
  }

  deletePrescription(prescriptionId: String) {
    this.prescriptionService.deletePrescription(prescriptionId)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
  }

  // not really necessary here
  // viewPrescription(prescriptionId: String) {
  //   this.router.navigate(['view-prescription', prescriptionId]);
  // }

  createPrescription() {
    this.router.navigate(['create-prescription', this.profileId]);
  }

  updatePrescription(profileId: String, prescriptionId: String) {
    this.router.navigate(['update-prescription', profileId, prescriptionId]);
  }

}
