import { Component } from '@angular/core';
import { Router } from "@angular/router";

@Component({
  selector: 'app-users',
  templateUrl: './login.component.html',
  styleUrls: ['./users.component.css']
})
export class LoginComponent {

  constructor(private router: Router) { }

  handleClick() {
    this.router.navigate(['/analytics'])
  }

}
