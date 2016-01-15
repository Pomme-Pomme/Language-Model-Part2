package langModel;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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
		ngramCounts = new HashMap<String, Integer>();
		this.order=1;
	}


	/**
	 * Setter of the maximal order of the ngrams considered.
	 * 
	 * In practice, the method will be called when parsing the training corpus, 
	 * or when parsing the NgramCounts file (using the maximal n-gram length encountered).
	 * 
	 * @param order the maximal order of n-grams considered.
	 */
	public/*private, pour méthode avec choix d'order*/ void setMaximalOrder (int order) {
		this.order=order;
	}

	
	@Override
	public int getMaximalOrder() {
		return this.order;
	}

	
	@Override
	public int getNgramCounterSize() {
		return this.ngramCounts.size();
	}

	
	@Override
	public int getTotalWordNumber(){
		return this.nbWordsTotal;
	}
	
	
	@Override
	public Set<String> getNgrams() {
		return this.ngramCounts.keySet();
	}

	
	@Override
	public int getCounts(String ngram) {
		if(this.ngramCounts.containsKey(ngram)){
			return this.ngramCounts.get(ngram);
		}else{
			return 0;
		}
	}
	

	@Override
	public void incCounts(String ngram) {
		//si on a le ngram
		if(this.ngramCounts.keySet().contains(ngram)) {
			//on augmente de un son nombre d'occurence
			this.ngramCounts.put(ngram, ngramCounts.get(ngram)+1);
			//et on augmente le nmbre de mot total
			this.nbWordsTotal++;
		}//si on avait pas ce mot
		else {
			//on l'ajoute
			this.ngramCounts.put(ngram, 1);
			//et on augmente le nombre de mot total
			this.nbWordsTotal++;
		}
	}

	
	@Override
	public void setCounts(String ngram, int counts) {
		this.ngramCounts.put(ngram, counts);
	}


	@Override
	public void scanTextString(String text, int maximalOrder) {
		
		//Si on a un nouvel ordre, on le met à jour
		if(maximalOrder > this.order){	
			this.setMaximalOrder(maximalOrder);
		}
		
		//On découpe la phrase
		String[] sentences = text.split("\n");
		ArrayList<String> l = new ArrayList<String>();
		
		//Pour chaque phrase
		for(String s : sentences) {
			//On génère les ngram avec cette phrase et on les ajoutes à la liste de passage
			l.addAll(NgramUtil.generateNgrams(s, 1, maximalOrder));
		}
		//On incrémente chaque ngram généré
		for(String ngram : l) {
			incCounts(ngram);
		}
	}

	
	@Override
	public void scanTextFile(String filePath, int maximalOrder) {
		
		//Si on a un nouvel ordre, on le met à jour
		if(maximalOrder > this.order){
			this.setMaximalOrder(maximalOrder);
		}
		
		try {
			//On lit le fichier
			Scanner sc = new Scanner(new File(filePath));
			
			//Pour chaque ligne
			while(sc.hasNextLine()) {
				//On génère les ngram et on les incrémente
				scanTextString(sc.nextLine(), maximalOrder);
			}
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}


	@Override
	public void writeNgramCountFile(String filePath) {
		try {
			//crée un filewriter CaD un Objet pour écrire dans fichier filepath
			FileWriter fw = new FileWriter(filePath, true);
			//on crée un buffer en écriture sur ce fichier
			BufferedWriter bw = new BufferedWriter(fw);
			//pour chaque nGram on l'écrit dans le buffer et on le fait suivre de son nombre d'occurences
			for(String s : ngramCounts.keySet()){
				bw.write(s+"\t"+ngramCounts.get(s)+"\n");
			}
			// on vide le buffer si il reste des données dedans
			bw.flush();
			bw.close();
			//la manipulation des Filewriter et BufferedWriter peut lancer des exceptions
		} catch (IOException ioe) {
			System.out.println("Erreur: ");
			ioe.printStackTrace();
		}
	}

	
	@Override
	public void readNgramCountsFile(String filePath) {
		try{
		//On lit le fichier	
		Scanner sc = new Scanner(new File(filePath));
		
		//Pour chaque ligne (composé du ngram et du nombre d'occurence, séparés par une tabulation)
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			//On découpe la ligne au niveau de la tabulation
			String[] lineCut = line.split("\t");
			
			//Si on a un nouvel ordre, on le met à jour
			if(lineCut[0].split("\\s+").length > order){
				this.order = lineCut[0].split("\\s+").length ;
			}
			
			//Si on a déjà ce ngram, on additionne son nombre d'occurence
			if(ngramCounts.keySet().contains(lineCut[0])){
				ngramCounts.put(lineCut[0],ngramCounts.get(lineCut[0])+ Integer.parseInt(lineCut[1]));
			}else{//Sinon, on l'ajoute
				ngramCounts.put(lineCut[0], Integer.parseInt(lineCut[1]));
			}
		}
		
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
}
