package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Game extends JFrame implements ActionListener {

    private JPanel panel;
    private Timer timer;
    private final int FIELD_SIZE = 3;
    private Box[][] box = new Box[FIELD_SIZE][FIELD_SIZE];
    private ArrayList<Coord> coordFigure = new ArrayList<>();
    private Box currentBox = Box.x;
    private boolean inGame = true;
    private boolean winO = false;
    private boolean winX = false;
    private boolean colOne = false;
    private boolean colTwo = false;
    private boolean colThree = false;
    private boolean rowOne = false;
    private boolean rowTwo = false;
    private boolean rowThree = false;
    private boolean digOne = false;
    private boolean digTwo = false;


    public static void main(String[] args) {
        new Game();
    }

    Game(){
        timer = new Timer(20,this);
        timer.start();
        initPanel();
        initFrame();
    }

    private void initFrame(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setTitle("XO");
        setVisible(true);
        addMouseListener(new Mouseboard());
    }

    private void initPanel(){
        panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(5.0f));
                g2.setColor(Color.white);
                g2.drawLine(100,10,100,290);
                g2.drawLine(200,10,200,290);
                g2.drawLine(10,100,290,100);
                g2.drawLine(10,200,290,200);
                for(Coord coord: coordFigure){
                    g.drawImage(getImage(getBox(coord).name(),"png"),coord.x*100,coord.y*100,this);
                }
                g2.setColor(Color.LIGHT_GRAY);
                if(digOne&&!inGame)
                    g2.drawLine(10,10,290,290);
                if(digTwo&&!inGame)
                    g2.drawLine(290,10,10,290);
                if(colOne&&!inGame)
                    g2.drawLine(50,10,50,290);
                if(colTwo&&!inGame)
                    g2.drawLine(150,10,150,290);
                if(colThree&&!inGame)
                    g2.drawLine(250,10,250,290);
                if(rowOne&&!inGame)
                    g2.drawLine(10,50,290,50);
                if(rowTwo&&!inGame)
                    g2.drawLine(10,150,290,150);
                if(rowThree&&!inGame)
                    g2.drawLine(10,250,290,250);

                if(!inGame){
                    JFrame frame1 = new JFrame();
                    JPanel panel1 = new JPanel(){
                        @Override
                        protected void paintComponent(Graphics g){
                            super.paintComponent(g);
                            g.drawImage(getImage("f","gif"),-150,-150,this);
                            g.setFont(new Font("123",Font.BOLD,20));
                            g.setColor(Color.white);
                            if(winO){
                                String str = "Win O";
                                g.drawString(str,40,60);
                            }
                            if(winX){
                                String str = "Win X";
                                g.drawString(str,40,60);
                            }
                        }
                    };
                    panel1.setPreferredSize(new Dimension(125,125));
                    frame1.add(panel1);
                    frame1.pack();
                    frame1.setLocationRelativeTo(null);
                    frame1.setTitle("Winner");
                    frame1.setVisible(true);
                }
            }
        };
        panel.setPreferredSize(new Dimension(300,300));
        panel.setBackground(Color.GRAY);
        add(panel);
    }

    private Image getImage(String name,String format){
        String filename = "/img/"+name+"."+format;
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame) {
            checkWin();
            repaint();
        }

    }

    private void checkWin(){
        if(box[0][0]==Box.x&&box[1][0]==Box.x&&box[2][0]==Box.x){
            winX =true;
            inGame = false;
            rowOne =true;
        }
        if(box[0][0]==Box.o&&box[1][0]==Box.o&&box[2][0]==Box.o){
            winO =true;
            inGame = false;
            rowOne = true;
        }
        if(box[0][1]==Box.x&&box[1][1]==Box.x&&box[2][1]==Box.x){
            winX =true;
            inGame = false;
            rowTwo = true;
        }
        if(box[0][1]==Box.o&&box[1][1]==Box.o&&box[2][1]==Box.o){
            winO =true;
            inGame = false;
            rowTwo = true;
        }
        if(box[0][2]==Box.x&&box[1][2]==Box.x&&box[2][2]==Box.x){
            winX =true;
            inGame = false;
            rowThree = true;
        }
        if(box[0][2]==Box.o&&box[1][2]==Box.o&&box[2][2]==Box.o){
            winO =true;
            inGame = false;
            rowThree = true;
        }
        //////////////////////////////////////////////////////////
        if(box[0][0]==Box.x&&box[0][1]==Box.x&&box[0][2]==Box.x){
            winX =true;
            inGame = false;
            colOne = true;
        }
        if(box[0][0]==Box.o&&box[0][1]==Box.o&&box[0][2]==Box.o){
            winO =true;
            inGame = false;
            colOne = true;
        }
        if(box[1][0]==Box.x&&box[1][1]==Box.x&&box[1][2]==Box.x){
            winX =true;
            inGame = false;
            colTwo = true;
        }
        if(box[1][0]==Box.o&&box[1][1]==Box.o&&box[1][2]==Box.o){
            winO =true;
            inGame = false;
            colTwo = true;
        }
        if(box[2][0]==Box.x&&box[2][1]==Box.x&&box[2][2]==Box.x){
            winX =true;
            inGame = false;
            colThree = true;
        }
        if(box[2][0]==Box.o&&box[2][1]==Box.o&&box[2][2]==Box.o){
            winO =true;
            inGame = false;
            colThree = true;
        }
        /////////////////////////////////////////////////////////
        if(box[0][0]==Box.o&&box[1][1]==Box.o&&box[2][2]==Box.o){
            winO =true;
            inGame = false;
            digOne = true;
        }
        if(box[0][0]==Box.x&&box[1][1]==Box.x&&box[2][2]==Box.x){
            winX =true;
            inGame = false;
            digOne = true;
        }
        if(box[2][0]==Box.o&&box[1][1]==Box.o&&box[0][2]==Box.o){
            winO =true;
            inGame = false;
            digTwo = true;
        }
        if(box[2][0]==Box.x&&box[1][1]==Box.x&&box[0][2]==Box.x){
            winX =true;
            inGame = false;
            digTwo = true;
        }


    }

    public class Mouseboard implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            changeFigure();


            if(checkBox(e.getPoint())) {
                if ((e.getX() <=105&&e.getX()>=0) && e.getY() <=127) {
                    box[0][0] = currentBox;
                    coordFigure.add(new Coord(0, 0));
                }
                if ((e.getX() <=207&&e.getX()>=106) && e.getY() <=127) {
                    box[1][0] = currentBox;
                    coordFigure.add(new Coord(1,0 ));
                }
                if ((e.getX() >= 207&&e.getX()<=307) && e.getY() <=127) {
                    box[2][0] = currentBox;
                    coordFigure.add(new Coord(2, 0));
                }
                if ((e.getX() <=105&&e.getX()>=0) && (e.getY() >=127&&e.getY()<=232)) {
                    box[0][1] = currentBox;
                    coordFigure.add(new Coord(0, 1));
                }
                if ((e.getX() <=207&&e.getX()>=106) && (e.getY() >=127&&e.getY()<=232)) {
                    box[1][1] = currentBox;
                    coordFigure.add(new Coord(1, 1));
                }

                if ((e.getX() >= 207&&e.getX()<=307) && (e.getY() >=127&&e.getY()<=232)) {
                    box[2][1] = currentBox;
                    coordFigure.add(new Coord(2, 1));
                }
                if ((e.getX() <=105&&e.getX()>=0) && (e.getY() >=233&&e.getY()<=327)) {
                    box[0][2] = currentBox;
                    coordFigure.add(new Coord(0, 2));
                }
                if ((e.getX() <=207&&e.getX()>=106) && (e.getY() >=233&&e.getY()<=327)) {
                    box[1][2] = currentBox;
                    coordFigure.add(new Coord(1, 2));
                }

                if ((e.getX() >= 207&&e.getX()<=307) && (e.getY() >=233&&e.getY()<=327)) {
                    box[2][2] = currentBox;
                    coordFigure.add(new Coord(2, 2));
                }

            }
            if(e.getButton()==MouseEvent.BUTTON2){
                box = new Box[FIELD_SIZE][FIELD_SIZE];
                coordFigure = new ArrayList<>();
                inGame = true;
                winO = false;
                winX = false;
                colOne = false;
                colTwo = false;
                colThree = false;
                rowOne = false;
                rowTwo = false;
                rowThree = false;
                digOne = false;
                digTwo = false;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    private Box getBox(Coord coord){
        return box[coord.x][coord.y];
    }

    private boolean checkBox(Point p){
        if(box[p.x/105][p.y/127]==Box.x||box[p.x/105][p.y/127]==Box.o)
            return false;
        return true;
    }

    private void changeFigure(){
        if(currentBox==Box.o)
            currentBox=Box.x;
        else
            currentBox=Box.o;
    }
}
