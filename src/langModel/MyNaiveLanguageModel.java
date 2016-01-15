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
		
		//On prend le nombre d'occurence du ngram
		double count = this.ngramCounts.getCounts(ngram);
		//On prend l'ordre
		int order = this.getLMOrder();

		//Si l'ordre est de un, on fait la méthode pour les unigrammes
		if(order == 1){
			//On renvoie le nombre d'occurence sur le nombre de mot total
			return (count/(this.ngramCounts.getTotalWordNumber()));
		}
		else{
			//Si le ngram est d'une taille de un, on renvoie un (premier ngram)
			if((ngram.trim().split("\\s+"))[0] == ngram){
				return 1.0;
			}//sinon on prend l'historique du mot
			String historique = NgramUtil.getHistory(ngram, order);
			
				if((this.ngramCounts.getCounts(historique)) == 0){
					//Si l'historique n'existe pas, on renvoie 1, pour ne pas faire de division par zero
					return count/(this.ngramCounts.getTotalWordNumber());
				}else{
					//Sinon, on renvoie le nombre d'occurence sur l'historique
					return (count/(this.ngramCounts.getCounts(historique)));
				}
		}
	}

	@Override
	public Double getSentenceProb(String sentence) {
		//On découpe la phrase en ngram
		List<String> listNgram = NgramUtil.decomposeIntoNgrams(sentence, this.getLMOrder());
		Double proba = 1.0;
		
		//Pour chaque phrase, on multiplie la proba totale avec la proba de chaque ngram
		for(String s: listNgram){
			proba = proba * this.getNgramProb(s);
		}

		return proba;
	}
	
	/// Ajouté pour choisir ordre
	
	
	public Double getSentenceProb2(String sentence, int orderHere) {
		List<String> listNgram = NgramUtil.decomposeIntoNgrams(sentence, this.getLMOrder());
		Double proba = 1.0;
		
		int order = this.getLMOrder();
		if(orderHere<order){
			this.ngramCounts.setMaximalOrder(orderHere);
		}
		
		
		for(String s: listNgram){
			proba = proba * this.getNgramProb(s);
		}

		this.ngramCounts.setMaximalOrder(order);
		
		return proba;
	}

}