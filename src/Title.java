import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		
		JButton credits = new JButton("Credits");
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

    void setActive (boolean isActive) {
    	this.setVisible(isActive);
		Component [] buttons = this.getComponents();
		for (Component button: buttons) {
			button.setEnabled(isActive);
		}
    }
    
	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getActionCommand().equals("Run an easy maze")) {
			engine.setIsTitle(false);
			this.setActive(false);
		}
		if (action.getActionCommand().equals("Exit")) {
			engine.stop();
		}
	}

}
