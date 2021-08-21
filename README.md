## How to run 
``java --enable-preview -jar CodeFormatter-1.0-SNAPSHOT-jar-with-dependencies.jar testSourceCode.java output.java``

### Example
###### Input:
```java
public UserLoginResponse loginUser(UserLoginRequest request) throws BadRequestException { /* COMMENT HERE */
    User user = userService.getUserByEmail(request.getEmail());
    /*ome more
    multiline comment*/
    if (user == null) { throw new BadRequestException(ErrorCode.INCORRECT_LOGIN, "login"); }
    //My single line comment right here
    String token = tokenService.getToken(user); for (int i = 0; i < count; i++) { counter++; } response.addHeader(SET_AUTH_HEADER_STRING, token); UserLoginResponse loginDtoResponse = new UserLoginResponse(token); return forz; return loginDtoResponse; }
```

###### Output tokenization:
```text
Simple statement      -      public UserLoginResponse loginUser(UserLoginRequest request) throws BadRequestException {
Multiline comment     -      /* COMMENT HERE */
Simple statement      -      User user = userService.getUserByEmail(request.getEmail());
Multiline comment     -      /*ome more     multiline comment*/
Simple statement      -      if (user == null) {
Simple statement      -      throw new BadRequestException(ErrorCode.INCORRECT_LOGIN, "login");
Simple statement      -      }
Single line comment   -      //My single line comment right here
Simple statement      -      String token = tokenService.getToken(user);
For loop              -      for (int i = 0; i < count; i++) {
Simple statement      -      counter++;
Simple statement      -      }
Simple statement      -      response.addHeader(SET_AUTH_HEADER_STRING, token);
Simple statement      -      UserLoginResponse loginDtoResponse = new UserLoginResponse(token);
Simple statement      -      return forz;
Simple statement      -      return loginDtoResponse;
Simple statement      -      }
```
###### Output formatted code:
```java
public UserLoginResponse loginUser(UserLoginRequest request) throws BadRequestException {
    /* COMMENT HERE */
    User user = userService.getUserByEmail(request.getEmail());
    /* ome more
    multiline comment */
    if (user == null) {
        throw new BadRequestException(ErrorCode.INCORRECT_LOGIN, "login");
    }
    //My single line comment right here
    String token = tokenService.getToken(user);
    for (int i = 0; i < count; i++) {
        counter++;
    }
    response.addHeader(SET_AUTH_HEADER_STRING, token);
    UserLoginResponse loginDtoResponse = new UserLoginResponse(token);
    return forz;
    return loginDtoResponse;
}

```