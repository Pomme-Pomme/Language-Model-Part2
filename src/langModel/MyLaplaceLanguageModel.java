package langModel;


/**
 * Class MyLaplaceLanguageModel: class inheriting the class MyNaiveLanguageModel by creating 
 * a n-gram language model using a Laplace smoothing.
 * 
 * @author ... (2015)
 *
 */
public class MyLaplaceLanguageModel extends MyNaiveLanguageModel {

	@Override
	public Double getNgramProb(String ngram) {
		double count = this.ngramCounts.getCounts(ngram);
		int order = this.getLMOrder();

		
		if(order == 1){
			return ((count+1)/((this.ngramCounts.getTotalWordNumber()+this.getVocabularySize())));
		}
		else{
			if((ngram.trim().split("\\s+"))[0] == ngram){
				return 1.0;
			}
			String historique = NgramUtil.getHistory(ngram, order);
			return ((count+1)/((this.ngramCounts.getCounts(historique)+this.getVocabularySize())));
		}
	}
}