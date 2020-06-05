import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter; 
import java.io.IOException;
import java.lang.Math;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Screen extends JPanel implements ActionListener, KeyListener, MouseListener{

	private Board b;
	private Color gray, green, black;
	private int r, c, best_time;
	private long start_time, seconds_elapsed, minute_time;
	private boolean completed, begun;
	private Font font;
	private JButton check, easy, medium, hard;
	private Scanner scan;

	public Screen(){
		this.setLayout(null);
		this.addMouseListener(this);
		this.addKeyListener(this);

		gray = new Color(192,192,192);
		green = new Color(153,255,153);
		black = new Color(0,0,0);
		r = Integer.MIN_VALUE;
		c = Integer.MIN_VALUE;
		completed = false;
		begun = false;
		font = new Font("Times New Roman",Font.BOLD,36);

		check = new JButton("Check Accuracy");
		check.setBounds(490,45,150,50);
		check.addActionListener(this);
		this.add(check);
		check.setVisible(false);
		check.setFocusable(false);

		easy = new JButton("Easy");
		easy.setBounds(400,275,100,50);
		easy.addActionListener(this);
		this.add(easy);

		medium = new JButton("Medium");
		medium.setBounds(400,375,100,50);
		medium.addActionListener(this);
		this.add(medium);

		hard = new JButton("Hard");
		hard.setBounds(400,475,100,50);
		hard.addActionListener(this);
		this.add(hard);

		/* best_time initializes with the maximum value for an integer. It is then updated after a lower score is gotten, 
		   so that their high score is saved when they close the program. */
		try{
			scan = new Scanner(new File("HighScore.txt"));
			while(scan.hasNextLine()){
				best_time = Integer.parseInt(scan.nextLine());
			}
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}

		this.setFocusable(true);
	}

	//Sets up the size of the window
	public Dimension getPreferredSize() {
        return new Dimension(900,700);
    }

    //Draws the board/numbers from Board.java along with the timer, text, and gray selection box
    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	if ((r >= 0 && r <= 8) && (c >= 0 && c <= 8) && completed == false){
    		g.setColor(gray);
    		g.fillRect(100+r*60,100+c*60,60,60);
    	}
    	if (completed){
			g.setFont(font);
			g.setColor(black);
			g.drawString("You have solved the puzzle!",20,40);
			if(minute_time*60 + seconds_elapsed < best_time){
				best_time = (int)(minute_time*60 + seconds_elapsed);
				try {
			    	FileWriter myWriter = new FileWriter("HighScore.txt");
			    	myWriter.write(best_time + "");
			    	myWriter.close();
			    } catch (IOException e) {
			    	e.printStackTrace();
			    }
			}
			easy.setVisible(true);
			medium.setVisible(true);
			hard.setVisible(true);
			begun = false;
    	}
    	if (begun == true && completed == false){
    		b.drawMe(g);
    		seconds_elapsed = ((System.nanoTime() - start_time)/1000000000) - (60 * minute_time);
    		if (seconds_elapsed >= 60){
    			minute_time += 1;
    			seconds_elapsed -= 60;
    		}
    		g.setColor(black);
			g.drawString("Time: " + minute_time + " m " + seconds_elapsed + " s.",20,670);
    	} else {
    		g.setFont(font);
    		g.setColor(black);
    		g.drawString("Select the map difficulty to begin.",180,200);
    	}
    	if(best_time < Integer.MAX_VALUE && begun){
    		g.drawString("Best time: " + Math.floorDiv(best_time,60) + " m " + best_time%60 + " s",600,30);
    	}
    	repaint();
    }

    //Controls what happens when buttons are clicked
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == check){
    		completed = b.checkAnswers();
    	}
    	if(e.getSource() == easy){
    		begin(1);
    		check.setVisible(true);
    		completed = false;
    	}
    	if(e.getSource() == medium){
    		begin(2);
    		check.setVisible(true);
    		completed = false;
    	}
    	if(e.getSource() == hard){
    		begin(3);
    		check.setVisible(true);
    		completed = false;
    	}
    	repaint();
    }
    
    public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}

	//Figures out which square has been clicked
	public void mousePressed(MouseEvent e){
		if (begun == true){
			r = Math.floorDiv(e.getX()-100,60);
			c = Math.floorDiv(e.getY()-100,60);
			repaint();
		}
	}

	//You guess by typing in numbers
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode() == 49){
			b.guess(r,c,1);
		}
		if(e.getKeyCode() == 50){
			b.guess(r,c,2);
		}
		if(e.getKeyCode() == 51){
			b.guess(r,c,3);
		}
		if(e.getKeyCode() == 52){
			b.guess(r,c,4);
		}
		if(e.getKeyCode() == 53){
			b.guess(r,c,5);
		}
		if(e.getKeyCode() == 54){
			b.guess(r,c,6);
		}
		if(e.getKeyCode() == 55){
			b.guess(r,c,7);
		}
		if(e.getKeyCode() == 56){
			b.guess(r,c,8);
		}
		if(e.getKeyCode() == 57){
			b.guess(r,c,9);
		}
		repaint();
	}
	
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}

	//Initializes the board with the given difficulty and closes the intro menu. Starts the stopwatch.
	public void begin(int d){
		b = new Board(100,100,d);
    	easy.setVisible(false);
    	medium.setVisible(false);
    	hard.setVisible(false);
    	begun = true;
    	start_time = System.nanoTime();
    	minute_time = 0;
	}
}