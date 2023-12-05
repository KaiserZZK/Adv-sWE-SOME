import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ConsentComponent } from './consent/consent.component';
import { PrescriptionComponent } from './prescription/prescription.component';
import { AnalyticsComponent } from './analytics/analytics.component';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CreateProfileComponent } from './components/profiles/create-profile/create-profile.component';
import { ViewProfileComponent } from './components/profiles/view-profile/view-profile.component';
import { UpdateProfileComponent } from './components/profiles/update-profile/update-profile.component';
import { ListProfilesComponent } from './components/profiles/list-profiles/list-profiles.component';
import { HealthAdviceComponent } from './components/analytics/health-advice/health-advice.component';
import { CreatePrescriptionComponent } from './components/prescription/create-prescription/create-prescription.component';
import { ListPrescriptionComponent } from './components/prescription/list-prescription/list-prescription.component';
import { UpdatePrescriptionComponent } from './components/prescription/update-prescription/update-prescription.component';
import { ViewPrescriptionComponent } from './components/prescription/view-prescription/view-prescription.component';


@NgModule({
  declarations: [
    AppComponent,
    ConsentComponent,
    PrescriptionComponent,
    AnalyticsComponent,
    SignupComponent,
    LoginComponent,
    DashboardComponent,
    CreateProfileComponent,
    ViewProfileComponent,
    UpdateProfileComponent,
    ListProfilesComponent,
    HealthAdviceComponent,
    CreatePrescriptionComponent,
    ListPrescriptionComponent,
    UpdatePrescriptionComponent,
    ViewPrescriptionComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
