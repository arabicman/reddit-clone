# reddit-clone

## 1.Git:

### **Quick setup** — if you’ve done this kind of thing before

[ Set up in Desktop](https://desktop.github.com/)

**or**

HTTPSSSH



Get started by [creating a new file](https://github.com/arabicman/reddit-clone/new/main) or [uploading an existing file](https://github.com/arabicman/reddit-clone/upload). We recommend every repository include a [README](https://github.com/arabicman/reddit-clone/new/main?readme=1), [LICENSE](https://github.com/arabicman/reddit-clone/new/main?filename=LICENSE.md), and [.gitignore](https://github.com/arabicman/reddit-clone/new/main?filename=.gitignore).

### …or create a new repository on the command line



```
echo "# reddit-clone" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/arabicman/reddit-clone.git
git push -u origin main
```

### …or push an existing repository from the command line



```
git remote add origin https://github.com/arabicman/reddit-clone.git
git branch -M main
git push -u origin main
```



## **2.APIs:**

(1)注册账号：   POST       http://localhost:8080/api/auth/signup    

```json
{
    "username": "test2",
    "email": "test2@mymail.com",
    "password": "password2"
}
```

(2)账号登陆:  POST  http://localhost:8080/api/auth/login (获得JWT)

```json
{
    "username": "test2",
    "password": "password2"
}
```

(3)查看、发布Subreddit： (需要JWT)

- GET http://localhost:8080/api/subreddit

- GET  http://localhost:8080/api/subreddit/1

- POST http://localhost:8080/api/subreddit

  ```json
  {
      "name":"Third Subreddit",
      "description": "This is 3rd Subreddit"
  }
  ```

(4)查看、发布post : (需要JWT)

- GET      http://localhost:8080/api/posts/

- GET      http://localhost:8080/api/posts/1

- GET      http://localhost:8080/api/posts/by-subreddit/1

- GET      http://localhost:8080/api/posts/by-user/test2

- POST    http://localhost:8080/api/posts/

  ```json
  {
      "subredditName": "First Subreddit",
      "postName": "2nd Post in 1st Sub",
      "url": "https://googlea.com",
      "description": "This is 2nd Post in 1st Sub"
  }
  ```

(5)查看、发布comment : (需要JWT)

-  GET       http://localhost:8080/api/comments/by-post/2

-  GET       http://localhost:8080/api/comments/by-user/test2

-  POST    http://localhost:8080/api/comments/

  ```json
  {
      "postId": 2,
      "text": "First",
      "userName": "test3"
  }
  ```

(6)点👍点👎: (需要JWT)

- POST   http://localhost:8080/api/votes/

  ```json
  {
      "voteType": "UPVOTE",
      "postId": 2
  }
  ```

(7)Refresh Token

- POST       http://localhost:8080/api/auth/refresh/token

  ```json
  {
      "refreshToken": "d024b0a1-1afe-4480-a127-8331ac051980",
      "username": "test2"
  }
  ```

(8)退出登陆

- POST  http://localhost:8080/api/auth/logout/

  ```json
  {
      "refreshToken": "d024b0a1-1afe-4480-a127-8331ac051980",
      "username": "test2"
  }
  ```

  

