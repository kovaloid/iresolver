import { Component, OnInit } from '@angular/core';

import { RuleCollectionService } from './rule-collection.service';

@Component({
  selector: 'rule-collection',
  templateUrl: './rule-collection.component.html',
  styleUrls: ['./rule-collection.component.css']
})
export class RuleCollectionComponent implements OnInit {

  ruleCollections: Array<any>;

  constructor(private ruleCollectionService: RuleCollectionService) { }

  ngOnInit() {
    this.ruleCollectionService.getAll().subscribe(data => {
      this.ruleCollections = data;
    });
  }
}
