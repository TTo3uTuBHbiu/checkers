
package g;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Cell {


    public int Color; //Цвет шашки, 1 — белая, 2 — черная, 0 — клетка пустая
    public int xCord;
    public int yCord;


    public Cell(int xCord, int yCord, int Color) {
        this.xCord = xCord;
        this.yCord = yCord;
        this.Color = Color;

    }

    public boolean Qeen = false;                   //Является ли шашка дамкой
    public boolean illumination = false;//подсвечено ли поле


    public static boolean cellIsFree(Cell cell) {
        if (cell.Color == 0) return true;
        else return false;
    }


    public static void clearCell(Cell cell) {
        cell.Color = 0;
        cell.Qeen = false;
    }

    int Number;

    public static boolean cellInDiag(Cell cell, int[] diag) {
        Numbering();
        for (int i : diag) {
            if (cell.Number == i) return true;
        }
        return false;
    }

    public static void Numbering() {
        for (int i = 0; i <= 31; i += 1) {
            Board.cells[i].Number = i;
        }
    }

    public void paintComponent(Graphics g) {
        if (illumination == true) g.setColor(new Color(0xFFFDFA11, true));
        if (illumination == false) g.setColor(new Color(0xA0621F10, true));
        if (Color == 0) g.fillRect(xCord, yCord, 50, 50);
        if (Color == 1) {
            g.fillRect(xCord, yCord, 50, 50);
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("C:\\Users\\z\\IdeaProjects\\checkers\\src\\g\\whiteChecker1.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            g.drawImage(image, xCord, yCord, null);

        }
        ;

        if (Color == 2) {
            g.fillRect(xCord, yCord, 50, 50);
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("C:\\Users\\z\\IdeaProjects\\checkers\\src\\g\\blackChecker.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            g.drawImage(image, xCord, yCord, null);
        }

        if ((Qeen == true)) {
            BufferedImage image = null;
            try {
                image = ImageIO.read(new File("C:\\Users\\z\\IdeaProjects\\checkers\\src\\g\\qeen.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            g.drawImage(image, xCord, yCord, null);
        }

    }


}




