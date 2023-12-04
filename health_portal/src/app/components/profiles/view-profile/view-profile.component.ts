import { Component } from '@angular/core';
import { Profile } from "./../../../documents/profile";
import { ProfileService } from "./../../../service/profile.service";
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-view-profile',
  templateUrl: './view-profile.component.html',
  styleUrls: ['./view-profile.component.css']
})
export class ViewProfileComponent {

  profileId: String;
  profile: Profile;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private profileService: ProfileService) { }

  ngOnInit() {
    this.profile = new Profile();
    this.profileId = this.route.snapshot.params['profileId'];

    this.profileService.getProfile(this.profileId)
      .subscribe(data => {
        console.log(data)
        this.profile = data;
      }, error => console.log(error));
  }

}