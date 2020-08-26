package brickBreaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballposX = 120;
    private int ballposy = 350;
    private int ballxdir = -1;
    private int ballydir = -2;

    private MapGenerator map;

    public Gameplay() {
        map = new MapGenerator(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        //Background
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        //Drawing Map
        map.draw((Graphics2D)g);

        //Borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        //The Scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString(""+score, 590, 30);

        //The Paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        //The Ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposy, 20, 20);

        if (totalBricks <= 0) {
            play = false;
            ballxdir = 0;
            ballydir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Win! ", 260, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);
        }

        if (ballposy > 570) {
            play = false;
            ballxdir = 0;
            ballydir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", 230, 350);

        }

        g.dispose();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //Recall paint method and draws everything again
        //Calling playerx only calls once, and doesn't pop up again
        timer.start();
        if (play) {
            //Intersection between paddle and ball so that it hits it
                //Need a rectangle around ball. Can intersect two diff objects with rectangle
            if (new Rectangle(ballposX, ballposy, 20, 20).intersects(new Rectangle(playerX, 550,100,8))) {
                ballydir = -ballydir;
            }
            //MapGenerator map.public int map[][]
            //Access 2D Array with object we've made in this class
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i* map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposy, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects(brickRect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            //Ball intersecting with brick eliminates it
                            if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                                ballxdir = -ballxdir;
                            } else {
                                ballydir = -ballydir;
                            }


                        }
                    }
                }
            }

            ballposX += ballxdir;
            ballposy += ballydir;

            //LEFT BORDER
            if (ballposX < 0) {
                ballxdir = -ballxdir;
            }
            //TOP BORDER
            if (ballposy < 0) {
                ballydir = -ballydir;
            }
            //RIGHT BORDER
            if (ballposX > 670) {
                ballxdir = -ballxdir;
            }

        }

        repaint();

    }

    @Override
    public void keyTyped(KeyEvent e) { }
    @Override
    public void keyReleased(KeyEvent e) { }

    @Override

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballposX = 120;
                ballposy = 350;
                ballxdir = -1;
                ballydir = -2;
                playerX = 310;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);

                repaint();

            }
        }
    }
    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

}
