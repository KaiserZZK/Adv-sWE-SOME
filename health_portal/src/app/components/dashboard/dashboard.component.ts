import { Component } from '@angular/core';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})

export class DashboardComponent {

  message: String;

  constructor(
    private service: AuthService
  ) { }

  ngOnInit() {
    this.hello();
  }

  hello() {
    this.service.hello().subscribe((response) => {
      console.log(response);
      this.message = response.message;
    })
  }

}