import { Component, OnInit } from '@angular/core';

import { RuleService } from '../rule/rule.service';

@Component({
  selector: 'rule-list',
  templateUrl: './rule-list.component.html',
  styleUrls: ['./rule-list.component.css']
})
export class RuleListComponent implements OnInit {

rules: Array<any>;
chosenRule: any;

constructor(private ruleService: RuleService) { }

  ngOnInit() {
    this.ruleService.getAll().subscribe(data => {
      this.rules = data;
    });
  }

  changeChosenRule(rule: any) {
    this.chosenRule = rule;
  }

  createRule() {
    this.rules.push(this.rules[0]);
    this.ruleService.saveAll(this.rules[this.rules.length - 1]).subscribe(result => {
      this.rules = result;
    });
  }

  changeButtonValue(id: string){
    let currentValue: string = document.getElementById(id).innerHTML;
    if (currentValue == "Show"){
      document.getElementById(id).innerHTML = "Hide";
    } else {
      document.getElementById(id).innerHTML = "Show";
    }
  }

  testFunc(id: string){
    for (let key in document.getElementById(id)){
      console.log(key + " : " + typeof(key));
    }
  }

  setRuleEditorVisible(){
    document.getElementById("open-rule1").style.visibility = "visible";
  }
}
