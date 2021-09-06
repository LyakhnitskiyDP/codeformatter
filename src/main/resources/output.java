public UserLoginResponse loginUser(UserLoginRequest request) throws BadRequestException {
    User user = userService.getUserByEmail(request.getEmail());
    String str = "string s { somethign; }";
    if (user == null) {
        throw new BadRequestException(ErrorCode.INCORRECT_LOGIN, "login");
    }
    String codeBlockThatShouldNotBeFormatted = "a (b) { c; d; e; }";
    String token = tokenService.getToken(user);
    for (int i = 0; i < count; i++) {
        counter++;
    }
    response.addHeader(SET_AUTH_HEADER_STRING, token);
    UserLoginResponse loginDtoResponse = new UserLoginResponse(token);
    return forks;
    return loginDtoResponse;
}
