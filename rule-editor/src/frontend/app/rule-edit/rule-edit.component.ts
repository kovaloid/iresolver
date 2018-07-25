import { Component, EventEmitter, Input, Output, OnChanges, SimpleChanges } from '@angular/core'
import {FormArray, FormControl, FormGroup} from '@angular/forms';

import { RuleService } from '../rule/rule.service';

@Component({
  selector: 'rule-edit',
  templateUrl: './rule-edit.component.html',
  styleUrls: ['./rule-edit.component.css']
})
export class RuleEditComponent implements OnChanges{

rule : any;
@Input()ruleId: string = '-1';
@Input()ruleName: string;
@Output() updated = new EventEmitter<boolean>();

constructor(private ruleService: RuleService) { }

  ngOnChanges(changes: SimpleChanges) {
console.log('changes');
  if (this.ruleId != 'new') {
    this.ruleService.get(changes.ruleId.currentValue).subscribe(data => {
      this.rule = data;
    });
  } else {
    this.rule = {
      name:	"New rule",
      location:	null,
      conditions: [],
      recommendations: ['results.putAdvice(\"Your advice");'],
      file:	"defaullt.drl",
      attributes:	[]
      };
  }
}

  save() {

if (this.ruleId != 'new') {
    this.ruleService.save(this.rule, this.ruleId).subscribe(result => {
      this.rule = result;
      this.updated.emit();
    });
} else {
    this.ruleService.saveNew(this.rule).subscribe(result => {
      this.rule = result;
      this.updated.emit();
      console.log(result);
    });
  }

}

  revertChanges(){
    this.ruleService.get(this.ruleId).subscribe(data => {
      this.rule = data;
    });
  }

  deleteCondition(i: number) {
    this.rule.conditions.splice(i, 1);
  }

  addCondition() {
    this.rule.conditions.push('');
  }

  deleteAttribute(i: number) {
    this.rule.attributes.splice(i, 1);
  }

  addAttribute() {
    this.rule.attributes.push('');
  }

  deleteRecommendation(i: number) {
    this.rule.recommendations.splice(i, 1);
  }

  addRecommendation() {
    this.rule.recommendations.push('');
  }

  changeButtonValue(id: string){
    let currentValue: string = document.getElementById(id).innerHTML;
    if (currentValue == "Show"){
      document.getElementById(id).innerHTML = "Hide";
    } else {
      document.getElementById(id).innerHTML = "Show";
    }
  }

  deleteRule(){
console.log('ne ok');
    this.ruleService.remove(this.ruleId).subscribe(result => {
      this.ruleId = '0';
    });
this.updated.emit();
  }
}
