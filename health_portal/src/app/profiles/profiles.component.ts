import { Component } from '@angular/core';
import { ProfileService } from 'src/app/service/profile.service';

@Component({
  selector: 'app-profiles',
  templateUrl: './profiles.component.html',
  styleUrls: ['./profiles.component.css']
})
export class ProfilesComponent {

  message: String;

  constructor(
    private service: ProfileService
  ) { }

  ngOnInit() {
    this.getProfiles();

  }

  getProfiles() {
    this.service.getProfiles().subscribe((response) => {
      console.log(response);
      this.message = response.message;
    })
  }

  // TODO 1: display a user's Profiles as a List 

  // TODO 2: give a "Add Profile" button

}