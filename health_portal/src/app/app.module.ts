import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AnalyticsComponent } from './analytics/analytics.component';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { CreateProfileComponent } from './components/profiles/create-profile/create-profile.component';
import { ViewProfileComponent } from './components/profiles/view-profile/view-profile.component';
import { UpdateProfileComponent } from './components/profiles/update-profile/update-profile.component';
import { ListProfilesComponent } from './components/profiles/list-profiles/list-profiles.component';
import { CreatePrescriptionComponent } from "./prescription/create-prescription/create-prescription.component";
import { ListPrescriptionComponent } from './prescription/list-prescription/list-prescription.component';
import { UpdateConsentComponent } from './consent/update-consent/update-consent.component';
import { ShowConsentComponent } from './consent/show-consent/show-consent.component';
import { CreateConsentComponent } from './consent/create-consent/create-consent.component';


@NgModule({
  declarations: [
    AppComponent,
    CreatePrescriptionComponent,
    AnalyticsComponent,
    SignupComponent,
    LoginComponent,
    DashboardComponent,
    CreateProfileComponent,
    ViewProfileComponent,
    UpdateProfileComponent,
    ListProfilesComponent,
    ListPrescriptionComponent,
    UpdateConsentComponent,
    ShowConsentComponent,
    CreateConsentComponent,
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
