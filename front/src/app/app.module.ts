import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './app.component';
import {AppRoutingModule} from "./app-routing.module";
import {HomeComponent} from './home/home.component';
import {HttpClientModule} from "@angular/common/http";
import { FormsModule } from '@angular/forms';
import {MatInputModule} from "@angular/material/input";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import {MatCardModule } from '@angular/material/card';
import {MatBadgeModule} from "@angular/material/badge";
import {MatTableModule} from "@angular/material/table";

const routes: Routes = [];
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent
  ],
    imports: [
        AppRoutingModule,
        BrowserModule,
        HttpClientModule,
        RouterModule.forRoot(routes),
        MatInputModule,
        MatSlideToggleModule,
        FormsModule,
        BrowserAnimationsModule,
        MatButtonModule,
        MatIconModule,
        MatCardModule,
        MatBadgeModule,
        MatTableModule
    ],
  bootstrap: [AppComponent]
})
export class AppModule { }
