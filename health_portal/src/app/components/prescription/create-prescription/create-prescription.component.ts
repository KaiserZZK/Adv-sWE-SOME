import { Component } from '@angular/core';
import { Prescription } from "./../../../documents/prescription";
import { PrescriptionService } from "./../../../service/prescription.service";
import { Router, ActivatedRoute } from '@angular/router';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-prescription',
  templateUrl: './create-prescription.component.html',
  styleUrls: ['./create-prescription.component.css']
})
export class CreatePrescriptionComponent {

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
  }

  newProfile(): void {
    this.submitted = false;
    this.prescription = new Prescription();
  }

  save() {
    this.prescription.profileId = this.profileId;
    this.prescriptionService.createPrescription(this.prescription)
      .subscribe(data => console.log(data), error => console.log(error));
    this.prescription = new Prescription();
    alert("Prescription created successfully!");
    this.gotoList(this.profileId);
  }

  onSubmit() {
    this.submitted = true;
    this.save();    
  }

  gotoList(profileId: String) {
    this.router.navigate(['list-prescription', this.profileId]);
  }

}