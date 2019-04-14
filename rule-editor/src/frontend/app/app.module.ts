import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { RuleListComponent } from './rule-list/rule-list.component';
import { RuleContentComponent } from './rule-content/rule-content.component';
import { RuleCollectionService } from './rule-service/rule-collection.service';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  declarations: [
    AppComponent,
    RuleListComponent,
    RuleContentComponent
  ],
  providers: [ RuleCollectionService ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }
