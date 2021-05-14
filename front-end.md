# Front-End Step by Step

## (1) 安装NPM

```bash
$ npm install -g npm@6.14.12
```

## (2) ANGULAR-CLI创建项目

``` bash
$ ng new reddit-clone-frontend
$ cd reddit-clone-frontend
$ ng serve
```

## (3) 安装BOOTSTRAP

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

## (4) 创建Header组件

```bash
$ ng generate component header
```

在app.component.html中导入

```html
<app-header></app-header>
<router-outlet></router-outlet>
```



## (5) 创建auth/signup组件

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

  

## (6)创建auth/shared/auth服务

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

  

## (7)创建auth/login组件

- 创建

  ```bash
  $ ng g c auth/login
  ```

- 导入HTML、CSS： https://programmingtechie.com/2020/04/03/build-a-full-stack-reddit-clone-with-spring-boot-and-angular-part-12/

- 绑定FormGroup和FormControl从TS到HTML

- 把组件添加到app-routing.module.ts

  ```typescript
  const routes: Routes = [
    {path: 'sign-up', component: SignupComponent},
    {path: 'login', component: LoginComponent}
  ];
  ```

- 创建DTO类login-request.payload.ts和login-response.payload.ts

- 在package.json中添加依赖,并在app.module.ts中导入

  ```bash
  $ npm install ngx-webstorage --save
  ```

  ```json
  {
    "devDependencies":{
      "ngx-webstorage": "5.0.0"
    }
  }
  ```

  ``` typescript
  imports:[
    NgxWebstorageModule.forRoot()
  ]
  ```

- 在auth服务中实现login( )方法

  ```typescript
  //Step1
  import { LocalStorageService } from 'ngx-webstorage';
  import { map, tap } from 'rxjs/operators';
  //Step2
  constructor(private httpClient: HttpClient, private localStorage: LocalStorageService) {
    }
  //Step3
  login(loginRequestPayload: LoginRequestPayload): Observable<boolean>{
      return this.httpClient.post<LoginResponse>('http://localhost:8080/api/auth/login',
        loginRequestPayload).pipe(map(data => {
          this.localStorage.store('authenticationToken', data.authenticationToken);
          this.localStorage.store('username', data.username);
          this.localStorage.store('refreshToken', data.refreshToken);
          this.localStorage.store('expiresAt', data.expiresAt);
  
          return true;
        })); 
    }
  ```

- 在login组件中引用auth服务的login()方法

  ```typescript
  //Step1
  loginForm:FormGroup;
    loginRequestPayload: LoginRequestPayload;
    isError:boolean;
  
    constructor(private authService: AuthService) { 
      this.loginRequestPayload = {
        username: '',
        password: ''
      };
      this.isError = false;
  
    }
  //Step2
  login(){
      this.loginRequestPayload.username = this.loginForm.get('username').value;
      this.loginRequestPayload.password = this.loginForm.get('password').value;
  
      this.authService.login(this.loginRequestPayload).subscribe(data =>{
        console.log('Login Successful');  
      });
    }
  ```

- 安装ngx-toastr并导入到app.module.ts

  ```typescript
  import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
  import { ToastrModule } from 'ngx-toastr';
  
  imports: [
      //.....,
      BrowserAnimationsModule,
      ToastrModule.forRoot()
    ]
  ```

- 将Toastr注入到signup、login组件

（8）创建token-interceptor.ts

```typescript
export class TokenInterceptor implements HttpInterceptor {
    isTokenRefreshing = false;
    refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject(null);

    constructor(public authService: AuthService) { }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<any>{
        const jwtToken = this.authService.getJwtToken();
        if(jwtToken) {
            this.addToken(req, jwtToken);
        }
        return next.handle(req).pipe(catchError(error => {
            if (error instanceof HttpErrorResponse
                && error.status === 403) {
                return this.handleAuthErrors(req, next);
            } else {
                return throwError(error);
            }
        }));

    }

    private handleAuthErrors(req: HttpRequest<any>, next: HttpHandler)
        : Observable<HttpEvent<any>> {
        if (!this.isTokenRefreshing) {
            this.isTokenRefreshing = true;
            this.refreshTokenSubject.next(null);

            return this.authService.refreshToken().pipe(
                switchMap((refreshTokenResponse: LoginResponse) => {
                    this.isTokenRefreshing = false;
                    this.refreshTokenSubject
                        .next(refreshTokenResponse.authenticationToken);
                    return next.handle(this.addToken(req,
                        refreshTokenResponse.authenticationToken));
                })
            )
        } else {
            return this.refreshTokenSubject.pipe(
                filter(result => result !== null),
                take(1),
                switchMap((res) => {
                    return next.handle(this.addToken(req,
                        this.authService.getJwtToken()))
                })
            );
        }
    }

    addToken(req: HttpRequest<any>, jwtToken: any) {
        return req.clone({
            headers: req.headers.set('Authorization',
                'Bearer ' + jwtToken)
        });
    }
}
```

