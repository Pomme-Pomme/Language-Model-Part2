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
		if(this.ngramCounts.containsKey(ngram)){
			return this.ngramCounts.get(ngram);
		}else{
			return 0;
		}
	}
	

	@Override
	public void incCounts(String ngram) {
		if(this.ngramCounts.keySet().contains(ngram)) {
			this.ngramCounts.put(ngram, ngramCounts.get(ngram)+1);
			this.nbWordsTotal++;
		}
		else {
			this.ngramCounts.put(ngram, 1);
			this.nbWordsTotal++;
		}
	}

	
	@Override
	public void setCounts(String ngram, int counts) {
		this.ngramCounts.put(ngram, counts);
	}


	@Override
	public void scanTextString(String text, int maximalOrder) {
		
		if(maximalOrder > this.order){
			this.setMaximalOrder(maximalOrder);
		}
		
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
		
		if(maximalOrder > this.order){
			this.setMaximalOrder(maximalOrder);
		}
		
		try {
			Scanner sc = new Scanner(new File(filePath));
			ArrayList<String> l = new ArrayList<String>();
			
			while(sc.hasNextLine()) {
				scanTextString(sc.nextLine(), maximalOrder);
			}
		} catch (FileNotFoundException e){
			System.out.println(e.getMessage());
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
		try{
		Scanner sc = new Scanner(new File(filePath));
		
		while(sc.hasNextLine()) {
			String line = sc.nextLine();
			String[] lineCut = line.split("\t");
			ngramCounts.put(lineCut[0], Integer.parseInt(lineCut[1]));
		}
		
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
}
