package servlets;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

class Surface extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;	
	
	public Surface(BufferedImage image){
		this.image = image;
	}

	private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
       
        g2d.drawString("Java 2D", 50, 50);
        g2d.drawImage(image, 1, 1, null);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}