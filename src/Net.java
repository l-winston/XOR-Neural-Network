import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Net {
	ArrayList<float[]> layers = new ArrayList<float[]>();
	ArrayList<float[]> layersZ = new ArrayList<float[]>();
	ArrayList<float[][]> weights = new ArrayList<float[][]>();
	ArrayList<float[]> derivatives = new ArrayList<float[]>();

	public final float e = 2;
	// public float[] in = new float[2];
	// public float[] hidden = new float[4];
	// public float[][] hiddenWeights = new float[hidden.length][in.length];
	// public float[] hiddenZ = new float[hidden.length];
	// public float[] out = new float[1];
	// public float[][] outWeights = new float[out.length][hidden.length];
	// public float[] outZ = new float[out.length];

	public Net(int[] layerInfo) {
		for (int i = 0; i < layerInfo.length; i++) {
			layers.add(new float[layerInfo[i]]);
		}
		for (int i = 1; i < layerInfo.length; i++) {
			layersZ.add(new float[layerInfo[i]]);
			derivatives.add(new float[layerInfo[i]]);
			weights.add(new float[layerInfo[i]][layerInfo[i - 1]]);
		}

	}

	public void propagate() {

		for (int i = 0; i < layersZ.size(); i++) {
			for (int j = 0; j < layersZ.get(i).length; j++) {
				layersZ.get(i)[j] = 0;
			}
		}

		for (int i = 1; i < layers.size(); i++) {
			for (int j = 0; j < weights.get(i - 1).length; j++) {
				for (int k = 0; k < weights.get(i - 1)[0].length; k++) {
					layersZ.get(i - 1)[j] += layers.get(i - 1)[k] * weights.get(i - 1)[j][k];
				}
				layers.get(i)[j] = sig(layersZ.get(i - 1)[j]);

			}
		}

		// hiddenZ = new float[hidden.length];
		//
		// for (int i = 0; i < hidden.length; i++) {
		// for (int j = 0; j < in.length; j++) {
		// hiddenZ[i] += in[j] * hiddenWeights[i][j];
		// }
		// hidden[i] = sig(hiddenZ[i]);
		// }
		//
		// outZ = new float[out.length];
		//
		// for (int i = 0; i < out.length; i++) {
		// for (int j = 0; j < hidden.length; j++) {
		// outZ[i] += hidden[j] * outWeights[i][j];
		// }
		// out[i] = sig(outZ[i]);
		// }
	}

	public void randomize() {
		for (float[][] f : weights) {
			for (int i = 0; i < f.length; i++) {
				for (int j = 0; j < f[0].length; j++) {
					f[i][j] = (float) (Math.random() / 2 - 0.5 / 2);
				}
			}
		}

		// for (int i = 0; i < hiddenWeights.length; i++) {
		// for (int j = 0; j < hiddenWeights[0].length; j++) {
		// hiddenWeights[i][j] = (float) (Math.random() / 2 - 0.5 / 2);
		// }
		// }
		//
		// for (int i = 0; i < outWeights.length; i++) {
		// for (int j = 0; j < outWeights[0].length; j++) {
		// outWeights[i][j] = (float) (Math.random() / 2 - 0.5 / 2);
		// }
		// }

	}

	public void exportNet(String s) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(s);

		for (float[][] f : weights) {
			for (int i = 0; i < f.length; i++) {
				for (int j = 0; j < f[0].length; j++) {
					pw.print(f[i][j] + " ");
				}
				pw.println();
			}
		}
		//
		// for (int i = 0; i < hiddenWeights.length; i++) {
		// for (int j = 0; j < hiddenWeights[0].length; j++) {
		// pw.print(hiddenWeights[i][j] + " ");
		// }
		// pw.println();
		// }
		//
		// for (int i = 0; i < outWeights.length; i++) {
		// for (int j = 0; j < outWeights[0].length; j++) {
		// pw.print(outWeights[i][j] + " ");
		// }
		// pw.println();
		// }
		pw.close();
	}

	public void printNet() {
		// System.out.println("IN VALUES:");
		// for (int i = 0; i < in.length; i++) {
		// System.out.print(in[i] + "\t");
		// }
		// System.out.println();
		//
		// for (int i = 0; i < hiddenWeights.length; i++) {
		// System.out.println(Arrays.toString(hiddenWeights[i]));
		// }
		//
		// System.out.println("HIDDEN VALUES:");
		//
		// for (int i = 0; i < hidden.length; i++) {
		// System.out.print(hidden[i] + "\t");
		// }
		// System.out.println();
		//
		// for (int i = 0; i < outWeights.length; i++) {
		// System.out.println(Arrays.toString(outWeights[i]));
		// }
		//
		// System.out.println("OUT VALUES:");
		//
		// for (int i = 0; i < out.length; i++) {
		// System.out.print(out[i] + "\t");
		// }
		// System.out.println();
	}

	public float invsig(float d) {
		return sig(d) * (1 - sig(d));
	}

	public float sig(float d) {
		return (float) (1 / (1 + Math.pow(Math.E, -d)));
	}

	public void backprop(float sol) {
		float[] label = {sol};
//		for (int i = 0; i < outWeights[0].length; i++) {
//			for (int j = 0; j < outWeights.length; j++) {
//				float change = hidden[i] * invsig(outZ[j]) * 2 * (out[0] - label);
//				outWeights[j][i] -= change * e;
//			}
//		}
//		for (int i = 0; i < hiddenWeights[0].length; i++) {
//			for (int j = 0; j < hiddenWeights.length; j++) {
//				float change = in[i] * invsig(hiddenZ[j]);
//				float temp = 0;
//				for (int k = 0; k < out.length; k++) {
//					temp += outWeights[k][j] * invsig(outZ[k]) * 2 * (out[k] - label);
//				}
//				change *= temp;
//				hiddenWeights[j][i] -= change * e;
//			}
//		}
		
		for(int l = weights.size()-1; l >= 0; l--){
			float[][] w = weights.get(l);
			for(int i = 0; i < w[0].length; i++){
				
				for(int j = 0; j < w.length; j++){
					float change = layers.get(l)[i] * invsig(layersZ.get(l)[j]);
					float temp = 0;
					if(l == weights.size()-1){
						temp = 2*(layers.get(layers.size()-1)[j] - label[j]);
						derivatives.get(derivatives.size()-1)[j] = temp;
					}else{
						for (int k = 0; k < layersZ.get(l+1).length; k++) {
							temp += weights.get(l+1)[k][j] * invsig(layersZ.get(l+1)[k]) * derivatives.get(l+1)[k];
						}
						derivatives.get(l)[j] = temp;
					}
					
					change*= temp;
					w[j][i] -= change*e;
				}
			}
		}
//
//		for (int i = 0; i < weights.get(weights.size() - 1)[0].length; i++) {
//			for (int j = 0; j < weights.get(weights.size() - 1).length; j++) {
//				float change = layers.get(1)[i] * invsig(layersZ.get(layersZ.size() - 1)[j]) * 2
//						* (layers.get(layers.size() - 1)[0] - label[0]);
//				weights.get(weights.size() - 1)[j][i] -= change * e;
//			}
//		}
//		for (int i = 0; i < weights.get(0)[0].length; i++) {
//			for (int j = 0; j < weights.get(0).length; j++) {
//				float change = layers.get(0)[i] * invsig(layersZ.get(0)[j]);
//				float temp = 0;
//				for (int k = 0; k < layers.get(layers.size() - 1).length; k++) {
//					temp += weights.get(weights.size() - 1)[k][j] * invsig(layersZ.get(layersZ.size() - 1)[k]) * 2
//							* (layers.get(layers.size() - 1)[k] - label[0]);
//				}
//				change *= temp;
//				weights.get(0)[j][i] -= change * e;
//			}
//		}

	}

}
