package edu.lieineyes.sockets.validator;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import lombok.Getter;

@Getter
@Parameters(separators = "=")
public class InputArgs {

    private static final String PARAMETER_CLIENT_PORT = "--port";

    @Parameter(names = {PARAMETER_CLIENT_PORT}, required = true, arity = 1, validateWith = edu.lieineyes.sockets.validator.ValidationProgramStartupArguments.class)
    private int port;

}

