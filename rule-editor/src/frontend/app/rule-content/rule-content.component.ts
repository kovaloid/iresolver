import { Component, EventEmitter, Input, Output, OnChanges, SimpleChanges } from '@angular/core'

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
    console.log('Change selected rule collection: ', changes.selectedRuleCollection.currentValue);
  }

  save() {
    if (this.selectedRuleCollection.id === null) {
      this.ruleCollectionService.create(this.selectedRuleCollection).subscribe(data => {
        console.log(data);
        this.updated.emit();
      });
    } else {
      this.ruleCollectionService.update(this.selectedRuleCollection).subscribe(data => {
        console.log(data);
        this.updated.emit();
      });
    }
  }

  addRule() {
    this.selectedRuleCollection.rules.push({
      name: '',
      attributes: [],
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

  deleteAttribute(ruleIndex: number, attributeIndex: number) {
    this.selectedRuleCollection.rules[ruleIndex].attributes.splice(attributeIndex, 1);
  }

  addAttribute(ruleIndex: number) {
    this.selectedRuleCollection.rules[ruleIndex].attributes.push('');
  }

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
    this.ruleCollectionService.remove(this.selectedRuleCollection.id).subscribe(result => {
      console.log(result);
    });
    this.updated.emit();
  }
}
