package GUI;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	public ImagePanel() {}
	
	public void addImage(BufferedImage i) {
		this.image = i;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
	}
	
	

}
