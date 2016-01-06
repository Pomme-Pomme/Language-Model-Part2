package langModel;


import java.util.ArrayList;
import java.util.List;


/**
 * Class NgramUtil: class containing useful functions to deal with n-grams.
 * 
 * @author N. Hernandez and S. Quiniou (2015)
 *
 */
public class NgramUtil {

	/**
	 * Method counting the number of words in a given sequence 
	 * (the sequence can be a n-gram or a sentence).
	 * 
	 * @param sequence the sequence to consider.
	 * @return the number of words of the given sequence.
	 */
	public static int getSequenceSize (String sequence) {
		//On découpe la phrase
		String[] passage = (sequence.trim()).split("\\s+");
		//On compte le nombre de mots
		return passage.length;
	}

	
	/**
	 * Method parsing the given sentence and generate all the combinations of ngrams,
	 * by varying the order n between the given minOrder and maxOrder.
	 * 
	 * This method will be used in the NgramCount class for counting the ngrams 
	 * occurring in a corpus.
	 * 
	 * Algorithm (one possible algo...)
	 * initialize list of ngrams
	 * for n = minOrder to maxOrder (for each order)
	 * 	 for i = 0 to sentence.length-n (parse the whole sentence)
	 *     initialize ngram string parsedSentence
	 *     for j = i to i+n-1 (create a ngram made of the following sequence of words starting from i to i + the order size)
	 *       ngram = ngram + " " + sentence[j]
	 *     add ngramm to list ngrams 
	 * return list ngrams
	 * 
	 * Example
	 * given the sentence "a b c d e f g", with minOrder=1 and maxOrder=3, it will result in the following list:
	 * [a, b, c, d, e, f, g, a b, b c, c d, d e, e f, f g, a b c, b c d, c d e, d e f, e f g]
	 * 
	 * @param sentence the sentence from which to generate n-grams.
	 * @param minOrder the minimal order of the n-grams to create.
	 * @param maxOrder the maximal order of the n-grams to create.
	 * @return a list of generated n-grams from the sentence.
	 */
	public static List<String> generateNgrams (String sentence, int minOrder, int maxOrder) {
		
		String[] passage = (sentence.trim()).split("\\s+");
		
		ArrayList<String> ngram = new ArrayList<String>();
		
		
		
		for(int n=minOrder; n<=maxOrder; n++){
			for(int i = 0; i <= passage.length-n; i++){
				String parsedSentence="";
					for(int j = i; j <= i+n-1; j++){
						parsedSentence = parsedSentence + " " +passage[j];
					}
				ngram.add(parsedSentence.trim());	
			}
		}
		
		return ngram;
	}


	/**
	 * Method parsing a n-gram and returning its history, i.e. the n-1 preceding words.
	 * 
	 * Example: 
	 *   let the ngram be "l' historique de cette phrase";
	 *   the history will be given for the last word of the ngram, here "phrase":
	 *   if the order is 2 then the history will be "cette"; 
	 *   if the order is 3 then it will be "de cette".
	 * 
	 * @param ngram the n-gram to consider.
	 * @param order the order to consider for the n-gram.
	 * @return history of the given n-gram (the length of the history is order-1).  
	 */
	public static String getHistory (String ngram, int order) {
		//On découpe la phrase
		String[] passage = (ngram.trim()).split("\\s+");
		String solution = "";
		
		if(passage.length>=order){
			for(int i=passage.length-2; i >= passage.length-order; i--){
				solution = passage[i] + " " + solution;
			}
		}
		return solution.trim();
	}

	
	/**
	 * Method decomposing the given sentence into n-grams of the given order.
	 * 
	 * This method will be used in the LanguageModel class for computing 
	 * the probability of a sentence as the product of the probabilities of its n-grams. 
	 * 
	 * Example
	 * given the sentence "a b c d e f g", with order=3,
	 * it will result in the following list:
	 * [a, a b, a b c, b c d, c d e, d e f, e f g] 
	 * 
	 * @param sentence the sentence to consider.
	 * @param order the maximal order for the n-grams to create from the sentence.
	 * @return the list of n-grams constructed from the sentence.
	 */
	public static List<String> decomposeIntoNgrams (String sentence, int order) {
		
		String[] passage = (sentence.trim()).split("\\s+");
		String parsedSentence="";
		
		ArrayList<String> ngram = new ArrayList<String>();
		
		int nbrBoucle = Math.min(order, passage.length+1);
		
		
			for(int l=1;l<nbrBoucle;l++){
				parsedSentence="";
				for(int b=0; b<l && b<passage.length; b++){
					parsedSentence = parsedSentence + " " +passage[b];
				}
				ngram.add(parsedSentence.trim());
			}
		
			for(int i = 0; i <= passage.length-order; i++){
				parsedSentence="";
					for(int j = i; j <= i+order-1; j++){
						parsedSentence = parsedSentence + " " +passage[j];
					}
				ngram.add(parsedSentence.trim());	
			}
		
		
		return ngram;		
	}

	//Méthode de test
	public static void main(String[] args){
		NgramUtil thislol = new NgramUtil();
		String test = "Je n'ai pas compris ce sujet";
		System.out.println(thislol.getSequenceSize(test));
		System.out.println(thislol.generateNgrams(test, 1, 12));
		System.out.println(thislol.getHistory(test, 3));
		System.out.println(thislol.decomposeIntoNgrams(test, 3));
	}
	
}
