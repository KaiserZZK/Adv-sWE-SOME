import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AnalyticsService } from "./../../../service/analytics.service";

@Component({
  selector: 'app-health-advice',
  templateUrl: './health-advice.component.html',
  styleUrls: ['./health-advice.component.css']
})

export class HealthAdviceComponent {

  profileId: String;
  advice: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private analyticsService: AnalyticsService) { }

  ngOnInit() {
    this.profileId = this.route.snapshot.params['profileId'];

    this.analyticsService.getHealthAdvice(this.profileId)
      .subscribe(data => {
        this.advice = data;
        console.log(data)
      }, error => console.log(error));
  }

}