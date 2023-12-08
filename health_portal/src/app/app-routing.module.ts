import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './components/signup/signup.component';
import { LoginComponent } from './components/login/login.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';

import { CreateProfileComponent } from './components/profiles/create-profile/create-profile.component';
import { ViewProfileComponent } from './components/profiles/view-profile/view-profile.component';
import { UpdateProfileComponent } from './components/profiles/update-profile/update-profile.component';
import { ListProfilesComponent } from './components/profiles/list-profiles/list-profiles.component';

import { CreatePrescriptionComponent } from './components/prescription/create-prescription/create-prescription.component';
import { ViewPrescriptionComponent } from './components/prescription/view-prescription/view-prescription.component';
import { UpdatePrescriptionComponent } from './components/prescription/update-prescription/update-prescription.component';
import { ListPrescriptionComponent } from './components/prescription/list-prescription/list-prescription.component';

import { CreateConsentComponent } from "./components/consent/create-consent/create-consent.component";
import { UpdateConsentComponent } from "./components/consent/update-consent/update-consent.component";
import { ShowConsentComponent } from "./components/consent/show-consent/show-consent.component";

import { HealthAdviceComponent } from './components/analytics/health-advice/health-advice.component';
import { AuthGuard } from "./auth/auth.guard";


const routes: Routes = [
  { path: 'signup',         component: SignupComponent        },
  { path: 'login',          component: LoginComponent         },
  { path: 'dashboard',      component: DashboardComponent, canActivate: [AuthGuard]     },
  { path: 'list-profiles',  component: ListProfilesComponent, canActivate: [AuthGuard]  },
  { path: 'create-profile', component: CreateProfileComponent, canActivate: [AuthGuard] },
  { path: 'view-profile/:profileId',    component: ViewProfileComponent, canActivate: [AuthGuard]   },
  { path: 'update-profile/:profileId',  component: UpdateProfileComponent, canActivate: [AuthGuard] },
  { path: 'list-prescription/:profileId',   component: ListPrescriptionComponent, canActivate: [AuthGuard]    },
  { path: 'create-prescription/:profileId', component: CreatePrescriptionComponent, canActivate: [AuthGuard]  },
  { path: 'view-prescription/:profileId/:prescriptionId',    component: ViewPrescriptionComponent, canActivate: [AuthGuard]   },
  { path: 'update-prescription/:profileId/:prescriptionId',  component: UpdatePrescriptionComponent, canActivate: [AuthGuard] },
  { path: 'health-advice/:profileId',   component: HealthAdviceComponent, canActivate: [AuthGuard]  },
  { path: 'create-consent',      component: CreateConsentComponent, canActivate: [AuthGuard]        },
  { path: 'update-consent',      component: UpdateConsentComponent, canActivate: [AuthGuard]        },
  { path: 'show-consent',        component: ShowConsentComponent, canActivate: [AuthGuard]          }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
