import { NgModule } from '@angular/core';

import { UsersRoutingModule } from './users-routing.module';
import { LoginComponent } from './login.component';
import { RegisterComponent } from './register.component';
import { HomeComponent } from "./home.component";
import { UsersComponent } from "./users.component";


@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    HomeComponent,
    UsersComponent
  ],
  imports: [
    UsersRoutingModule,
  ]
})
export class UsersModule { }
