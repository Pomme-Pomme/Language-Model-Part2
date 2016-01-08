package langModel;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class MyVocabularyTest {

	@Test
	public void scanNgramSetTest() {
		Vocabulary voc = new MyVocabulary();
		
		MyNgramCounts mnc = new MyNgramCounts();
		mnc.readNgramCountsFile("test2.txt");
		voc.scanNgramSet(mnc.getNgrams());
		
		assertEquals(voc.getWords().contains("1"), false);
		assertEquals(voc.getWords().contains("cette"), true);
		assertEquals(voc.getWords().contains("</s>"), true);
	}

	@Test
	public void readVocabularyFileTest() {
		Vocabulary voc = new MyVocabulary();
		
		voc.readVocabularyFile("test3.txt");
		
		for(String s: voc.getWords()){
			System.out.println(s);
		}
		
		assertEquals(voc.getWords().contains("1"), false);
		assertEquals(voc.getWords().contains("bonjour"), true);
		assertEquals(voc.getWords().contains("</s>"), true);
	}
	
	@Test
	public void writeVocabularyFileTest() throws FileNotFoundException {
		Vocabulary voc = new MyVocabulary();
		
		voc.readVocabularyFile("test3.txt");
	
		File myFile = new File("test.txt");
		myFile.delete();
		voc.writeVocabularyFile("test.txt");
		
		Scanner sc = new Scanner(new File("test.txt"));
		
		while(sc.hasNextLine()){
			String s = sc.nextLine();
			if(s.equals( "<s> cette\t1")){
				assertEquals(true, false);
			}
			if(s.equals("1")){
				assertEquals(true, false);
			}
			if(s.equals("bonjour")){
				assertEquals(true, true);
			}
		}
		
	}


	@Rule
	public TestName name = new TestName();

	@Before
	public void printSeparator()
	{
		System.out.println("\n=== " + name.getMethodName() + " =====================");
	}
}
