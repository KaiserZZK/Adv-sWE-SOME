import { Component } from '@angular/core';
import { Observable } from "rxjs";
import { Profile } from "./../../../documents/profile";

@Component({
  selector: 'app-list-profiles',
  templateUrl: './list-profiles.component.html',
  styleUrls: ['./list-profiles.component.css']
})

export class ListProfilesComponent {

  profiles: Observable<Profile[]>;

  constructor (

  ) {}

  ngOnInit() {
    this.profiles = null;

  }

}
