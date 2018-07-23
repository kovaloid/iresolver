import { Component, OnInit } from '@angular/core';

import { RuleService } from '../rule/rule.service';

@Component({
  selector: 'rule-list',
  templateUrl: './rule-list.component.html',
  styleUrls: ['./rule-list.component.css']
})
export class RuleListComponent implements OnInit {
name: string = 'dfgdfgg';
rule_categories: Array<any> = [
{
name: "string",
rules: [
{
id: 1,
name: 'name1',
conditions: ['con11', 'con12'],
recommendations: ['rec11', 'rec12'],
attributes: ['atr11', 'atr12']
},
{
id: 2,
name: 'name2',
conditions: ['con21', 'con22'],
recommendations: ['rec21', 'rec22'],
attributes: ['atr21', 'atr22']
}
]
},
{
name: "string2",
rules: [
{
id: 3,
name: 'name3',
conditions: ['con31', 'con32'],
recommendations: ['rec31', 'rec32'],
attributes: ['atr31', 'atr32']
},
{
id: 4,
name: 'name4',
conditions: ['con41', 'con42'],
recommendations: ['rec41', 'rec42'],
attributes: ['atr41', 'atr42']
}
]
}
];

chosenRule: any;

constructor(private ruleService: RuleService) { }

  ngOnInit() {
    this.ruleService.getAll().subscribe(data => {
      this.rule_categories = data;
    });
  }

  save() {
    this.ruleService.saveAll(this.rule_categories).subscribe(result => {
      this.rule_categories = result;
    });
  }

  changeChosenRule(rule: any) {
    this.chosenRule = rule;
  this.save();
  }
}
