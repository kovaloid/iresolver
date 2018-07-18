import { Component, OnInit } from '@angular/core';

import { RuleService } from '../rule/rule.service';

@Component({
  selector: 'rule-list',
  templateUrl: './rule-list.component.html',
  styleUrls: ['./rule-list.component.css']
})
export class RuleListComponent implements OnInit {
rule_categories: Array<any>;

constructor(private ruleService: RuleService) { }

  ngOnInit() {
    this.ruleService.getAll().subscribe(data => {
      this.rule_categories = data;
    });
  }

  save() {
    this.ruleService.save(this.rule_categories).subscribe(result => {
      this.rule_categories = result;
    });
  }
}
