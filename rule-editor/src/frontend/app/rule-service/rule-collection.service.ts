import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class RuleCollectionService {

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<any> {
    return this.http.get('/rest/rule-collections');
  }

  get(id: string): any {
    return this.http.get('/rest/rule-collections/' + id);
  }

  update(ruleCollection: any, ruleCollectionId: string): Observable<any> {
    return this.http.put('/rest/rule-collections/' + ruleCollectionId, ruleCollection);
  }

  create(ruleCollection: any): any {
    return this.http.post('/rest/rule-collections', ruleCollection);
  }

  remove(ruleCollectionId: string) {
    return this.http.delete('/rest/rule-collections/' + ruleCollectionId);
  }
}
