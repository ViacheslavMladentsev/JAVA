package printer.app;

import printer.logic.*;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    private static final int CODE_FOR_FORCED_TERMINATION_PROGRAM = -1;

    private static final Path PATH_IMAGE = Paths.get("src/resources/image.bmp");  // todo изменить на путь относительно архива


    public static void main(String[] args) {
        InputArgs arguments = new InputArgs();
        try {
            JCommander.newBuilder().addObject(arguments).build().parse(args);
            ValidationProgramParametrs.checkedPathImage(PATH_IMAGE);
            ImageOutputTerminal temp = new ImageOutputTerminal(arguments.getWhite(), arguments.getBlack(), PATH_IMAGE);
            temp.printImage();
        } catch (ParameterException e) {
            System.out.println(e);
            System.exit(CODE_FOR_FORCED_TERMINATION_PROGRAM);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(CODE_FOR_FORCED_TERMINATION_PROGRAM);
        }

    }

}
