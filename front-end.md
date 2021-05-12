# Front-End Step by Step

(1) 安装NPM

```bash
$ npm install -g npm@6.14.12
```

(2) ANGULAR-CLI创建项目

``` bash
$ ng new reddit-clone-frontend
$ cd reddit-clone-frontend
$ ng serve
```

(3) 安装BOOTSTRAP

```bash
$ npm install --save bootstrap@4.5.2
```

然后,修改angular.json文件中的

```json
{
  "styles": [
              "node_modules/bootstrap/dist/css/bootstrap.min.css",
              "src/styles.css"
            ]
}
```

(4) 创建Header组件

```bash
$ ng generate component header
```

在app.component.html中导入

```html
<app-header></app-header>
<router-outlet></router-outlet>
```



(5) 创建AUTH/SIGNUP组件

- 创建

  ```bash
  $ ng g c auth/signup
  ```

- 添加html和css

- 打开app-routing.module.ts并加入导航

  ```typescript
  import { SignupComponent } from './auth/signup/signup.component';
  
  const routes: Routes = [
    {path:'sign-up', component: SignupComponent}
  ];
  ```

- 在app.module.ts中引入ReactiveFormsModule

  ```typescript
  import { ReactiveFormsModule } from '@angular/forms';
  
  @NgModule({
    declarations: [
      AppComponent,
      HeaderComponent,
      SignupComponent
    ],
    imports: [
      BrowserModule,
      AppRoutingModule,
      ReactiveFormsModule
    ],
    providers: [],
    bootstrap: [AppComponent]
  })
  export class AppModule { }

- 在signup.component.ts中引入FormGroup

  ```typescript
  import { Component, OnInit } from '@angular/core';
  import { FormControl, FormGroup, Validators } from '@angular/forms';
  
  @Component({
    selector: 'app-signup',
    templateUrl: './signup.component.html',
    styleUrls: ['./signup.component.css']
  })
  export class SignupComponent implements OnInit {
    signupForm: FormGroup;
  
    constructor() { }
  
    ngOnInit(): void {
      this.signupForm = new FormGroup({
        username: new FormControl('', Validators.required),
        email: new FormControl('', [Validators.required, Validators.email]),
        password: new FormControl('', Validators.required)
      });
    }
  
  }
  ```

- 在signup.component.html中绑定signupForm

  ```html
  <form [formGroup]="signupForm">
          <div class="form-group row">
                <label class="col-md-4 col-form-label text-md-right">User Name</label>
                <div class="col-md-6">
                <input type="text" [formControlName]="'username'" class="form-control" required autofocus>
                <span *ngIf="!signupForm.get('username').valid && signupForm.get('username').touched">
                                      Please provide a valid username.
                </span>
                </div>            
         </div>
         <!-- email and password are same with username-->
  
                <span class="col-md-6 offset-md-4">
                <button type="submit" class="sign-up">
                     Sign Up
                </button>
               <span style="padding-left: 15px">Existing user? <a routerLink="/login">Log In</a></span>
               </span>
  </form>
  ```

- 打开style.css修改错误提示框

  ```css
  input.ng-invalid.ng-touched {
    border: 1px solid red;
  }
  ```

- 创建signup-request.payload.ts实体类

  ```typescript
  export interface SignupRequestPayload {
      username: string;
      password: string;
      email: string;
  }
  ```

- 在signup.component.ts中实现signup()方法

  ```typescript
  export class SignupComponent implements OnInit {
    signupForm: FormGroup;
    signupRequestPayload: SignupRequestPayload;
  
    constructor() { 
      this.signupRequestPayload = {
        username: '',
        email: '',
        password: ''
      }
    }
  
    ngOnInit(): void {
      this.signupForm = new FormGroup({
        username: new FormControl('', Validators.required),
        email: new FormControl('', [Validators.required, Validators.email]),
        password: new FormControl('', Validators.required)
      });
    }
  
    signup() {
      this.signupRequestPayload.email = this.signupForm.get('email').value;
      this.signupRequestPayload.username = this.signupForm.get('username').value;
      this.signupRequestPayload.password = this.signupForm.get('password').value;
    }
  
  }
  ```

  

(6)创建Auth/shared/auth服务

- 创建

  ```bash
  $ ng g s auth/shared/auth
  ```

- 在app.module.ts中引入HttpClientModule

  ```typescript
  import { HttpClientModule } from '@angular/common/http';
  
  @NgModule({
    declarations: [],
    imports: [
      xxxModule,
      HttpClientModule
    ],
    providers: [],
    bootstrap: [AppComponent]
  });
  ```

  

- 在auth.service.ts中引入http客户端并创建signup()方法：

  ``` typescript
  import { HttpClient } from '@angular/common/http'
  
  export class AuthService {
  
    constructor(private httpClient: HttpClient) {
    }
  
    signup(signupRequestPayload: SignupRequestPayload): Observable<any> {
      return this.httpClient.post('http://localhost:8080/api/auth/signup', signupRequestPayload, { responseType: 'text' });
    }
  
  }
  ```

  