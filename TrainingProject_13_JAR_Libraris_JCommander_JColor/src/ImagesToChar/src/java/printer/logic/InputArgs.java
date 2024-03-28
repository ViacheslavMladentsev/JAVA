package printer.logic;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import printer.logic.ValidationProgramParametrs;

@Parameters(separators = "=")
public class InputArgs {

    @Parameter(names = {"--white"}, required = true, arity = 1, validateWith = ValidationProgramParametrs.class)
    private String white;

    @Parameter(names = {"--black"}, required = true, arity = 1, validateWith = ValidationProgramParametrs.class)
    private String black;


    public String getWhite() {
        return white;
    }

    public String getBlack() {
        return black;
    }

}
