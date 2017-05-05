import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Title extends JPanel implements ActionListener {
	
	GridBagConstraints layout;
	Font titleFont, buttonFont;
	JLabel title;
	Game engine;
	private int width, height;
	private static final long serialVersionUID = 1L;

	public Title (int w, int h, Game eng) {
		this.engine = eng;
		this.width = w;
		this.height = h;
		this.setSize(width, height);
		this.setBackground(Color.CYAN);
		this.setLayout(new GridBagLayout());
		this.setDoubleBuffered(true);
		setLayout();
	}
	
	private void setLayout() {
    	titleFont = new Font("Dialog",Font.BOLD, 48);
        title = new JLabel("3D Maze (Working Title)");
        title.setFont(titleFont);
		setGridBag(title, 0, 0, 20, 20, 20, 20, GridBagConstraints.CENTER,GridBagConstraints.NONE);
		buttonFont = new Font("Dialog",Font.PLAIN, 20);
		
		JButton easy = new JButton("Run an easy maze");
		easy.setFont(buttonFont);
		easy.addActionListener(this);
		setGridBag(easy, 0, 1, 15, 15, 15, 15, GridBagConstraints.CENTER,GridBagConstraints.NONE);
		
		JButton hard = new JButton("Run a hard maze");
		hard.setFont(buttonFont);
		hard.addActionListener(this);
		setGridBag(hard, 0, 2, 15, 15, 15, 15, GridBagConstraints.CENTER,GridBagConstraints.NONE);
		
		JButton fastest = new JButton("Fastest route for previous maze");
		fastest.setFont(buttonFont);
		fastest.addActionListener(this);
		setGridBag(fastest, 0, 3, 15, 15, 15, 15, GridBagConstraints.CENTER,GridBagConstraints.NONE);
		
		JButton credits = new JButton("Help/Credits");
		credits.setFont(buttonFont);
		credits.addActionListener(this);
		setGridBag(credits, 0, 4, 15, 15, 15, 15, GridBagConstraints.CENTER,GridBagConstraints.NONE);
		
		JButton exit = new JButton("Exit");
		exit.setFont(buttonFont);
		exit.addActionListener(this);
		setGridBag(exit, 0, 5, 15, 15, 15, 15, GridBagConstraints.CENTER,GridBagConstraints.NONE);
	}
	
    private void setGridBag (JComponent comp, int x, int y,int top, int left, int bot, int right, int align, int fill) {
        layout = new GridBagConstraints();
        layout.insets = new Insets(top, left, bot, right);
        if (fill != GridBagConstraints.NONE)
        	layout.fill = fill;
        if (align != GridBagConstraints.NONE)
        	layout.anchor=align;
        layout.gridx = x;
        layout.gridy = y;
        this.add(comp, layout);
    }	
    private JTextArea credits() throws IOException {
        JTextArea credits = new JTextArea ();
        credits.setEditable(false);
        credits.setFont(new Font("Dialog",Font.PLAIN, 12));
        credits.append(readFile(new File("readme.txt")));
        return credits;
    }
	public String readFile(File file) throws IOException {
		Scanner fileScanner = new Scanner(file);
		String contents = "";
		while (fileScanner.hasNextLine()) {
			String line = fileScanner.nextLine();
			contents += line + "\n";
		}
		fileScanner.close();
		return contents;
	}

    void setActive (boolean isActive) {
		Component [] buttons = this.getComponents();
		for (Component button: buttons) {
			button.setVisible(isActive);
			button.setEnabled(isActive);
		}
		this.setEnabled(isActive);
    	this.setVisible(isActive);
    	this.update(this.getGraphics());
    }
    
	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand().equals("Run an easy maze")) {
			engine.newMap(10,10);
			engine.setIsTitle(false);
			this.setActive(false);
		}
		if (action.getActionCommand().equals("Run a hard maze")) {
			engine.newMap(17,17);
			engine.setIsTitle(false);
			this.setActive(false);
		}
		if (action.getActionCommand().equals("Help/Credits")) {
			try {
				JOptionPane.showMessageDialog(this, credits(), "Help/Credits", JOptionPane.PLAIN_MESSAGE);
			} catch (HeadlessException | IOException e) {
				e.printStackTrace();
			}
		}
		if (action.getActionCommand().equals("Exit")) {
			engine.stop();
		}
	}
}