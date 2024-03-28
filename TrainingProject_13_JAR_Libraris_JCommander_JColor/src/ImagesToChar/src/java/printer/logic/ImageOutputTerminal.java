package printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;

import com.diogonunes.jcolor.Attribute;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class ImageOutputTerminal {

    private final String pixelWhite;

    private final String pixelBlack;

    private BufferedImage bufferImage = null;


    public ImageOutputTerminal(final String pixelWhite, final String pixelBlack, final Path pathFile) throws IOException {
        this.bufferImage = ImageIO.read(new File(pathFile.toString()));
        this.pixelWhite = pixelWhite;
        this.pixelBlack = pixelBlack;
    }


    private void printColorForPixelByName(final String nameColor) {
        Color color;
        try {
            Field field = Class.forName("java.awt.Color").getField(nameColor);
            color = (Color) field.get(null);
        } catch (Exception e) {
            color = null;
        }

        Attribute bkgColor = BACK_COLOR(color.getRed(), color.getGreen(), color.getBlue());
        System.out.print(colorize("   ", bkgColor));
    }

    public void printImage() {
        for (int i = 0; i < this.bufferImage.getHeight(); ++i) {
            for (int j = 0; j < this.bufferImage.getWidth(); ++j) {
                if (this.bufferImage.getRGB(j, i) == Color.WHITE.getRGB()) {
                    printColorForPixelByName(pixelWhite);
                } else if (this.bufferImage.getRGB(j, i) == Color.BLACK.getRGB()) {
                    printColorForPixelByName(pixelBlack);
                }
            }
            System.out.println();
        }
    }

}
