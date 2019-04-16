package main;

import java.util.LinkedList;
import java.util.List;
import main.core.MotifStruct;

public class MotifCombiner {
	
	/**<strong>combineLikeMotifs()</strong>
	 * <p>Will combine like motifs and reduce the array if possible.</p>
	 * @param input List to reduce.
	 * @return Reduced list.
	 */
	public MotifStruct[] combineLikeMotifs(MotifStruct[] input) {
		// Create dynamic list
		List<MotifStruct> sender = new LinkedList<MotifStruct>();
		
		// Horribly iterate through every element like n^2
		for (int i = 0; i < input.length; i ++) {
			for (MotifStruct ms : input) {
				if (ms != null) {
					if (checkMatching(ms, input[i])) {
						MotifStruct temp = new MotifStruct(ms.getMotif(), ms.getMatchMotif(),
											ms.getScore() + input[i].getScore(), input[i].getIndex(),
											input[i].getThreadName());
						input[i] = temp;
					}
				}
			}
			sender.add(new MotifStruct(input[i]));
			input[i] = null;				// This is to stop the repeat addition that happened from elements in the array.
		}
		return sender.toArray(new MotifStruct[sender.size()]);
	}
	
	/**<strong>checkMatching()</strong>
	 * <p>Will check if the motif arrays from input1 and input2 match each other.</p>
	 * @param input1 to check against input2.
	 * @param input2 to check against input1.
	 * @return a boolean of matching or not matching.
	 */
	public boolean checkMatching(MotifStruct input1, MotifStruct input2) {
		for (int i = 0; i < input1.getMotif().length; i ++) {
			if (input1.getMotif()[i] != input2.getMotif()[i]) {
				return false;
			}
		}
		return true;
	}
}
