import { Component } from '@angular/core';
import { Profile } from "./../../../documents/profile";
import { MedicalHistory } from "./../../../documents/medical-history";
import { ProfileService } from "./../../../service/profile.service";
import { Router, ActivatedRoute } from '@angular/router';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-profile',
  templateUrl: './update-profile.component.html',
  styleUrls: ['./update-profile.component.css']
})

export class UpdateProfileComponent {

  profile: Profile = new Profile();
  submitted = false;
  userId: string;
  medicalHistoryForm: FormGroup;
  list = []

  constructor(
    private route: ActivatedRoute,
    private profileService: ProfileService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.medicalHistoryForm = this.fb.group({
      tableRows: this.fb.array([],[Validators.required])
    });
  }

  ngOnInit() {
    this.userId = this.profileService.getIdFromJwt();
    const profileId = this.route.snapshot.paramMap.get('profileId');

    this.profileService.getProfile(profileId)
        .subscribe(
          (data: Profile) => {
            this.profile = data;
            this.profile.medicalHistory.forEach(medicalHistory => {
              this.addRow(medicalHistory);
            });
            console.log(this.medicalHistoryForm.value)
          }
        )
    }

  createFormGroup(medicalHistory?: MedicalHistory): FormGroup {
    return this.fb.group({
      diseaseName: [medicalHistory ? medicalHistory.diseaseName : '',[Validators.required]],
      diagnosedAt: [medicalHistory ? medicalHistory.diagnosedAt : '',[Validators.required]],
      treatment:[medicalHistory ? medicalHistory.treatment : '',[Validators.required]],
    });
  }

  get getFormControls() {
    const control = this.medicalHistoryForm.get('tableRows') as FormArray;
    return control;
  }

  addRow(medicalHistory?: MedicalHistory) {
    const control =  this.medicalHistoryForm.get('tableRows') as FormArray;
    control.push(this.createFormGroup(medicalHistory));
  }

  newProfile(): void {
    this.submitted = false;
    this.profile = new Profile();
  }

  save() {
    this.profile.userId = this.userId;
    this.profile.medicalHistory = this.medicalHistoryForm.value.tableRows;
    console.log(this.medicalHistoryForm.value)
    this.profileService.createProfile(this.profile)
      .subscribe(data => console.log(data), error => console.log(error));
    this.profile = new Profile();
    alert("Profile updated successfully!");
    this.gotoList();
  }

  removeMedicalHistory(index:number) {
    const control =  this.medicalHistoryForm.get('tableRows') as FormArray;
    control.removeAt(index);
  }

  onSaveForm() {
    const formValue = this.medicalHistoryForm.value;
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
