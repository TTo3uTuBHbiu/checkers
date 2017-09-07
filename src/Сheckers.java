

import g.Cell;
import g.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class Сheckers extends JPanel implements Runnable, MouseListener, MouseMotionListener {

    //размеры окна
    final int width = 600;
    final int height = 600;

    //позиция мыши
    public int mouseX, mouseY;

    private Thread thread; //поток
    private int FPS = 30;
    private long targetTime = 1000 / FPS; //целевое время
    private boolean isRunnable;

    public boolean whoMove = false;  //true-белые,false-черные
    public int currentCell;
    public int atackSide;  //true - атакует направо, false - атакеут налево


    public Сheckers() {
        setPreferredSize(new Dimension(width, height)); //установка желаемого размера окна
        addMouseListener(this);
        addMouseMotionListener(this);
        start();

    }

    private void start() {
        isRunnable = true;
        thread = new Thread(this, "Game");
        thread.start();
    }

    private void stop() {
        isRunnable = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //Игровой цикл:
    public void run() {

        long start, elapsed, wait;
        while (isRunnable) {
            start = System.currentTimeMillis();
            elapsed = System.currentTimeMillis() - start;
            wait = targetTime - elapsed;
            if (wait < 5) {
                wait = 5;
            }
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
        stop();

    }

    public void update() {
    }

    ;


    //Отрисовка компонентов:
    public void paintComponent(Graphics g) {
        removeAll();
        g.clearRect(100, 100, 400, 400);
        Board.paint(g);

        if (whiteWin()) {
            g.setColor(Color.red);
            g.setFont(new Font("Times New Roman", Font.BOLD, 80));
            FontMetrics fm = g.getFontMetrics();
            g.drawString("White win", 100, (300));
        }


        if (blackWin()) {
            g.setColor(Color.red);
            g.setFont(new Font("Times New Roman", Font.BOLD, 80));
            FontMetrics fm = g.getFontMetrics();
            g.drawString("Black win", 100, (300));
        }

    }

    //Рабочее окно:
    public static void main(String[] args) {
        new Сheckers().go();
    }

    void go() {
        JFrame frame = new JFrame("Checkers");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //закрытие окна
        frame.setLayout(new BorderLayout());                    //менеджер компановки
        frame.add(new Сheckers(), BorderLayout.CENTER);
        frame.setVisible(true);//сделать видимым
        frame.pack();
        frame.setLocationRelativeTo(null);//центрирует окно
        frame.setBackground(Color.LIGHT_GRAY); //фон
        repaint();
    }


    public boolean whiteWin() {
        for (int i = 0; i <= 31; i += 1) {
            if (Board.cells[i].Color == 2) return false;
        }
        return true;
    }

    public boolean blackWin() {
        for (int i = 0; i <= 31; i += 1) {
            if (Board.cells[i].Color == 1) return false;
        }
        return true;
    }

    public void makeIlumination() {
        for (int i = 0; i <= 31; i += 1) {
            //условие совпадения координат мыши во время клика и клетки
            if ((mouseX <= 500) && (mouseY <= 500) && (mouseX >= 100) && (mouseY >= 100) && (mouseX <= Board.cells[i].xCord + 50) &&
                    (mouseY <= Board.cells[i].yCord + 50) && (mouseX >= Board.cells[i].xCord) && (mouseY >= Board.cells[i].yCord)) {

                if (Board.move == false) {

                    currentCell = i;
                    if (Board.cells[i].Qeen == false) {

                        if (Board.moveWhite) {

                            if (canFightWhite() == false) {   //если никто из белых не может атаковать

                                if (Board.cells[i].Color == 1) {    // подсветка возможных ходов для белых шашек

                                    if ((Board.cells[i].xCord == 100) || (Board.cells[i].xCord == 200) || (Board.cells[i].xCord == 300) || (Board.cells[i].xCord == 400)) {
                                        if ((Board.cells[i].xCord > 150) && (Cell.cellIsFree(Board.cells[i - 5]))) {
                                            Board.cells[i - 5].illumination = true;
                                            Board.move = true;
                                        }
                                        if ((Board.cells[i].xCord < 450) && (Cell.cellIsFree(Board.cells[i - 4]))) {
                                            Board.cells[i - 4].illumination = true;
                                            Board.move = true;
                                        }
                                    }

                                    if ((Board.cells[i].xCord == 150) || (Board.cells[i].xCord == 250) || (Board.cells[i].xCord == 350) || (Board.cells[i].xCord == 450)) {
                                        if ((Board.cells[i].xCord >= 150) && (Cell.cellIsFree(Board.cells[i - 4]))) {
                                            Board.cells[i - 4].illumination = true;
                                            Board.move = true;
                                        }
                                        if ((Board.cells[i].xCord < 450) && (Cell.cellIsFree(Board.cells[i - 3]))) {
                                            Board.cells[i - 3].illumination = true;
                                            Board.move = true;
                                        }

                                    }


                                }
                            }


                        }


                        if (Board.moveBlack == true) {
                            if (canFightBlack() == false) {   //если никто из черных не может атаковать

                                if (Board.cells[i].Color == 2) {    //подсветка возможных ходов для черных шашек
                                    if ((Board.cells[i].xCord == 100) || (Board.cells[i].xCord == 200) || (Board.cells[i].xCord == 300) || (Board.cells[i].xCord == 400)) {
                                        if ((Board.cells[i].xCord >= 150) && (Cell.cellIsFree(Board.cells[i + 3]))) {
                                            Board.cells[i + 3].illumination = true;
                                            Board.move = true;
                                        }
                                        if ((Board.cells[i].xCord < 450) && (Cell.cellIsFree(Board.cells[i + 4]))) {
                                            Board.cells[i + 4].illumination = true;
                                            Board.move = true;
                                        }
                                    }

                                    if ((Board.cells[i].xCord == 150) || (Board.cells[i].xCord == 250) || (Board.cells[i].xCord == 350) || (Board.cells[i].xCord == 450)) {
                                        if ((Board.cells[i].xCord >= 150) && (Cell.cellIsFree(Board.cells[i + 4]))) {
                                            Board.cells[i + 4].illumination = true;
                                            Board.move = true;
                                        }
                                        if ((Board.cells[i].xCord < 450) && (Cell.cellIsFree(Board.cells[i + 5]))) {
                                            Board.cells[i + 5].illumination = true;
                                            Board.move = true;
                                        }
                                    }

                                }

                            }


                        }

                    }

                    if (Board.cells[i].Qeen && Board.moveWhite == true && Board.cells[i].Color == 1 && !canFightWhite()) {
                        for (int[] j : Board.Diagonals) {
                            if (Cell.cellInDiag(Board.cells[i], j)) {
                                for (int k : j) {
                                    if (Board.cells[k].Color == 0) {
                                        Board.move = true;
                                        Board.cells[k].illumination = true;
                                    }

                                }
                            }
                        }


                    }

                    if (Board.cells[i].Qeen && Board.moveBlack == true && Board.cells[i].Color == 2 && !canFightBlack()) {
                        for (int[] j : Board.Diagonals) {
                            if (Cell.cellInDiag(Board.cells[i], j)) {
                                for (int k : j) {
                                    if (Board.cells[k].Color == 0) {
                                        Board.move = true;
                                        Board.cells[k].illumination = true;
                                    }

                                }
                            }
                        }
                    }

                }


            }
        }
    }


    public void iluminationFight() {
        try {
            for (int i = 0; i <= 31; i += 1) {
                if ((mouseX <= 500) && (mouseY <= 500) && (mouseX >= 100) && (mouseY >= 100) && (mouseX <= Board.cells[i].xCord + 50) &&
                        (mouseY <= Board.cells[i].yCord + 50) && (mouseX >= Board.cells[i].xCord) && (mouseY >= Board.cells[i].yCord)) {

                    if (Board.move == false) {
                        currentCell = i;
                        if (Board.cells[i].Qeen == false) {
                            if ((Board.moveWhite) && (canFightWhite()) && (Board.cells[i].Color == 1)) {
                                if ((Board.cells[i].xCord == 100) || (Board.cells[i].xCord == 200) || (Board.cells[i].xCord == 300) || (Board.cells[i].xCord == 400)) {
                                    if ((Board.cells[i - 4].Color == 2) && (Board.cells[i - 7].Color == 0) && (Board.cells[i].xCord != 400)) {
                                        Board.cells[i - 7].illumination = true;
                                        atackSide = 1;
                                        Board.move = true;
                                    }

                                    if ((Board.cells[i - 5].Color == 2) && (Board.cells[i - 9].Color == 0) && (Board.cells[i].xCord != 100)) {
                                        Board.cells[i - 9].illumination = true;
                                        atackSide = 2;
                                        Board.move = true;
                                    }
                                    if ((Board.cells[i + 4].Color == 2) && (Board.cells[i + 9].Color == 0) && (Board.cells[i].xCord != 400)) {
                                        Board.cells[i + 9].illumination = true;
                                        atackSide = 3;
                                        Board.move = true;
                                    }
                                    if ((Board.cells[i + 3].Color == 2) && (Board.cells[i + 7].Color == 0) && (Board.cells[i].xCord != 100)) {
                                        Board.cells[i + 7].illumination = true;
                                        atackSide = 4;
                                        Board.move = true;
                                    }
                                }

                                if ((Board.cells[i].xCord == 150) || (Board.cells[i].xCord == 250) || (Board.cells[i].xCord == 350) || (Board.cells[i].xCord == 450)) {
                                    if ((Board.cells[i - 3].Color == 2) && (Board.cells[i - 7].Color == 0) && (Board.cells[i].xCord != 450)) {
                                        Board.cells[i - 7].illumination = true;
                                        atackSide = 1;
                                        Board.move = true;
                                    }
                                    if ((Board.cells[i - 4].Color == 2) && (Board.cells[i - 9].Color == 0) && (Board.cells[i].xCord != 150)) {
                                        Board.cells[i - 9].illumination = true;
                                        atackSide = 2;
                                        Board.move = true;
                                    }
                                    if ((Board.cells[i + 4].Color == 2) && (Board.cells[i + 7].Color == 0) && (Board.cells[i].xCord != 150)) {
                                        Board.cells[i + 7].illumination = true;
                                        atackSide = 4;
                                        Board.move = true;
                                    }
                                    if ((Board.cells[i + 5].Color == 2) && (Board.cells[i + 9].Color == 0) && (Board.cells[i].xCord != 450)) {
                                        Board.cells[i + 9].illumination = true;
                                        atackSide = 3;
                                        Board.move = true;
                                    }
                                }

                            }

                            if ((Board.moveBlack) && (canFightBlack()) && (Board.cells[i].Color == 2)) {
                                if ((Board.cells[i].xCord == 100) || (Board.cells[i].xCord == 200) || (Board.cells[i].xCord == 300) || (Board.cells[i].xCord == 400)) {
                                    if ((Board.cells[i + 4].Color == 1) && (Board.cells[i + 9].Color == 0) && (Board.cells[i].xCord != 400)) {
                                        Board.cells[i + 9].illumination = true;
                                        atackSide = 1;
                                        Board.move = true;
                                    }
                                    if ((Board.cells[i + 3].Color == 1) && (Board.cells[i + 7].Color == 0) && (Board.cells[i].xCord != 100)) {
                                        Board.cells[i + 7].illumination = true;
                                        atackSide = 2;
                                        Board.move = true;
                                    }
                                    if ((Board.cells[i - 4].Color == 1) && (Board.cells[i - 7].Color == 0) && (Board.cells[i].xCord != 400)) {
                                        Board.cells[i - 7].illumination = true;
                                        atackSide = 3;
                                        Board.move = true;
                                    }

                                    if ((Board.cells[i - 5].Color == 1) && (Board.cells[i - 9].Color == 0) && (Board.cells[i].xCord != 100)) {
                                        Board.cells[i - 9].illumination = true;
                                        atackSide = 4;
                                        Board.move = true;
                                    }
                                }

                                if ((Board.cells[i].xCord == 150) || (Board.cells[i].xCord == 250) || (Board.cells[i].xCord == 350) || (Board.cells[i].xCord == 450)) {
                                    if ((Board.cells[i + 4].Color == 1) && (Board.cells[i + 7].Color == 0) && (Board.cells[i].xCord != 150)) {
                                        Board.cells[i + 7].illumination = true;
                                        atackSide = 2;
                                        Board.move = true;
                                    }
                                    if ((Board.cells[i + 5].Color == 1) && (Board.cells[i + 9].Color == 0) && (Board.cells[i].xCord != 450)) {
                                        Board.cells[i + 9].illumination = true;
                                        atackSide = 1;
                                        Board.move = true;
                                    }
                                    if ((Board.cells[i - 3].Color == 1) && (Board.cells[i - 7].Color == 0) && (Board.cells[i].xCord != 450)) {
                                        Board.cells[i - 7].illumination = true;
                                        atackSide = 3;
                                        Board.move = true;
                                    }
                                    if ((Board.cells[i - 4].Color == 1) && (Board.cells[i - 9].Color == 0) && (Board.cells[i].xCord != 150)) {
                                        Board.cells[i - 9].illumination = true;
                                        atackSide = 4;
                                        Board.move = true;
                                    }
                                }
                            }
                        }

                        if (Board.cells[i].Qeen) {
                            if ((Board.moveWhite) && (canFightWhite()) && (Board.cells[i].Color == 1)) {

                                if (Board.cells[i].Qeen && Board.moveWhite == true && Board.cells[i].Color == 1) {
                                    int count = 0;
                                    for (int[] j : Board.Diagonals) {
                                        if (Cell.cellInDiag(Board.cells[i], j)) {
                                            for (int k : j) {
                                                count++;
                                                if (Board.cells[k].Color == 2 && Board.cells[j[count]].Color == 0) {
                                                    Board.cells[j[count]].illumination = true;
                                                }


                                            }
                                        }
                                    }


                                }
                                if (Board.cells[i].Qeen && Board.moveWhite == true && Board.cells[i].Color == 1) {
                                    int count = 0;
                                    for (int[] j : Board.reverseDiagonals) {
                                        if (Cell.cellInDiag(Board.cells[i], j)) {
                                            for (int k : j) {
                                                count++;
                                                if (Board.cells[k].Color == 2 && Board.cells[j[count]].Color == 0) {
                                                    Board.cells[j[count]].illumination = true;
                                                }


                                            }
                                        }
                                    }


                                }

                            }


                        }

                    }


                }
            }
        } catch (Exception e) {
        }
    }


    public void makeMoveBlack() { //ход черных
        for (int j = 0; j <= 31; j += 1) {
            if ((mouseX <= 500) && (mouseY <= 500) && (mouseX >= 100) && (mouseY >= 100) && (mouseX <= Board.cells[j].xCord + 50) &&
                    (mouseY <= Board.cells[j].yCord + 50) && (mouseX >= Board.cells[j].xCord)
                    && (mouseY >= Board.cells[j].yCord) && (Board.cells[j].illumination == true)) {
                if (Board.cells[currentCell].Qeen == false) {
                    Board.cells[j].Color = 2;
                    Cell.clearCell(Board.cells[currentCell]);
                    Board.move = false;
                    Board.moveBlack = false;
                    Board.moveWhite = true;

                }
                if (Board.cells[currentCell].Qeen == true) {
                    Board.cells[currentCell].Qeen = false;
                    Board.cells[j].Qeen = true;
                    Board.cells[j].Color = 2;
                    Cell.clearCell(Board.cells[currentCell]);
                    Board.move = false;
                    Board.moveBlack = false;
                    Board.moveWhite = true;

                }


                for (int i = 0; i <= 31; i += 1) {
                    Board.cells[i].illumination = false;
                }
            }
        }

    }

    public void makeMoveWhite() {
        //ход белых
        for (int j = 0; j <= 31; j += 1) {
            if ((mouseX <= 500) && (mouseY <= 500) && (mouseX >= 100) && (mouseY >= 100) && (mouseX <= Board.cells[j].xCord + 50) &&
                    (mouseY <= Board.cells[j].yCord + 50) && (mouseX >= Board.cells[j].xCord)
                    && (mouseY >= Board.cells[j].yCord) && (Board.cells[j].illumination == true)) {
                if (Board.cells[currentCell].Qeen == false) {
                    Board.moveBlack = true;
                    Board.moveWhite = false;
                    Board.move = false;
                    Board.cells[j].Color = 1;
                    Cell.clearCell(Board.cells[currentCell]);
                }

                if ((Board.cells[currentCell].Qeen)) {
                    Board.moveBlack = true;
                    Board.moveWhite = false;
                    Board.move = false;
                    Board.cells[j].Color = 1;
                    Board.cells[j].Qeen = true;
                    Board.cells[currentCell].Qeen = false;
                    Cell.clearCell(Board.cells[currentCell]);


                }

                for (int i = 0; i <= 31; i += 1) {
                    Board.cells[i].illumination = false;
                }

            }

        }

    }


    public void fightWhite() {
        try {
            for (int i = 0; i <= 31; i += 1) {
                if ((mouseX <= 500) && (mouseY <= 500) && (mouseX >= 100) && (mouseY >= 100) && (mouseX <= Board.cells[i].xCord + 50) &&
                        (mouseY <= Board.cells[i].yCord + 50) && (mouseX >= Board.cells[i].xCord)
                        && (mouseY >= Board.cells[i].yCord) && (Board.cells[i].illumination == true)) {
                    if ((Board.cells[i].xCord == 100) || (Board.cells[i].xCord == 200) || (Board.cells[i].xCord == 300) || (Board.cells[i].xCord == 400)) {

                        if (Board.cells[i].illumination == true) {
                            Board.cells[i].Color = 1;
                            if (atackSide == 1) Cell.clearCell(Board.cells[i + 3]);
                            if (atackSide == 2) Cell.clearCell(Board.cells[i + 4]);
                            if (atackSide == 3) Cell.clearCell(Board.cells[i - 5]);
                            if (atackSide == 4) Cell.clearCell(Board.cells[i - 4]);
                            Cell.clearCell(Board.cells[currentCell]);
                            if (!canFightWhite()) {
                                Board.moveBlack = true;
                                Board.moveWhite = false;
                            }
                            Board.move = false;

                            for (i = 0; i <= 31; i += 1) {
                                Board.cells[i].illumination = false;
                            }

                        }

                    }


                    if ((Board.cells[i].xCord == 150) || (Board.cells[i].xCord == 250) || (Board.cells[i].xCord == 350) || (Board.cells[i].xCord == 450)) {
                        if (Board.cells[i].illumination == true) {
                            Board.cells[i].Color = 1;
                            System.out.print("2");
                            if (atackSide == 1) Cell.clearCell(Board.cells[i + 4]);
                            if (atackSide == 2) Cell.clearCell(Board.cells[i + 5]);
                            if (atackSide == 3) Cell.clearCell(Board.cells[i - 4]);
                            if (atackSide == 4) Cell.clearCell(Board.cells[i - 3]);

                            Cell.clearCell(Board.cells[currentCell]);
                            if (!canFightWhite()) {
                                Board.moveBlack = true;
                                Board.moveWhite = false;
                            }
                            Board.move = false;

                            for (i = 0; i <= 31; i += 1) {
                                Board.cells[i].illumination = false;
                            }
                        }
                    }

                }


            }
        } catch (Exception e) {
        }
    }


    public void fightBlack() {
        try {
            for (int i = 0; i <= 31; i += 1) {
                if ((mouseX <= 500) && (mouseY <= 500) && (mouseX >= 100) && (mouseY >= 100) && (mouseX <= Board.cells[i].xCord + 50) &&
                        (mouseY <= Board.cells[i].yCord + 50) && (mouseX >= Board.cells[i].xCord)
                        && (mouseY >= Board.cells[i].yCord) && (Board.cells[i].illumination == true)) {
                    if ((Board.cells[i].xCord == 100) || (Board.cells[i].xCord == 200) || (Board.cells[i].xCord == 300) || (Board.cells[i].xCord == 400)) {

                        if (Board.cells[i].illumination == true) {
                            Board.cells[i].Color = 2;
                            System.out.print("3");
                            if (atackSide == 1) Cell.clearCell(Board.cells[i - 5]);
                            if (atackSide == 2) Cell.clearCell(Board.cells[i - 4]);
                            if (atackSide == 3) Cell.clearCell(Board.cells[i + 3]);
                            if (atackSide == 4) Cell.clearCell(Board.cells[i + 4]);
                            Cell.clearCell(Board.cells[currentCell]);
                            Board.move = false;
                            if (!canFightBlack()) {
                                Board.moveBlack = false;
                                Board.moveWhite = true;
                            }
                            for (i = 0; i <= 31; i += 1) {
                                Board.cells[i].illumination = false;
                            }
                        }
                    }

                    if ((Board.cells[i].xCord == 150) || (Board.cells[i].xCord == 250) || (Board.cells[i].xCord == 350) || (Board.cells[i].xCord == 450)) {
                        if (Board.cells[i].illumination == true) {
                            Board.cells[i].Color = 2;
                            System.out.print("4");
                            if (atackSide == 1) Cell.clearCell(Board.cells[i - 4]);
                            if (atackSide == 2) Cell.clearCell(Board.cells[i - 3]);
                            if (atackSide == 3) Cell.clearCell(Board.cells[i + 4]);
                            if (atackSide == 4) Cell.clearCell(Board.cells[i + 5]);
                            Cell.clearCell(Board.cells[currentCell]);
                            Board.move = false;
                            if (!canFightBlack()) {
                                Board.moveBlack = false;
                                Board.moveWhite = true;
                            }
                            for (i = 0; i <= 31; i += 1) {
                                Board.cells[i].illumination = false;
                            }
                        }
                    }


                }


            }

        } catch (Exception e) {
        }
    }


    public boolean canFightWhite() {
        try {
            for (int i = 0; i <= 31; i += 1) {
                if (Board.cells[i].Color == 1) {
                    if (Board.cells[i].Qeen == false) {
                        if ((Board.cells[i].xCord == 100) || (Board.cells[i].xCord == 200) || (Board.cells[i].xCord == 300) || (Board.cells[i].xCord == 400)) {
                            if ((Board.cells[i - 4].Color == 2) && (Board.cells[i - 7].Color == 0) && (Board.cells[i].xCord != 400))
                                return true;
                            if ((Board.cells[i - 5].Color == 2) && (Board.cells[i - 9].Color == 0) && (Board.cells[i].xCord != 100))
                                return true;
                            if (i <= 23) {
                                if ((Board.cells[i + 3].Color == 2) && (Board.cells[i + 7].Color == 0) && (Board.cells[i].xCord != 100))
                                    return true;
                                if ((Board.cells[i + 4].Color == 2) && (Board.cells[i + 9].Color == 0) && (Board.cells[i].xCord != 400))
                                    return true;
                            }
                        }

                        if ((Board.cells[i].xCord == 150) || (Board.cells[i].xCord == 250) || (Board.cells[i].xCord == 350) || (Board.cells[i].xCord == 450)) {
                            if ((Board.cells[i - 3].Color == 2) && (Board.cells[i - 7].Color == 0) && (Board.cells[i].xCord != 450))
                                return true;
                            if ((Board.cells[i - 4].Color == 2) && (Board.cells[i - 9].Color == 0) && (Board.cells[i].xCord != 150))
                                return true;
                            if (i <= 23) {
                                if ((Board.cells[i + 4].Color == 2) && (Board.cells[i + 7].Color == 0) && (Board.cells[i].xCord != 150))
                                    return true;
                                if ((Board.cells[i + 5].Color == 2) && (Board.cells[i + 9].Color == 0) && (Board.cells[i].xCord != 450))
                                    return true;
                            }
                        }

                    }

                    if (Board.cells[i].Qeen && Board.moveWhite == true && Board.cells[i].Color == 1) {
                        int count = 0;
                        for (int[] j : Board.Diagonals) {
                            if (Cell.cellInDiag(Board.cells[i], j)) {
                                for (int k : j) {
                                    count++;
                                    if (Board.cells[k].Color == 2 && Board.cells[j[count]].Color == 0) {

                                        return true;

                                    }


                                }
                            }
                        }


                    }
                    if (Board.cells[i].Qeen && Board.moveWhite == true && Board.cells[i].Color == 1) {
                        int count = 0;
                        for (int[] j : Board.reverseDiagonals) {
                            if (Cell.cellInDiag(Board.cells[i], j)) {
                                for (int k : j) {
                                    count++;
                                    if (Board.cells[k].Color == 2 && Board.cells[j[count]].Color == 0) {
                                        return true;
                                    }


                                }
                            }
                        }


                    }

                }
            }

        } catch (Exception e) {
        }
        return false;
    }

    public boolean canFightBlack() {
        try {

            for (int i = 0; i < 31; i += 1) {
                if (Board.cells[i].Color == 2) {
                    if (Board.cells[i].Qeen == false) {
                        if ((Board.cells[i].xCord == 100) || (Board.cells[i].xCord == 200) || (Board.cells[i].xCord == 300) || (Board.cells[i].xCord == 400)) {
                            if ((Board.cells[i + 4].Color == 1) && (Board.cells[i + 9].Color == 0) && (Board.cells[i].xCord != 400))
                                return true;
                            if ((Board.cells[i + 3].Color == 1) && (Board.cells[i + 7].Color == 0) && (Board.cells[i].xCord != 100))
                                return true;
                            if (i >= 9) {
                                if ((Board.cells[i - 4].Color == 1) && (Board.cells[i - 7].Color == 0) && (Board.cells[i].xCord != 400))
                                    return true;
                                if ((Board.cells[i - 5].Color == 1) && (Board.cells[i - 9].Color == 0) && (Board.cells[i].xCord != 100))
                                    return true;
                            }
                        }

                        if ((Board.cells[i].xCord == 150) || (Board.cells[i].xCord == 250) || (Board.cells[i].xCord == 350) || (Board.cells[i].xCord == 450)) {
                            if ((Board.cells[i + 4].Color == 1) && (Board.cells[i + 7].Color == 0) && (Board.cells[i].xCord != 150))
                                return true;
                            if ((Board.cells[i + 5].Color == 1) && (Board.cells[i + 9].Color == 0) && (Board.cells[i].xCord != 450))
                                return true;
                            if (i >= 9) {
                                if ((Board.cells[i - 3].Color == 1) && (Board.cells[i - 7].Color == 0) && (Board.cells[i].xCord != 450))
                                    return true;
                                if ((Board.cells[i - 4].Color == 1) && (Board.cells[i - 9].Color == 0) && (Board.cells[i].xCord != 150))
                                    return true;
                            }
                        }

                    }
                }

                if (Board.cells[i].Qeen && Board.moveBlack == true && Board.cells[i].Color == 2) {
                    int count = 0;
                    for (int[] j : Board.Diagonals) {
                        if (Cell.cellInDiag(Board.cells[i], j)) {
                            for (int k : j) {
                                count++;
                                if (Board.cells[k].Color == 1 && Board.cells[j[count]].Color == 0) {
                                    return true;
                                }


                            }
                        }
                    }

                }
                if (Board.cells[i].Qeen && Board.moveBlack == true && Board.cells[i].Color == 2) {
                    int count = 0;
                    for (int[] j : Board.reverseDiagonals) {
                        if (Cell.cellInDiag(Board.cells[i], j)) {
                            for (int k : j) {
                                count++;
                                if (Board.cells[k].Color == 1 && Board.cells[j[count]].Color == 0) {
                                    return true;
                                }


                            }
                        }
                    }

                }
            }
            return false;
        } catch (Exception e) {
        }
        return false;
    }

    public void cellBecomeQeen() {
        for (int i = 0; i <= 31; i += 1) {
            if (Board.cells[i].Color == 1 && (i == 0 || i == 1 || i == 2 || i == 3)) Board.cells[i].Qeen = true;
            if (Board.cells[i].Color == 2 && (i == 31 || i == 30 || i == 29 || i == 28)) Board.cells[i].Qeen = true;
        }

    }


    public void mouseClicked(MouseEvent e) {
        makeIlumination();
        if (Board.moveBlack && !canFightBlack()) makeMoveBlack();
        if (Board.moveWhite && !canFightWhite()) makeMoveWhite();


        iluminationFight();
        if (Board.moveBlack && canFightBlack()) fightBlack();
        if (Board.moveWhite && canFightWhite()) fightWhite();

        cellBecomeQeen();


        System.out.println(Board.moveBlack);
        System.out.println(Board.moveWhite);
        System.out.println(Board.move);
        System.out.println(canFightWhite());
        System.out.println(canFightBlack());
        System.out.println(currentCell);


    }


    public void mouseDragged(MouseEvent e) {
        setMousePosition(e);
    }

    public void mouseMoved(MouseEvent e) {
        setMousePosition(e);


    }

    //Отслеживание положения мыши
    private void setMousePosition(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }


}
