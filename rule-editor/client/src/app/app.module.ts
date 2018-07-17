import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { RuleListComponent } from './rule-list/rule-list.component';

import { RuleService } from './rule/rule.service';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    RuleListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [RuleService],
  bootstrap: [AppComponent]
})
export class AppModule { }
