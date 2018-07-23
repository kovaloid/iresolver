import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class RuleService {

constructor(private http: HttpClient) {
  }

  getAll(): Observable<any> {
    return this.http.get('//localhost:8080/rest/rules');
  }

  save(rule_categories: Array<any>): Observable<any> {
    return this.http.post('//localhost:8080/rest/rules', rule_categories);
  }
}
