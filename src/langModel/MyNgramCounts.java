package langModel;


import java.io.BufferedWriter;
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
		return this.ngramCounts.get(ngram);
	}
	

	@Override
	public void incCounts(String ngram) {
		if(this.ngramCounts.keySet().contains(ngram)) {
			this.ngramCounts.put(ngram, ngramCounts.get(ngram)+1);
		}
		else {
			this.ngramCounts.put(ngram, 1);
		}
	}

	
	@Override
	public void setCounts(String ngram, int counts) {
		this.ngramCounts.put(ngram, counts);
	}


	@Override
	public void scanTextString(String text, int maximalOrder) {
		String[] sentences = text.split("\n");
		ArrayList<String> l = new ArrayList<String>();
		
		for(String s : sentences) {
			l.addAll(NgramUtil.generateNgrams(s, 1, maximalOrder));
		}
		for(String ngram : l) {
			incCounts(ngram);
		}
	}

	
	@Override
	public void scanTextFile(String filePath, int maximalOrder) {
		Scanner sc = new Scanner(filePath);
		ArrayList<String> l = new ArrayList<String>();
		
		while(sc.hasNextLine()) {
			scanTextString(sc.nextLine(), maximalOrder);
		}
	}

	
	@Override
	public void writeNgramCountFile(String filePath) {
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			for(String s : ngramCounts.keySet()){
				bw.write(s+"\t"+ngramCounts.get(s)+"\n");
			}
			bw.flush();
			bw.close();
		} catch (IOException ioe) {
			System.out.println("Erreur: ");
			ioe.printStackTrace();
		}
	}

	
	@Override
	public void readNgramCountsFile(String filePath) {
		Scanner sc = new Scanner(filePath);
		
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			ngramCounts.put(line.split("\t")[0], Integer.parseInt(line.split("\t")[1]));
		}
		
	}

	public static void main(String[] args) {
		String text = "<s> je ne comprends pas cette phrase </s> \n<s> Meat is good, good is meat </s> \n <s> faut pas écraser les pommes -Lilian Barreteau 2016 </s>";
		for(String s : text.split("\n"))
		System.out.println(s);
	}
}
