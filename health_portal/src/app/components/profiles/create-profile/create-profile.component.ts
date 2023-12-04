import { Component } from '@angular/core';
import { Profile } from "./../../../documents/profile";
import { ProfileService } from "./../../../service/profile.service";
import { Router } from '@angular/router';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-profile',
  templateUrl: './create-profile.component.html',
  styleUrls: ['./create-profile.component.css']
})


export class CreateProfileComponent {

  profile: Profile = new Profile();
  submitted = false;
  userId: string;
  medicalHistoryForm: FormGroup;
  list = []

  constructor(
    private profileService: ProfileService,
    private router: Router,
    private fb: FormBuilder
  ) { 
    this.medicalHistoryForm = this.fb.group({
      tableRows: this.fb.array([],[Validators.required])
    });
    this.addRow();
  }

  ngOnInit() {
    this.userId = this.profileService.getIdFromJwt();
  }

  createFormGroup(): FormGroup {
    return this.fb.group({
      diseaseName: ['',[Validators.required]],
      diagnosedAt: ['',[Validators.required]],
      treatment:[''],
    });
  }

  get getFormControls() {
    const control = this.medicalHistoryForm.get('tableRows') as FormArray;
    return control;
  }

  addRow() {
    const control =  this.medicalHistoryForm.get('tableRows') as FormArray;
    control.push(this.createFormGroup());
  }

  newProfile(): void {
    this.submitted = false;
    this.profile = new Profile();
  }

  save() {
    this.profile.userId = this.userId; 
    this.profileService.createProfile(this.profile)
      .subscribe(data => console.log(data), error => console.log(error));
    this.profile = new Profile();
    alert("Profile created successfully!");
    this.gotoList();
  }

  removeMedicalHistory(index:number) {
    const control =  this.medicalHistoryForm.get('tableRows') as FormArray;
    control.removeAt(index);
  }

  onSubmit() {
    this.submitted = true;
    console.log(this.list);
    this.save();    
  }

  gotoList() {
    this.router.navigate(['/list-profiles']);
  }

}
