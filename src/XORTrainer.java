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

		Net net = new Net();
		net.randomize();

		Point lastCoord = new Point(0, image.getHeight());

		int[] neuron1test = { 1, 1, 0, 0 };
		int[] neuron2test = { 1, 0, 1, 0 };
		int[] label = { 0, 1, 1, 0 };

		// float[] prev = new float[4];

		int cycle = 0;
		int totalCorrect = 0;
		while (true) {

			int numberCorrect = 0;
			cycle++;
			for (int i = 0; i < label.length; i++) {
				net.in[0] = neuron1test[i];
				net.in[1] = neuron2test[i];

				net.propagate();

				if ((net.out[0] > 0.5 && label[i] == 1) || (net.out[0] <= 0.5 && label[i] == 0)) {
					numberCorrect++;
					totalCorrect++;
				}

				// if(Math.abs(out[0]-label[i]) < 0.05){
				// numberCorrect++;
				// totalCorrect++;
				// }

				net.backprop(label[i]);
			}

			numberCorrect /= label.length;

			int interval = 10;

			if (cycle % interval == 0) {
				int x = cycle / interval;
				if (x > image.getWidth()) {
					break;
				}
				int y = (int) (image.getHeight()
						- Math.round(totalCorrect * 1.0 / cycle / label.length * image.getHeight()));

				g2d.drawLine(lastCoord.x, lastCoord.y, x, y);
				frame.repaint();
				lastCoord = new Point(x, y);
			}

			//System.out.println(totalCorrect * 1.0 / cycle / label.length);
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
