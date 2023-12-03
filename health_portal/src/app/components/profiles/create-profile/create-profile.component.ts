import { Component } from '@angular/core';
import { Profile } from "./../../../documents/profile";
import { ProfileService } from "./../../../service/profile.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-profile',
  templateUrl: './create-profile.component.html',
  styleUrls: ['./create-profile.component.css']
})


export class CreateProfileComponent {

  profile: Profile = new Profile();
  submitted = false;
  userId: string;

  constructor(
    private profileService: ProfileService,
    private router: Router
  ) { }

  ngOnInit() {
    this.userId = this.profileService.getIdFromJwt();
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
    this.gotoList();
  }

  onSubmit() {
    this.submitted = true;
    this.save();    
  }

  gotoList() {
    this.router.navigate(['/list-profiles']);
  }

}
