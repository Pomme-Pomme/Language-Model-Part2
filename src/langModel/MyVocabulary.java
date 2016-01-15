package langModel;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


/**
 * Class MyVocabulary: class implementing the interface Vocabulary.
 * 
 * @author ... (2015)
 *
 */
public class MyVocabulary implements Vocabulary {
	/**
	 * The set of words corresponding to the vocabulary.
	 */
	protected Set<String> vocabulary;

	
	/**
	 * Constructor.
	 */
	public MyVocabulary(){
		this.vocabulary = new TreeSet<String>();
	}
	
	
	@Override
	public int getSize() {
		return vocabulary.size();
	}

	@Override
	public Set<String> getWords() {
		return vocabulary;
	}

	@Override
	public boolean contains(String word) {
		return vocabulary.contains(word);
	}

	@Override
	public void addWord(String word) {
		vocabulary.add(word);
	}

	@Override
	public void removeWord(String word) {
		vocabulary.remove(word);
	}

	@Override
	public void scanNgramSet(Set<String> ngramSet) {
		//Pour chaque Ngram
		for(String s : ngramSet){
			//Si il n'y a ni espace, ni tabulation, on ajoute le mot
			if(!s.contains(" ") && !s.contains("\t")) {
				vocabulary.add(s);
			}
		}	
	}

	@Override
	public void readVocabularyFile(String filePath) {
		try{
			Scanner sc = new Scanner(new File(filePath));
			
			//Pour chaque lgne du fichier
			while(sc.hasNextLine()) {
				String line = sc.nextLine();
				//On ajoute le mot
				vocabulary.add(line);
			}
			
			}catch(FileNotFoundException e){
				System.out.println(e.getMessage());
			}
	}

	@Override
	public void writeVocabularyFile(String filePath) {
		try {
			FileWriter fw = new FileWriter(filePath, true);
			BufferedWriter bw = new BufferedWriter(fw);
			//On écrit chaque mot du vocabulaire dans le buffer
			for(String s : vocabulary){
				bw.write(s+"\n");
			}
			bw.flush();
			bw.close();
		} catch (IOException ioe) {
			System.out.println("Erreur: ");
			ioe.printStackTrace();
		}
	}

}
