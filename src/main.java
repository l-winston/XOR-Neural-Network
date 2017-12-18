import java.util.Arrays;

public class main {
	public static final float e = 2;
	public static float[] in = new float[2];
	public static float[] hidden = new float[4];
	public static float[][] hiddenWeights = new float[hidden.length][in.length];
	public static float[] hiddenZ = new float[hidden.length];
	public static float[] out = new float[1];
	public static float[][] outWeights = new float[out.length][hidden.length];
	public static float[] outZ = new float[out.length];

	public static void main(String[] args) {
		randomize();

		int[] neuron1test = { 1, 1, 0, 0};
		int[] neuron2test = { 1, 0, 1, 0};
		int[] label = { 0, 1, 1, 0};
		
		float[] prev = new float[100];

		int cycle = 0;
		while (true) {
			int numberCorrect = 0;
			cycle++;
			for (int i = 0; i < label.length; i++) {
				in[0] = neuron1test[i];
				in[1] = neuron2test[i];
				propagate();
				float cost = Math.abs(out[0] - label[i]);
				//System.out.println(out[0] + "\t" + label[i] + "\t" + cost);
				if((out[0] > 0.5 && label[i] ==1)||(out[0] <= 0.5 && label[i] ==0)){
					numberCorrect++;
				}
				backprop(label[i]);
			}
			
			numberCorrect /= 4.0;
			
			
			
			if(cycle < prev.length)prev[cycle] = numberCorrect;
			else {
				for(int i = 0; i < prev.length-1; i++){
					prev[i] = prev[i+1];
				}
				prev[prev.length-1] = numberCorrect;
			}
			
			
			double sum = 0;
			for(int i = 0; i < prev.length; i++){
				sum += prev[i];
			}
			
			
			System.out.println(sum/prev.length);
			if(sum/prev.length == 1.0){
				System.out.println("Cycles taken: " + cycle);
				System.out.println("Time taken: " + System.currentTimeMillis()/1000.0 + " seconds");
				System.out.println("Number correct in a row: " + prev.length);
				break;
			}
		}
	}

	public static void backprop(int label) {
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
				change*= temp;
				hiddenWeights[j][i] -= change * e;
			}
		}
	}

	public static float invsig(float d) {
		return sig(d) * (1 - sig(d));
	}

	public static float sig(float d) {
		return (float) (1 / (1 + Math.pow(Math.E, -d)));
	}

	public static void propagate() {

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

	public static void randomize() {
		for (int i = 0; i < hiddenWeights.length; i++) {
			for (int j = 0; j < hiddenWeights[0].length; j++) {
				hiddenWeights[i][j] = (float) (Math.random()/2 - 0.5/2);
			}
		}

		for (int i = 0; i < outWeights.length; i++) {
			for (int j = 0; j < outWeights[0].length; j++) {
				outWeights[i][j] = (float) (Math.random()/2 - 0.5/2);
			}
		}

	}
	
	public static void printWeights(){
		System.out.println("IN VALUES:");
		for(int i = 0; i < in.length; i++){
			System.out.print(in[i] + "\t");
		}
		System.out.println();
		
		for(int i = 0; i < hiddenWeights.length; i++){
			System.out.println(Arrays.toString(hiddenWeights[i]));
		}
		
		System.out.println("HIDDEN VALUES:");

		for(int i = 0; i < hidden.length; i++){
			System.out.print(hidden[i] + "\t");
		}
		System.out.println();
		
		for(int i = 0; i < outWeights.length; i++){
			System.out.println(Arrays.toString(outWeights[i]));
		}
		
		System.out.println("OUT VALUES:");

		for(int i = 0; i < out.length; i++){
			System.out.print(out[i] + "\t");
		}
		System.out.println();
	}
}
