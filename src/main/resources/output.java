public UserLoginResponse loginUser(UserLoginRequest request) throws BadRequestException {
    /* COMMENT HERE */
    User user = userService.getUserByEmail(request.getEmail());
    if (user == null) {
        throw new BadRequestException(ErrorCode.INCORRECT_LOGIN, "login");
    }
    String token = tokenService.getToken(user);
    for (int i = 0; i < count; i++) {
        counter++;
    }
    response.addHeader(SET_AUTH_HEADER_STRING, token);
    UserLoginResponse loginDtoResponse = new UserLoginResponse(token);
    return loginDtoResponse;
}
