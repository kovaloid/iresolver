webpackJsonp(["main"],{

/***/ "./src/frontend/$$_lazy_route_resource lazy recursive":
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncatched exception popping up in devtools
	return Promise.resolve().then(function() {
		throw new Error("Cannot find module '" + req + "'.");
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./src/frontend/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/frontend/app/app.component.css":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/frontend/app/app.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\r\n  <div class=\"py-5 text-center\">\r\n    <img class=\"d-block mx-auto mb-4\" src=\"https://getbootstrap.com/assets/brand/bootstrap-solid.svg\" alt=\"\" width=\"72\" height=\"2\">\r\n    <h2>Jresolver Rule Editor</h2>\r\n    <p class=\"lead\">Rule editor for Jresolver</p>\r\n  </div>\r\n\r\n  <rule-list></rule-list>\r\n\r\n  <footer class=\"my-5 pt-5 text-muted text-center text-small\">\r\n    <p class=\"mb-1\">&copy; 2018 Jresolver</p>\r\n    <ul class=\"list-inline\">\r\n      <!-- Ссылки на соответственно настройки и about. -->\r\n      <li class=\"list-inline-item\"><a href=\"#\">Settings</a></li>\r\n      <li class=\"list-inline-item\"><a href=\"#\">About</a></li>\r\n    </ul>\r\n  </footer>\r\n</div>\r\n"

/***/ }),

/***/ "./src/frontend/app/app.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var AppComponent = /** @class */ (function () {
    function AppComponent() {
        this.title = 'app';
    }
    AppComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: 'app-root',
            template: __webpack_require__("./src/frontend/app/app.component.html"),
            styles: [__webpack_require__("./src/frontend/app/app.component.css")]
        })
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/frontend/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__ = __webpack_require__("./node_modules/@angular/platform-browser/esm5/platform-browser.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("./node_modules/@angular/forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__app_component__ = __webpack_require__("./src/frontend/app/app.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__rule_list_rule_list_component__ = __webpack_require__("./src/frontend/app/rule-list/rule-list.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__rule_edit_rule_edit_component__ = __webpack_require__("./src/frontend/app/rule-edit/rule-edit.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__rule_rule_service__ = __webpack_require__("./src/frontend/app/rule/rule.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};








var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["E" /* NgModule */])({
            imports: [
                __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser__["a" /* BrowserModule */],
                __WEBPACK_IMPORTED_MODULE_2__angular_forms__["a" /* FormsModule */],
                __WEBPACK_IMPORTED_MODULE_3__angular_common_http__["b" /* HttpClientModule */]
            ],
            declarations: [
                __WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* AppComponent */],
                __WEBPACK_IMPORTED_MODULE_5__rule_list_rule_list_component__["a" /* RuleListComponent */],
                __WEBPACK_IMPORTED_MODULE_6__rule_edit_rule_edit_component__["a" /* RuleEditComponent */]
            ],
            providers: [__WEBPACK_IMPORTED_MODULE_7__rule_rule_service__["a" /* RuleService */]],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* AppComponent */]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/frontend/app/rule-edit/rule-edit.component.css":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/frontend/app/rule-edit/rule-edit.component.html":
/***/ (function(module, exports) {

module.exports = "<hr class=\"mb-4\">\r\n<h4 class=\"mb-3\">Name</h4>\r\n<input type=\"text\" class=\"form-control\" [(ngModel)]=\"rule.name\"><br>\r\n\r\n<h4 class=\"mb-3\">FileName</h4>\r\n<input type=\"text\" class=\"form-control\" [(ngModel)]=\"rule.file\"><br>\r\n\r\n  <h4 class=\"mb-3 float-left\">Attributes</h4>\r\n  <button type=\"button\" id=\"show1\" class=\"btn  btn-info btn-outline-secondary btn-sm float-left btn-space\" data-toggle=\"collapse\" data-target=\"#hide-attributes\">Show</button><br><br>\r\n  <form class=\"needs-validation\" novalidate>\r\n    <div id=\"hide-attributes\" class=\"collapse in\">\r\n      <div class=\"row\" *ngFor=\"let attribute of rule.attributes; let i = index\">\r\n        <div class=\"col-md-11 \">\r\n          <input type=\"text\" class=\"form-control\" [value]=\"rule.attributes[i]\" (keyup.enter)=\"rule.attributes[i] = $event.target.value\" required>\r\n        </div>\r\n        <button type=\"button\" class=\"btn btn-outline-danger btn-sm  btn-space1\" (click)=\"deleteAttribute(i)\">&times;</button><br>\r\n      </div>\r\n      <!-- Кнопка добавления новых атрибутов. -->\r\n      <br><button type=\"button\" class=\"btn btn-primary btn-sm float-right btn-space\" (click)=\"addAttribute()\">+</button><br>\r\n    </div>\r\n\r\n    <hr class=\"mb-4\">\r\n    <h4 class=\"mb-3\">Conditions</h4>\r\n\r\n    <div class=\"row\" *ngFor=\"let condition of rule.conditions; let i = index\">\r\n      <div class=\"col-md-11 \" ngModelGroup=\"rule.conditions\">\r\n        <input type=\"text\" class=\"form-control\" [value]=\"rule.conditions[i]\" (keyup.enter)=\"rule.conditions[i] = $event.target.value\" required>\r\n        <div class=\"invalid-feedback\">\r\n          Valid condition is required.\r\n        </div>\r\n      </div>\r\n      <button type=\"button\" class=\"btn btn-outline-danger btn-sm  btn-space1\" (click)=\"deleteCondition(i)\">&times;</button><br>\r\n    </div>\r\n\r\n    <!-- Кнопка добавления нового условия. -->\r\n    <br><button type=\"button\" class=\"btn btn-primary btn-sm float-right btn-space\" (click)=\"addCondition()\">+</button><br>\r\n\r\n    <hr class=\"mb-4\">\r\n    <h4 class=\"mb-3\">Recommendations</h4>\r\n\r\n    <div class=\"row\" *ngFor=\"let recommendation of rule.recommendations; let i = index\">\r\n      <div class=\"col-md-11 \">\r\n        <!-- Здесь снова лучше ng-repeat'ом выводить поля с рекомендациями. -->\r\n        <input type=\"text\" class=\"form-control\" [value]=\"rule.recommendations[i]\" (keyup.enter)=\"rule.recommendations[i] = $event.target.value\" required>\r\n        <div class=\"invalid-feedback\">\r\n          Valid recommendation is required.\r\n        </div>\r\n      </div>\r\n      <button type=\"button\" class=\"btn btn-outline-danger btn-sm  btn-space1\" (click)=\"deleteRecommendation(i)\">&times;</button><br>\r\n    </div>\r\n\r\n    <!-- Кнопка добавления новых рекомендаций. -->\r\n    <br><button type=\"button\" class=\"btn btn-primary btn-sm float-right btn-space\" (click)=\"addRecommendation()\">+</button><br>\r\n\r\n    <hr class=\"mb-4\">\r\n    <!-- Кнопка сохранения. -->\r\n    <button class=\"btn btn-primary btn-lg btn-space float-right\" type=\"submit\" (click)=\"save()\">Save</button>\r\n  </form>\r\n  <!-- Кнопка удаления правила. -->\r\n  <button class=\"btn btn-primary btn-lg btn-space float-right\" type=\"submit\" (click)=\"revertChanges()\">Cancel</button>\r\n\r\n"

/***/ }),

/***/ "./src/frontend/app/rule-edit/rule-edit.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RuleEditComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__rule_rule_service__ = __webpack_require__("./src/frontend/app/rule/rule.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var RuleEditComponent = /** @class */ (function () {
    function RuleEditComponent(ruleService) {
        this.ruleService = ruleService;
        this.ruleId = '-1';
    }
    RuleEditComponent.prototype.ngOnChanges = function (changes) {
        var _this = this;
        this.ruleService.get(changes.ruleId.currentValue).subscribe(function (data) {
            _this.rule = data;
        });
    };
    RuleEditComponent.prototype.save = function () {
        var _this = this;
        this.ruleService.save(this.rule, this.ruleId).subscribe(function (result) {
            _this.rule = result;
        });
    };
    RuleEditComponent.prototype.revertChanges = function () {
        var _this = this;
        this.ruleService.get(this.ruleId).subscribe(function (data) {
            _this.rule = data;
        });
    };
    RuleEditComponent.prototype.deleteCondition = function (i) {
        this.rule.conditions.splice(i, 1);
    };
    RuleEditComponent.prototype.addCondition = function () {
        this.rule.conditions.push('');
    };
    RuleEditComponent.prototype.deleteAttribute = function (i) {
        this.rule.attributes.splice(i, 1);
    };
    RuleEditComponent.prototype.addAttribute = function () {
        this.rule.attributes.push('');
    };
    RuleEditComponent.prototype.deleteRecommendation = function (i) {
        this.rule.recommendations.splice(i, 1);
    };
    RuleEditComponent.prototype.addRecommendation = function () {
        this.rule.recommendations.push('');
    };
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["z" /* Input */])(),
        __metadata("design:type", String)
    ], RuleEditComponent.prototype, "ruleId", void 0);
    __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["z" /* Input */])(),
        __metadata("design:type", String)
    ], RuleEditComponent.prototype, "ruleName", void 0);
    RuleEditComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: 'rule-edit',
            template: __webpack_require__("./src/frontend/app/rule-edit/rule-edit.component.html"),
            styles: [__webpack_require__("./src/frontend/app/rule-edit/rule-edit.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__rule_rule_service__["a" /* RuleService */]])
    ], RuleEditComponent);
    return RuleEditComponent;
}());



/***/ }),

/***/ "./src/frontend/app/rule-list/rule-list.component.css":
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/frontend/app/rule-list/rule-list.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"row\">\r\n\r\n  <div class=\"col-md-3 text-center\">\r\n    <!-- Здесь создание правил кнопкой + и вывод кнопок для правил, удобнее наверное через ng-repeat.\r\n    Если создаете методы с другими названиями, меняйте здесь тоже. -->\r\n\r\n    <button type=\"button\" class=\"btn btn-primary btn-block btn-space\" (click)=\"ngOnInit()\">+</button><br>\r\n    <hr class=\"mb-4\">\r\n\r\n    <div *ngFor=\"let rule of rules\">\r\n    <button type=\"button\" id=\"show\" class=\"btn  btn-info btn-outline-secondary btn-sm float-left btn-space\" data-toggle=\"collapse\" data-target=\"#rule.name\">Show</button>\r\n    </div>\r\n    <h3 class=\"mb-3 float-left\">Rules</h3>\r\n\r\n    <div class=\"btn-group btn-group-sm\" >\r\n      <div class=\"btn-group\">\r\n        <button type=\"button\" class=\"btn btn-outline-primary btn-sm float-right btn-space\" (click)=\"createRule()\">+</button>\r\n      </div>\r\n      <div class=\"btn-group\">\r\n        <button type=\"button\" class=\"btn btn-info btn-outline-danger btn-sm float-right btn-space\" data-toggle=\"modal\" data-target=\"#deleteModal\">&times;</button>\r\n      </div>\r\n    </div>\r\n\r\n    <div class=\"modal fade\" id=\"deleteModal\" role=\"dialog\">\r\n      <div class=\"modal-dialog modal-bg\">\r\n        <div class=\"modal-content\">\r\n          <div class=\"modal-header\">\r\n            <h4 class=\"modal-title\">Delete File</h4>\r\n            <button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>\r\n          </div>\r\n          <div class=\"modal-body\">\r\n            <p>Are you sure you want to delete this file?</p>\r\n          </div>\r\n          <div class=\"modal-footer\">\r\n            <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">No</button>\r\n            <button type=\"button\" class=\"btn btn-success\" data-dismiss=\"modal\" ng-click=\"deleteFile()\">Yes</button>\r\n          </div>\r\n        </div>\r\n      </div>\r\n    </div>\r\n\r\n    <div *ngFor=\"let rule of rules\">\r\n    <ul id=\"rule.name\" class=\"connectedSortable collapse in\">\r\n      <!-- Генерировать data-target и соответственно id у collapse in правил, репитом правила. -->\r\n      <li class=\"ui-state-default btn btn-outline-secondary\" data-toggle=\"collapse\" data-target=\"#open-rule1\" (click)=\"changeChosenRule(rule)\">{{rule.name}}</li>\r\n    </ul>\r\n    </div>\r\n\r\n    <!-- !!!Демонстрация перетаскивания, удалить потом!!! -->\r\n    <ul id=\"sortable2\" class=\"connectedSortable\">\r\n      <li class=\"ui-state-highlight\">Item 1</li>\r\n      <li class=\"ui-state-highlight\">Item 2</li>\r\n    </ul>\r\n    <!-- Конец репита. -->\r\n\r\n\r\n    <div class=\"modal fade\" id=\"deleteRuleModal\" role=\"dialog\">\r\n      <div class=\"modal-dialog modal-bg\">\r\n        <div class=\"modal-content\">\r\n          <div class=\"modal-header\">\r\n            <h4 class=\"modal-title\">Delete Rule</h4>\r\n            <button type=\"button\" class=\"close\" data-dismiss=\"modal\">&times;</button>\r\n          </div>\r\n          <div class=\"modal-body\">\r\n            <p>Are you sure you want to delete this rule?</p>\r\n          </div>\r\n          <div class=\"modal-footer\">\r\n            <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">No</button>\r\n            <button type=\"button\" class=\"btn btn-success\" data-dismiss=\"modal\" ng-click=\"deleteRule()\">Yes</button>\r\n          </div>\r\n        </div>\r\n      </div>\r\n    </div>\r\n\r\n\r\n  </div>\r\n  <div id=\"open-rule1\" class=\"col-md-8 order-md-1 collapse in\">\r\n<rule-edit [ruleId] = chosenRule.id [ruleName] = chosenRule.name></rule-edit>\r\n  </div>\r\n\r\n\r\n</div>\r\n"

/***/ }),

/***/ "./src/frontend/app/rule-list/rule-list.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RuleListComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__rule_rule_service__ = __webpack_require__("./src/frontend/app/rule/rule.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var RuleListComponent = /** @class */ (function () {
    function RuleListComponent(ruleService) {
        this.ruleService = ruleService;
    }
    RuleListComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.ruleService.getAll().subscribe(function (data) {
            _this.rules = data;
        });
    };
    RuleListComponent.prototype.changeChosenRule = function (rule) {
        this.chosenRule = rule;
    };
    RuleListComponent.prototype.createRule = function () {
        var _this = this;
        this.rules.push(this.rules[0]);
        this.ruleService.saveAll(this.rules[this.rules.length - 1]).subscribe(function (result) {
            _this.rules = result;
        });
    };
    RuleListComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["m" /* Component */])({
            selector: 'rule-list',
            template: __webpack_require__("./src/frontend/app/rule-list/rule-list.component.html"),
            styles: [__webpack_require__("./src/frontend/app/rule-list/rule-list.component.css")]
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__rule_rule_service__["a" /* RuleService */]])
    ], RuleListComponent);
    return RuleListComponent;
}());



/***/ }),

/***/ "./src/frontend/app/rule/rule.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RuleService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_common_http__ = __webpack_require__("./node_modules/@angular/common/esm5/http.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var RuleService = /** @class */ (function () {
    function RuleService(http) {
        this.http = http;
    }
    RuleService.prototype.getAll = function () {
        return this.http.get('/rest/rules');
    };
    RuleService.prototype.saveAll = function (rule) {
        return this.http.post('/rest/rules', rule);
    };
    RuleService.prototype.get = function (id) {
        return this.http.get('/rest/rules/' + id);
    };
    RuleService.prototype.save = function (rule, ruleId) {
        return this.http.put('/rest/rules/' + ruleId, rule);
    };
    RuleService.prototype.remove = function (id) {
        return this.http.delete('/rest/rules/' + id);
    };
    RuleService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["w" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__angular_common_http__["a" /* HttpClient */]])
    ], RuleService);
    return RuleService;
}());



/***/ }),

/***/ "./src/frontend/environments/environment.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
var environment = {
    production: false
};


/***/ }),

/***/ "./src/frontend/main.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("./node_modules/@angular/core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__("./node_modules/@angular/platform-browser-dynamic/esm5/platform-browser-dynamic.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__("./src/frontend/app/app.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__("./src/frontend/environments/environment.ts");




if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_7" /* enableProdMode */])();
}
Object(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */])
    .catch(function (err) { return console.log(err); });


/***/ }),

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__("./src/frontend/main.ts");


/***/ })

},[0]);
//# sourceMappingURL=main.bundle.js.map