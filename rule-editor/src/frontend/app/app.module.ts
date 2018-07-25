import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { RuleListComponent } from './rule-list/rule-list.component';
import { RuleEditComponent } from './rule-edit/rule-edit.component';
import { RuleService } from './rule/rule.service';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  declarations: [
    AppComponent,
    RuleListComponent,
    RuleEditComponent
  ],
  providers: [ RuleService ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
