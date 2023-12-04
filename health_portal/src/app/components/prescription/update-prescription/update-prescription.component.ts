import { Component } from '@angular/core';
import { Prescription } from "./../../../documents/prescription";
import { PrescriptionService } from "./../../../service/prescription.service";
import { Router, ActivatedRoute } from '@angular/router';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-prescription',
  templateUrl: './update-prescription.component.html',
  styleUrls: ['./update-prescription.component.css']
})
export class UpdatePrescriptionComponent {

  prescription: Prescription = new Prescription();
  submitted = false;
  profileId: string;
  prescriptionId: string;

  constructor(
    private route: ActivatedRoute,
    private prescriptionService: PrescriptionService,
    private router: Router,
    private fb: FormBuilder
  ) { 
  }

  ngOnInit() {
    this.profileId = this.route.snapshot.paramMap.get('profileId');
    this.prescriptionId = this.route.snapshot.paramMap.get('prescriptionId');

    this.prescriptionService.getPrescription(this.prescriptionId)
        .subscribe(
          (data: Prescription) => {
            this.prescription = data;
          }
        )
    }  

  newPrescription(): void {
    this.submitted = false;
    this.prescription = new Prescription();
  }

  save() {
    this.prescription.profileId = this.profileId;
    this.prescription.prescriptionId = this.prescriptionId; 
    this.prescriptionService.updatePrescription(this.prescriptionId, this.prescription)
      .subscribe(data => console.log(data), error => console.log(error));
    this.prescription = new Prescription();
    alert("Prescription updated successfully!");
    this.gotoList(this.profileId);
  }

  onSubmit() {
    this.submitted = true;
    this.save();    
  }

  gotoList(profileId: String) {
    this.router.navigate(['list-prescription', profileId]);
  }

}