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
		//On prend le nombre d'occurence du ngram
		double count = this.ngramCounts.getCounts(ngram);
		//On prend l'ordre
		int order = this.getLMOrder();

		//Si l'ordre est de un, on fait la méthode pour les unigrammes
		if(order == 1){
			//On renvoie le nombre d'occurence+1 sur le nombre de mot total+taille du vocabulaire
			return ((count+1.0)/((this.ngramCounts.getTotalWordNumber()+this.getVocabularySize())));
		}
		else{
			//Si le ngram est d'une taille de un, on renvoie un (premier ngram)
			if((ngram.trim().split("\\s+"))[0] == ngram){
				//Si l'historique n'existe pas, on renvoie 0 
				return 1.0;
			}//sinon on prend l'historique du mot
			String historique = NgramUtil.getHistory(ngram, order);
			//On renvoie le nombre d'occurence+1 sur l'historique+taille du vocabulaire
			return ((count+1.0)/((this.ngramCounts.getCounts(historique)+this.getVocabularySize())));
		}
	}
}