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

import { HealthAdviceComponent } from './components/analytics/health-advice/health-advice.component';

import { PrescriptionComponent } from './prescription/prescription.component';
import { ConsentComponent } from './consent/consent.component';


const routes: Routes = [
  { path: 'signup',         component: SignupComponent        },
  { path: 'login',          component: LoginComponent         },
  { path: 'dashboard',      component: DashboardComponent     },
  { path: 'list-profiles',  component: ListProfilesComponent  },
  { path: 'create-profile', component: CreateProfileComponent },
  { path: 'view-profile/:profileId',    component: ViewProfileComponent   },
  { path: 'update-profile/:profileId',  component: UpdateProfileComponent },
  { path: 'list-prescription/:profileId',   component: ListPrescriptionComponent    },
  { path: 'create-prescription',            component: CreatePrescriptionComponent  },
  { path: 'view-prescription/:profileId/:prescriptionId',    component: ViewPrescriptionComponent   },
  { path: 'update-prescription/:profileId/:prescriptionId',  component: UpdatePrescriptionComponent },
  { path: 'health-advice/:profileId',   component: HealthAdviceComponent  },
  { path: 'consent',        component: ConsentComponent       }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
