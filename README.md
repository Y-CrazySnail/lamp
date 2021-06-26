# spring-security--oauth2-demo

## 目录结构

```
├── README.md
├── auth-server
│   ├── auth-server.iml
│   ├── pom.xml
│   └── src
│       └── main
│           ├── java
│           │   └── com.snail
│           │       ├── AuthServerApplication.java
│           │       ├── controller
│           │       │   └── UsersController.java
│           │       ├── entity
│           │       │   ├── Permission.java
│           │       │   ├── Role.java
│           │       │   └── User.java
│           │       ├── mapper
│           │       │   ├── PermissionMapper.java
│           │       │   ├── RoleMapper.java
│           │       │   └── UserMapper.java
│           │       ├── security
│           │       │   ├── AuthorizationServerConfig.java
│           │       │   ├── CORSFilter.java
│           │       │   ├── UserDetailService.java
│           │       │   └── WebSecurityConfig.java
│           │       └── service
│           │           ├── IPermissionService.java
│           │           ├── IRoleService.java
│           │           ├── IUserService.java
│           │           └── impl
│           │               ├── PermissionServiceImpl.java
│           │               ├── RoleServiceImpl.java
│           │               └── UserServiceImpl.java
│           └── resources
│               ├── application.yml
│               └── mappers
│                   ├── PermissionMapper.xml
│                   ├── RoleMapper.xml
│                   └── UserMapper.xml
├── client-password
│   ├── client-password.iml
│   ├── pom.xml
│   └── src
│       └── main
│           ├── java
│           │   └── com.snail
│           │       ├── ClientPassword.java
│           │       ├── config
│           │       │   ├── CORSFilter.java
│           │       │   └── ResourceServerConfig.java
│           │       ├── controller
│           │       │   ├── BaseController.java
│           │       │   ├── BlogsController.java
│           │       │   └── UserController.java
│           │       ├── entity
│           │       │   ├── BaseModel.java
│           │       │   └── Blogs.java
│           │       ├── mapper
│           │       │   └── BlogsMapper.java
│           │       └── service
│           │           ├── IBlogsService.java
│           │           └── impl
│           │               └── BlogsServiceImpl.java
│           └── resources
│               ├── application.yml
│               └── mappers
│                   └── BlogsMapper.xml
├── spring-security-oauth2.sql
└── pom.xml
```