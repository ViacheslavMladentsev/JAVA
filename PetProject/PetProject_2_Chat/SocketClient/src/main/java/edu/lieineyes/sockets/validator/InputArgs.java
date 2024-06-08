package edu.school21.sockets.validator;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import lombok.Getter;

@Getter
@Parameters(separators = "=")
public class InputArgs {

    private static final String PARAMETER_SERVER_PORT = "--server-port";

    @Parameter(names = {PARAMETER_SERVER_PORT}, required = true, arity = 1, validateWith = ValidationProgramStartupArguments.class)
    private int port;

}

