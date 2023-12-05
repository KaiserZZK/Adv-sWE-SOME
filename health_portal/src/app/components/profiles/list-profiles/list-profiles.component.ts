import { Component } from '@angular/core';
import { Observable } from "rxjs";
import { Profile } from "./../../../documents/profile";
import { ProfileService } from "./../../../service/profile.service";
import { Router } from '@angular/router';

@Component({
  selector: 'app-list-profiles',
  templateUrl: './list-profiles.component.html',
  styleUrls: ['./list-profiles.component.css']
})

export class ListProfilesComponent {

  profiles: Observable<Profile[]>;
  userId: String;

  constructor (
    private profileService: ProfileService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.userId = this.profileService.getIdFromJwt();
    this.reloadData();
  }

  reloadData() {
    this.profiles = this.profileService.getProfiles(this.userId);
  }

  deleteProfile(id: String) {
    this.profileService.deleteProfile(id)
      .subscribe(
        data => {
          console.log(data);
          this.reloadData();
        },
        error => console.log(error));
  }

  viewProfile(profileId: String) {
    console.log(profileId)
    this.router.navigate(['view-profile', profileId]);
  }

  updateProfile(profileId: String) {
    this.router.navigate(['update-profile', profileId]);
  }

  getHealthAdvice(profileId: String) {
    this.router.navigate(['health-advice', profileId]);
  }


}
