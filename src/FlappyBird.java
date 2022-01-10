import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class FlappyBird implements ActionListener, MouseListener, KeyListener {
    public  static FlappyBird flappyBird;
    public final int WIDTH = 800, HEIGHT = 600;
    public Renderer renderer;
    public Rectangle bird;
    public ArrayList<Rectangle>  column;
    public Random rand;
    public int ticks, yMotion,score;
    public boolean gameOver, start;

    public FlappyBird(){
        JFrame jFrame = new JFrame();
        Timer timer = new Timer(20,this);
        renderer = new Renderer();
        rand = new Random();
        jFrame.add(renderer);
        jFrame.addMouseListener(this);
        jFrame.setTitle("FlappyBird");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(WIDTH,HEIGHT);
        jFrame.setVisible(true);

        bird = new Rectangle(WIDTH/2-10,HEIGHT/2-10,20,20);
        column = new ArrayList<Rectangle>();
        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        timer.start();

    }
    public static void main(String[] args) {
        flappyBird = new FlappyBird();

    }

    public void repaint(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(0,0,WIDTH,HEIGHT);
        g.setColor(Color.orange);
        g.fillRect(0,HEIGHT -120,WIDTH,150);
        g.setColor(Color.GREEN);
        g.fillRect(0,HEIGHT -120,WIDTH,20);

        g.setColor(Color.RED);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);
        for (Rectangle col:column) {
            paintColumn(g,col);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",1,100));
        if(!start){
            g.drawString("Click to start",100,HEIGHT/2 -50);
        }
        if(gameOver){
            g.drawString("Game Over",100,HEIGHT/2 -50);
        }
        if (!gameOver && start){
            g.drawString(String.valueOf(score),WIDTH/2 -25,100);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ticks ++;
        int speed = 10;
        if(start) {
            for (int i = 0; i < column.size(); i++) {
                Rectangle col = column.get(i);
                col.x -= speed;
            }
            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            }
            for (int i = 0; i < column.size(); i++) {
                Rectangle col = column.get(i);
                if (col.x + col.width < 0) {
                    column.remove(col);
                    if (col.y == 0) {
                        addColumn(false);
                    }
                }

            }
            bird.y += yMotion;
            for (Rectangle col : column) {
                if(col.y == 0 && bird.x + bird.width / 2 > col.x + col.width / 2 - 10 && bird.x + bird.width / 2 < col.x + col.width / 2 + 10){
                    score ++;
                }
                if (col.intersects(bird)) {
                    gameOver = true;
                    if(bird.x <= col.x) {
                        bird.x = col.x - bird.width;

                    }else {
                        if(col.y != 0){
                            bird.y = col.y - bird.height;
                        }else if(bird.y < col.height){
                            bird.y = col.height;
                        }
                    }
                }
            }
            if (bird.y > HEIGHT - 120 || bird.y < 0) {
                gameOver = true;
            }
            if(gameOver){
                bird.y = HEIGHT - 120- bird.height;
            }
        }
        renderer.repaint();
    }
    public void addColumn(boolean  start){
        int space =  300;
        int width = 100;
        int height = 50 + rand.nextInt(300);
        if(start) {
            column.add(new Rectangle(WIDTH + width + column.size() * 300, HEIGHT - height - 120, width, height));
            column.add(new Rectangle(WIDTH + width + (column.size() - 1) * 300, 0, width, HEIGHT - height - space));
        }else{
            column.add(new Rectangle(column.get(column.size() - 1).x + 600, HEIGHT - height - 120, width, height));
            column.add(new Rectangle(column.get(column.size() - 1).x, 0, width, HEIGHT - height - space));
        }

    }

    public void paintColumn(Graphics g, Rectangle col){
        g.setColor(Color.GREEN.darker());
        g.fillRect(col.x,col.y,col.width,col.height);
    }
    public  void jump(){
        if(gameOver){
            bird = new Rectangle(WIDTH/2-10,HEIGHT/2-10,20,20);
            column.clear();
            yMotion = 0;
            score = 0;
            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);
            gameOver = false;
        }
        if(!start){
            start = true;
        }else  if(!gameOver){
            if(yMotion > 0){
                yMotion = 0;
            }
            yMotion -= 10;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            jump();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        jump();
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
