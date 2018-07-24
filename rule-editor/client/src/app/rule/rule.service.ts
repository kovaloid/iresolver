import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class RuleService {

constructor(private http: HttpClient) {
  }

  getAll(): Observable<any> {
    return this.http.get('/rest/rules');
  }

  saveAll(rule: any): Observable<any> {
    return this.http.post('/rest/rules', rule);
  }

  get(id: string): any {
    return this.http.get('/rest/rules/' + id);
  }

  save(rule: any, ruleId: string): Observable<any> {
      return this.http.put('/rest/rules/' + ruleId, rule);
  }

  remove(id: string) {
    return this.http.delete('/rest/rules/' + id);
  }
}
