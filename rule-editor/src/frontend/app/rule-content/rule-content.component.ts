import { Component, EventEmitter, Input, Output, OnChanges, SimpleChanges } from '@angular/core'
import {FormArray, FormControl, FormGroup} from '@angular/forms';

import { RuleCollectionService } from '../rule-service/rule-collection.service';

@Component({
  selector: 'rule-content',
  templateUrl: './rule-content.component.html',
  styleUrls: ['./rule-content.component.css']
})
export class RuleContentComponent implements OnChanges {

  rule: any;
  ruleId: string = '-1';
  ruleName: string;
  @Input() selectedRuleCollection: any;
  @Output() updated = new EventEmitter<boolean>();

  constructor(private ruleCollectionService: RuleCollectionService) { }

  ngOnChanges(changes: SimpleChanges) {

    //console.log(this.selectedRuleCollection);

    console.log('changes');
    /*if (this.ruleId != 'new') {
      this.ruleCollectionService.get(changes.ruleId.currentValue).subscribe(data => {
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
    }*/
  }

  save() {
    if (this.ruleId != 'new') {
      //this.ruleCollectionService.save(this.rule, this.ruleId).subscribe(result => {
      //  this.rule = result;
      //  this.updated.emit();
     // });
      } else {
     // this.ruleCollectionService.saveNew(this.rule).subscribe(result => {
     //   this.rule = result;
     //   this.updated.emit();
     //   console.log(result);
     // });
    }

    console.log(this.selectedRuleCollection);

    this.ruleCollectionService.update(this.selectedRuleCollection, this.selectedRuleCollection.id).subscribe(data => {
      console.log(data);
    });
  }

  revertChanges(){
    this.ruleCollectionService.get(this.ruleId).subscribe(data => {
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

  changeButtonValue(id: string) {
    let currentValue: string = document.getElementById(id).innerHTML;
    if (currentValue == "Show"){
      document.getElementById(id).innerHTML = "Hide";
    } else {
      document.getElementById(id).innerHTML = "Show";
    }
  }

  deleteRule() {
    console.log('ne ok');
    this.ruleCollectionService.remove(this.ruleId).subscribe(result => {
      this.ruleId = '0';
    });
    this.updated.emit();
  }
}
