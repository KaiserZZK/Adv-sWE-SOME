import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-users',
  templateUrl: './register.component.html',
  styleUrls: ['./users.component.css']
})
export class RegisterComponent {

  constructor(private router: Router) { }

  handleClick() {
    this.router.navigate(['/analytics'])
  }

}
