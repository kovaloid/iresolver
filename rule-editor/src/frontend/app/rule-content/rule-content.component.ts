import { Component, EventEmitter, Input, Output, OnChanges, SimpleChanges } from '@angular/core'
import {FormArray, FormControl, FormGroup} from '@angular/forms';

import { RuleCollectionService } from '../rule-service/rule-collection.service';

@Component({
  selector: 'rule-content',
  templateUrl: './rule-content.component.html',
  styleUrls: ['./rule-content.component.css']
})
export class RuleContentComponent implements OnChanges {

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
    //if (this.ruleId != 'new') {
      //this.ruleCollectionService.save(this.rule, this.ruleId).subscribe(result => {
      //  this.rule = result;
      //  this.updated.emit();
     // });
    //  } else {
     // this.ruleCollectionService.saveNew(this.rule).subscribe(result => {
     //   this.rule = result;
     //   this.updated.emit();
     //   console.log(result);
     // });
   // }

    if (this.selectedRuleCollection.id === null) {
      this.ruleCollectionService.create(this.selectedRuleCollection).subscribe(data => {
        console.log(data);
      });
    } else {
      this.ruleCollectionService.update(this.selectedRuleCollection).subscribe(data => {
        console.log(data);
      });
    }
  }

  addRule() {
    this.selectedRuleCollection.rules.push({
      name: '',
      conditions: [],
      recommendations: []
    });
  }

  revertChanges(){
    this.ruleCollectionService.get(this.selectedRuleCollection.id).subscribe(data => {
      this.selectedRuleCollection = data;
    });
  }

  deleteCondition(ruleIndex: number, conditionIndex: number) {
    this.selectedRuleCollection.rules[ruleIndex].conditions.splice(conditionIndex, 1);
  }

  addCondition(ruleIndex: number) {
    this.selectedRuleCollection.rules[ruleIndex].conditions.push('');
  }

/*  deleteAttribute(i: number) {
    this.selectedRuleCollection.rules[0].attributes.splice(i, 1);
  }

  addAttribute() {
    this.selectedRuleCollection.rules[0].attributes.push('');
  }*/

  deleteRecommendation(ruleIndex: number, recommendationIndex: number) {
    this.selectedRuleCollection.rules[ruleIndex].recommendations.splice(recommendationIndex, 1);
  }

  addRecommendation(ruleIndex: number) {
    this.selectedRuleCollection.rules[ruleIndex].recommendations.push('');
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
    this.ruleCollectionService.remove(this.selectedRuleCollection.id).subscribe(result => {
      console.log(result);
    });
    this.updated.emit();
  }
}
