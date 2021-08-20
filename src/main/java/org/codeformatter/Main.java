package org.codeformatter;

import org.codeformatter.formatters.ChainedFormatter;
import org.codeformatter.io.string.StringReader;
import org.codeformatter.io.string.StringWriter;


public class Main {

    public static void main(String[] args) {

        ChainedFormatter chainedFormatter = new ChainedFormatter();

        StringBuilder formattedCode = new StringBuilder();
        StringWriter stringWriter = new StringWriter(formattedCode);

        StringReader stringReader = new StringReader(
                "public UserLoginResponse loginUser(UserLoginRequest request) throws BadRequestException { /* COMMENT HERE */User user = userService.getUserByEmail(request.getEmail()); if (user == null) { throw new BadRequestException(ErrorCode.INCORRECT_LOGIN, \"login\"); } String token = tokenService.getToken(user); for (int i = 0; i < count; i++) { counter++; } response.addHeader(SET_AUTH_HEADER_STRING, token); UserLoginResponse loginDtoResponse = new UserLoginResponse(token); return loginDtoResponse; }"
        );

        chainedFormatter.format(stringReader, stringWriter);

        System.out.println(formattedCode.toString());
    }


}
