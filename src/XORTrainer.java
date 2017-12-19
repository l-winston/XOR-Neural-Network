import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class XORTrainer {
	public static JFrame frame = new JFrame("graph");
	public static BufferedImage image;
	public static Graphics2D g2d;

	public static void main(String[] args) throws FileNotFoundException{
		initFrame();

		Net net = new Net(new int[]{2,2, 4, 1});
		net.randomize();

		Point lastCoord = new Point(0, image.getHeight());
		
		

		int[] XORTest1 = { 1, 1, 0, 0 };
		int[] XORTest2 = { 1, 0, 1, 0 };
		int[] XORLabel = { 0, 1, 1, 0 };

		// float[] prev = new float[4];

		int cycle = 0;
		int totalCorrect = 0;
		while (true) {

			cycle++;
			for (int i = 0; i < XORLabel.length; i++) {
				net.layers.get(0)[0] = XORTest1[i];
				net.layers.get(0)[1] = XORTest2[i];
				
				net.propagate();
				net.backprop(XORLabel[i]);
				double out = net.layers.get(net.layers.size()-1)[0];
				if ((out > 0.5 && XORLabel[i] == 1) || (out <= 0.5 && XORLabel[i] == 0)) {
					totalCorrect++;
				}

			}

			int interval = 10;

			if (cycle % interval == 0) {
				int x = cycle / interval;
				if (x > image.getWidth()) {
					break;
				}
				int y = (int) (image.getHeight()
						- Math.round(totalCorrect * 1.0 / cycle	/ XORLabel.length * image.getHeight()));

				g2d.drawLine(lastCoord.x, lastCoord.y, x, y);
				frame.repaint();
				lastCoord = new Point(x, y);
			}

			System.out.println(totalCorrect + " " + cycle + " " + totalCorrect * 1.0 / cycle /4);
		}

		net.exportNet("net.out");
	}

	public static void initFrame() {
		image = new BufferedImage(1200, 750, BufferedImage.TYPE_INT_RGB);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new JLabel(new ImageIcon(image)));
		frame.pack();
		g2d = image.createGraphics();
		g2d.setBackground(Color.white);
		g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(2));
	}

}
