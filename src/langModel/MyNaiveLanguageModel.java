package langModel;


/**
 * Class MyNaiveLanguageModel: class implementing the interface LanguageModel by creating a naive language model,
 * i.e. a n-gram language model with no smoothing.
 * 
 * @author ... (2015)
 *
 */
public class MyNaiveLanguageModel implements LanguageModel {
	/**
	 * The NgramCounts corresponding to the language model.
	 */
	protected NgramCounts ngramCounts;
	
	/**
	 * The vocabulary of the language model.
	 */
	protected Vocabulary vocabulary;
	
	
	/**
	 * Constructor.
	 */
	public MyNaiveLanguageModel(){
		//TODO
	}
	

	@Override
	public void setNgramCounts(NgramCounts ngramCounts) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLMOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getVocabularySize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Double getNgramProb(String ngram) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getSentenceProb(String sentence) {
		// TODO Auto-generated method stub
		return null;
	}

}
