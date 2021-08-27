## How to run 
``java --enable-preview -jar CodeFormatter-1.0-SNAPSHOT-jar-with-dependencies.jar testSourceCode.java output.java``

### Example
###### Input:
```java
public UserLoginResponse loginUser(UserLoginRequest request) throws BadRequestException { /* COMMENT HERE */
        User user = userService.getUserByEmail(request.getEmail());
        String str = "string s { somethign; }";
    /*ome more
    multiline comment*/
        if (user == null) { throw new BadRequestException(ErrorCode.INCORRECT_LOGIN, "login"); }
        String codeBlockThatShouldNotBeFormatted = """a (b) { c; d; e; }""";
        //My single line comment right here
        String token = tokenService.getToken(user); for (int i = 0; i < count; i++) { counter++; } response.addHeader(SET_AUTH_HEADER_STRING, token); UserLoginResponse loginDtoResponse = new UserLoginResponse(token); return forz; return loginDtoResponse; }
```
###### Output formatted code:
```java
public UserLoginResponse loginUser(UserLoginRequest request) throws BadRequestException {
    /* COMMENT HERE */
    User user = userService.getUserByEmail(request.getEmail());
    String str = "string s { somethign; }";
    /*ome more
    multiline comment*/
    if (user == null) {
        throw new BadRequestException(ErrorCode.INCORRECT_LOGIN, "login");
    }
    String codeBlockThatShouldNotBeFormatted = """a (b) { c; d; e; }""";
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