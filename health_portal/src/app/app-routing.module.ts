import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';

// import { ProfilesComponent } from "./profiles/profiles.component";
// import { PrescriptionComponent } from "./prescription/prescription.component";
// import { ConsentComponent } from "./consent/consent.component";
// import { AnalyticsComponent } from "./analytics/analytics.component";

const routes: Routes = [
  { path: 'signup', component: SignupComponent },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent },
];

// const routes: Routes = [{
//     path: 'users/auth',
//     loadChildren: () =>
//       import('./users/users.module').then(
//         (module) => module.UsersModule
//       ),
//   },
//   {
//     path: 'profiles',
//     component: ProfilesComponent
//   },
//   {
//     path: 'prescription',
//     component: PrescriptionComponent
//   },
//   {
//     path: 'consent',
//     component: ConsentComponent
//   },
//   {
//     path: 'analytics',
//     component: AnalyticsComponent
//   }
// ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
