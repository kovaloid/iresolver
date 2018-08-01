import { Component, OnInit } from '@angular/core';

import { RuleCollectionService } from '../rule-service/rule-collection.service';

@Component({
  selector: 'rule-list',
  templateUrl: './rule-list.component.html',
  styleUrls: ['./rule-list.component.css']
})
export class RuleListComponent implements OnInit {

  ruleCollections: Array<any>;
  chosenRule: any;

  constructor(private ruleCollectionService: RuleCollectionService) { }

  ngOnInit() {
    this.ruleCollectionService.getAll().subscribe(data => {
      this.ruleCollections = data;
    });
  }

  changeChosenRule(rule: any) {
    this.chosenRule = rule;
  }

  createRule() {
    this.chosenRule = {
      name: '',
      file: '',
      globals: []
    };
  }

  changeButtonValue(id: string) {
    let currentValue: string = document.getElementById(id).innerHTML;
    if (currentValue == "Show"){
      document.getElementById(id).innerHTML = "Hide";
    } else {
      document.getElementById(id).innerHTML = "Show";
    }
  }

  setRuleEditorVisible(){
    document.getElementById("open-rule1").style.visibility = "visible";
  }

  updateRules() {
    this.ruleCollectionService.getAll().subscribe(data => {
      this.ruleCollections = data;
    });
  }
}
