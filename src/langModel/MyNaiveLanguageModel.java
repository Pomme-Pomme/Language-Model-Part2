package langModel;

import java.util.ArrayList;
import java.util.List;


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
		this.vocabulary = new MyVocabulary();
	}
	

	@Override
	public void setNgramCounts(NgramCounts ngramCounts) {
		this.ngramCounts=ngramCounts;
		this.vocabulary.scanNgramSet(this.ngramCounts.getNgrams());
	}

	@Override
	public int getLMOrder() {
		return this.ngramCounts.getMaximalOrder();
	}

	@Override
	public int getVocabularySize() {
		return this.vocabulary.getSize();
	}

	@Override
	public Double getNgramProb(String ngram) {
		
		double count = this.ngramCounts.getCounts(ngram);
		int order = this.getLMOrder();

		if(order == 1){
			return (count/(this.ngramCounts.getTotalWordNumber()));
		}
		else{
			if((ngram.trim().split("\\s+"))[0] == ngram){
				return 1.0;
			}
			String historique = NgramUtil.getHistory(ngram, order);
			if((this.ngramCounts.getCounts(historique)) == 0){
				return 0.0;
			}else{
				return (count/(this.ngramCounts.getCounts(historique)));
			}
		}
	}

	@Override
	public Double getSentenceProb(String sentence) {
		List<String> listNgram = NgramUtil.decomposeIntoNgrams(sentence, this.getLMOrder());
		Double proba = 1.0;
		
		for(String s: listNgram){
			proba = proba * this.getNgramProb(s);
		}

		return proba;
	}

}