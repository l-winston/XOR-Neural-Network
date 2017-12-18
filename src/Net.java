import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Net {

	public final float e = 2;
	public float[] in = new float[2];
	public float[] hidden = new float[4];
	public float[][] hiddenWeights = new float[hidden.length][in.length];
	public float[] hiddenZ = new float[hidden.length];
	public float[] out = new float[1];
	public float[][] outWeights = new float[out.length][hidden.length];
	public float[] outZ = new float[out.length];

	public Net() {

	}

	public void propagate() {

		hiddenZ = new float[hidden.length];

		for (int i = 0; i < hidden.length; i++) {
			for (int j = 0; j < in.length; j++) {
				hiddenZ[i] += in[j] * hiddenWeights[i][j];
			}
			hidden[i] = sig(hiddenZ[i]);
		}

		outZ = new float[out.length];

		for (int i = 0; i < out.length; i++) {
			for (int j = 0; j < hidden.length; j++) {
				outZ[i] += hidden[j] * outWeights[i][j];
			}
			out[i] = sig(outZ[i]);
		}
	}

	public void randomize() {
		for (int i = 0; i < hiddenWeights.length; i++) {
			for (int j = 0; j < hiddenWeights[0].length; j++) {
				hiddenWeights[i][j] = (float) (Math.random() / 2 - 0.5 / 2);
			}
		}

		for (int i = 0; i < outWeights.length; i++) {
			for (int j = 0; j < outWeights[0].length; j++) {
				outWeights[i][j] = (float) (Math.random() / 2 - 0.5 / 2);
			}
		}

	}

	public void exportNet(String s) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(s);
		for (int i = 0; i < hiddenWeights.length; i++) {
			for (int j = 0; j < hiddenWeights[0].length; j++) {
				pw.print(hiddenWeights[i][j] + " ");
			}
			pw.println();
		}

		for (int i = 0; i < outWeights.length; i++) {
			for (int j = 0; j < outWeights[0].length; j++) {
				pw.print(outWeights[i][j] + " ");
			}
			pw.println();
		}
		pw.close();
	}

	public void printNet() {
		System.out.println("IN VALUES:");
		for (int i = 0; i < in.length; i++) {
			System.out.print(in[i] + "\t");
		}
		System.out.println();

		for (int i = 0; i < hiddenWeights.length; i++) {
			System.out.println(Arrays.toString(hiddenWeights[i]));
		}

		System.out.println("HIDDEN VALUES:");

		for (int i = 0; i < hidden.length; i++) {
			System.out.print(hidden[i] + "\t");
		}
		System.out.println();

		for (int i = 0; i < outWeights.length; i++) {
			System.out.println(Arrays.toString(outWeights[i]));
		}

		System.out.println("OUT VALUES:");

		for (int i = 0; i < out.length; i++) {
			System.out.print(out[i] + "\t");
		}
		System.out.println();
	}

	public float invsig(float d) {
		return sig(d) * (1 - sig(d));
	}

	public float sig(float d) {
		return (float) (1 / (1 + Math.pow(Math.E, -d)));
	}

	public void backprop(float label) {
		for (int i = 0; i < outWeights[0].length; i++) {
			for (int j = 0; j < outWeights.length; j++) {
				float change = hidden[i] * invsig(outZ[j]) * 2 * (out[0] - label);
				outWeights[j][i] -= change * e;
			}
		}
		for (int i = 0; i < hiddenWeights[0].length; i++) {
			for (int j = 0; j < hiddenWeights.length; j++) {
				float change = in[i] * invsig(hiddenZ[j]);
				float temp = 0;
				for (int k = 0; k < out.length; k++) {
					temp += outWeights[k][j] * invsig(outZ[k]) * 2 * (out[k] - label);
				}
				change *= temp;
				hiddenWeights[j][i] -= change * e;
			}
		}
	}

}
