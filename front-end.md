# Front-End Step by Step

(1)安装NPM

```bash
$ npm install -g npm@6.14.12
```

(2)ANGULAR-CLI创建项目

``` bash
$ ng new reddit-clone-frontend
$ cd reddit-clone-frontend
```

(3)安装BOOTSTRAP

```bash
$ npm install --save bootstrap
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

(4)创建一个新的Component

```bash
$ ng generate component header
```

