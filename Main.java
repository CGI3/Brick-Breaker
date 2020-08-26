package brickBreaker;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        //Sets the window for our game
            JFrame obj = new JFrame();
            Gameplay gameplay = new Gameplay();
            obj.setBounds(10, 10, 700, 600);
            obj.setTitle("Breakout Ball");
            obj.setResizable(false);
            obj.setVisible(true);
            obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            obj.add(gameplay);
    }

}
