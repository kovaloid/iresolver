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
      id: null,
      pack:'com.koval.jresolver.rules',
      imports: ['com.koval.jresolver.connector.bean.JiraIssue;', 'com.koval.jresolver.connector.bean.JiraStatus;'],
      globals: ['com.koval.jresolver.rules.results.RulesResult results'],
      name:'NewRuleCollection',
      rules: []
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

  updateRules() {
    this.ruleCollectionService.getAll().subscribe(data => {
      this.ruleCollections = data;
    });
  }
}
