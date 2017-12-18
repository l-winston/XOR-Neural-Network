
import java.util.Arrays;
import java.util.Random;

// TODO: maybe whole matches?

public class NeuralNetwork {

	/*	Data Input Format
	 * 	G = Gears
	 * 	P = Pressure
	 * 	# = # of bots
	 * 
	 * 	#G>0.0	#G>0.5	#G>1.0	#G>1.5	#G>2.0	#G>2.5	#G>3.0	#G>3.5	#G>4.0	#G>4.5	#G>5.0	#G>5.5	#G>6.0
	 * 	#P>0	#P>10	#P>20	#P>30	#P>40	#P>50	#P>60
	 * 
	 * Output Data
	 * Red Alliance Score
	 * Blue Alliance Score
	 * Winner Prediction
	 * Pressure Ranking Prediction
	 * Gear Ranking Prediction
	 */
    private static double IH_learningRate = 0.001;
    private static double HO_learningRate = 0.001;

    private static double hiddenValues[] = new double[2];

    private static int trainingInputs[][] = {
    		{0, 0},
    		{0, 1},
    		{1, 0},
    		{1, 1}
    	};
    
    private static int trainingOutputs[] = {0, 1, 1, 0};

    
    private static int problemInputs[][] = {
    		{0, 0},
    		{0, 1},
    		{1, 0},
    		{1, 1}
    	};
    
    private static double IH_weights[][] =  new double[trainingInputs[0].length][hiddenValues.length];
    private static double HO_weights[] = new double[hiddenValues.length];
    
    private static double tempError = 0.0;
    private static double experimentalOut = 0.0;
    private static double RMSerror = 0.0;
    private static double accuracy = 0.20;

    public static void main(String[] args) {
        initWeights();
        int epoch = 0;
        double last = 0;
        double orig = 0;
        while( true ) {
        	double rate = 0;
        	for(int f = 0; f<trainingInputs.length; f++) {
                int patNum = new Random().nextInt(trainingInputs.length);
                calcNet(patNum);
                WeightChangesIH(patNum);
            }
            RMSerror = calcOverallError();
            if( epoch == 0 ) {
            	orig = RMSerror;
            }
            rate = (last - RMSerror);
            System.out.println("epoch = " + epoch + "\tRMS Error = " + RMSerror + "\timprovement: " + (orig-RMSerror) + "\trate: " + rate);
            if( RMSerror<accuracy ) {
            	break;
            }
            
            last = RMSerror;
            epoch++;
            sitRep();
        }
        displayResults();
        for( int f=0; f<problemInputs.length; f++ ) {
        	System.out.println(finalCalc(problemInputs[f]));
        }
    }

    private static void sitRep() {
    	System.out.println("IH Weights: ");
    	for( int f=0; f<IH_weights.length; f++ ) {
    		System.out.println("\tNode " + f + " " + Arrays.toString(IH_weights[f]));
    	}
    	System.out.println("HO Weights: " + Arrays.toString(HO_weights));
    }
    private static void initWeights() {
        for(int f = 0; f<hiddenValues.length; f++) {
            HO_weights[f] = (new Random().nextDouble() - 0.5) / 2.0;
            for(int g = 0; g<trainingInputs[0].length; g++) {
                IH_weights[g][f] = (new Random().nextDouble() - 0.5) / 5.0;
            }
        }
    }
    private static double finalCalc(int[] values) {
        for(int f = 0; f<hiddenValues.length; f++) {
            hiddenValues[f] = 0.0;
            for(int g = 0; g<values.length; g++) {
                hiddenValues[f] += (values[g] * IH_weights[g][f]);
            }
            hiddenValues[f] = activate(hiddenValues[f]);
        }
        experimentalOut = 0.0;
        for(int f = 0; f<hiddenValues.length; f++) {
            experimentalOut += hiddenValues[f] * HO_weights[f];
        }
        return experimentalOut;
    }
    private static void calcNet(final int patNum) {
        for(int f = 0; f<hiddenValues.length; f++) {
            hiddenValues[f] = 0.0;
            for(int g = 0; g<trainingInputs[patNum].length; g++) {
                hiddenValues[f] += (trainingInputs[patNum][g] * IH_weights[g][f]);
            }
            hiddenValues[f] = activate(hiddenValues[f]);
        }
        experimentalOut = 0.0;
        for(int f = 0; f<hiddenValues.length; f++) {
            experimentalOut += hiddenValues[f] * HO_weights[f];
        }
        tempError = experimentalOut - trainingOutputs[patNum];
    }
    private static double activate(final double data) {
    	//return 1.0 / (1.0 + Math.pow(Math.E, -data));
    	return Math.tanh(data);
    }
    private static void WeightChangesIH(final int patNum) {
    	for(int f = 0; f<hiddenValues.length; f++) {
            double weightChange = HO_learningRate * tempError * hiddenValues[f];
            HO_weights[f] -= weightChange;

            if(HO_weights[f]<-15.0){
                HO_weights[f] = -15.0;
            }else if(HO_weights[f] > 15.0){
                HO_weights[f] = 15.0;
            }
        }
        for(int f = 0; f<hiddenValues.length; f++) {
            for(int g = 0; g<trainingInputs[patNum].length; g++) {
                double x = 1 - Math.pow(hiddenValues[f], 2);
                x = x * HO_weights[f] * tempError * IH_learningRate;
                x = x * trainingInputs[patNum][g];
                double weightChange = x;
                IH_weights[g][f] -= weightChange;
            }
        }
    }
    private static double calcOverallError() {
        double errorValue = 0.0;
        for(int f = 0; f<trainingInputs.length; f++) {
             calcNet(f);
             errorValue += Math.pow(tempError, 2);
        }
        errorValue /= trainingInputs.length;  
        return Math.sqrt(errorValue);
    }
    private static void displayResults() {
        for(int f = 0; f<trainingInputs.length; f++) {
            calcNet(f);
            System.out.println("pat = " + (f + 1) + "\t\tactual = " + trainingOutputs[f] + "\tneural = " + experimentalOut);
        }
        System.out.println();
        //sitRep();
    }
}