package langModel;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Class MyNgramCounts: class implementing the interface NgramCounts. 
 * 
 * @author N. Hernandez and S. Quiniou (2015)
 *
 */
public class MyNgramCounts implements NgramCounts {
	/**
	 * The maximal order of the n-gram counts.
	 */
	protected int order;

	/**
	 * The map containing the counts of each n-gram.
	 */
	protected Map<String,Integer> ngramCounts;

	/**
	 * The total number of words in the corpus.
	 * In practice, the total number of words will be increased when parsing a corpus 
	 * or when parsing a NgramCounts file (only if the ngram encountered is a unigram one).
	 */
	protected int nbWordsTotal;
	
	
	/**
	 * Constructor.
	 */
	public MyNgramCounts(){
		//TODO
	}


	/**
	 * Setter of the maximal order of the ngrams considered.
	 * 
	 * In practice, the method will be called when parsing the training corpus, 
	 * or when parsing the NgramCounts file (using the maximal n-gram length encountered).
	 * 
	 * @param order the maximal order of n-grams considered.
	 */
	private void setMaximalOrder (int order) {
		// TODO Auto-generated method stub
	}

	
	@Override
	public int getMaximalOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public int getNgramCounterSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public int getTotalWordNumber(){
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	@Override
	public Set<String> getNgrams() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public int getCounts(String ngram) {
		// TODO Auto-generated method stub
		return 0;
	}
	

	@Override
	public void incCounts(String ngram) {
		// TODO Auto-generated method stub
	}

	
	@Override
	public void setCounts(String ngram, int counts) {
		// TODO Auto-generated method stub
	}


	@Override
	public void scanTextString(String text, int maximalOrder) {
		// TODO Auto-generated method stub
	}

	
	@Override
	public void scanTextFile(String filePath, int maximalOrder) {
		// TODO Auto-generated method stub
	}

	
	@Override
	public void writeNgramCountFile(String filePath) {
		// TODO Auto-generated method stub
	}

	
	@Override
	public void readNgramCountsFile(String filePath) {
		// TODO Auto-generated method stub
	}

}
