package g;

import java.awt.*;


public class Board {
    public static boolean move = false;    //состояние хода
    public static boolean moveBlack = false;
    public static boolean moveWhite = true;
    public static boolean fightWight = false;
    public static boolean fightBlack = false;


    public static Cell[] cells = {
            new Cell(150, 100, 2),
            new Cell(250, 100, 2),
            new Cell(350, 100, 2),
            new Cell(450, 100, 2),
            new Cell(100, 150, 2),
            new Cell(200, 150, 2),
            new Cell(300, 150, 2),
            new Cell(400, 150, 2),
            new Cell(150, 200, 2),
            new Cell(250, 200, 2),
            new Cell(350, 200, 2),
            new Cell(450, 200, 2),
            new Cell(100, 250, 0),
            new Cell(200, 250, 0),
            new Cell(300, 250, 0),
            new Cell(400, 250, 0),
            new Cell(150, 300, 0),
            new Cell(250, 300, 0),
            new Cell(350, 300, 0),
            new Cell(450, 300, 0),
            new Cell(100, 350, 1),
            new Cell(200, 350, 1),
            new Cell(300, 350, 1),
            new Cell(400, 350, 1),
            new Cell(150, 400, 1),
            new Cell(250, 400, 1),
            new Cell(350, 400, 1),
            new Cell(450, 400, 1),
            new Cell(100, 450, 1),
            new Cell(200, 450, 1),
            new Cell(300, 450, 1),
            new Cell(400, 450, 1)

    };

    public static int Diagonals[][] = {
            {29, 24, 20},
            {30, 25, 21, 16},
            {31, 26, 22, 17, 13, 8, 4},
            {27, 23, 18, 14, 9, 5, 0},
            {19, 15, 10, 6, 1},
            {11, 7, 2},


            {12, 8, 5, 1},
            {20, 16, 13, 9, 6, 2},
            {28, 24, 21, 17, 14, 10, 7, 3},
            {29, 25, 22, 18, 15, 11},
            {30, 26, 23, 19},
    };

    public static int reverseDiagonals[][] = {
            {20, 24, 29},
            {16, 21, 25, 30},
            {4, 8, 13, 17, 22, 26, 31},
            {0, 5, 9, 14, 18, 23, 27},
            {1, 6, 10, 15, 19},
            {2, 7, 11},


            {1, 5, 8, 12},
            {2, 6, 9, 13, 16, 20},
            {3, 7, 10, 14, 17, 21, 24, 28},
            {11, 15, 18, 22, 25, 29},
            {19, 23, 26, 30},
    };


    public static void paint(Graphics g) {

        for (int i = 0; i < 32; i += 1) {
            cells[i].paintComponent(g);
        }


    }



}
