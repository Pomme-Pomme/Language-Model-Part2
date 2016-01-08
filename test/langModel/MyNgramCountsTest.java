package langModel;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class MyNgramCountsTest {

	String sentence = "<s> cette phrase est de taille 9 . </s>";
	String ngram = "où commence l' historique de cet n-gramme";
	
	
	@Test
	public void testincCounts() {
		
		MyNgramCounts mnc = new MyNgramCounts();
		mnc.incCounts("<s> cette");
		mnc.incCounts("<s> cette");
		mnc.incCounts("cette phrase est");
		
		assertEquals(mnc.getNgrams().contains("<s> cette"), true);
		assertEquals(mnc.getNgrams().contains("cette phrase est"), true);
		assertEquals(mnc.getNgrams().contains("cette phrase"), false);
		assertEquals(mnc.getCounts("<s> cette"), 2);
		assertEquals(mnc.getCounts("cette phrase est"), 1);
	}
	
	@Test
	public void testscanTextString() {
		
		MyNgramCounts mnc = new MyNgramCounts();
		mnc.scanTextString(sentence, 2);
		
		assertEquals(mnc.getNgrams().contains("<s> cette"), true);
		assertEquals(mnc.getNgrams().contains("cette phrase est"), false);
		assertEquals(mnc.getNgrams().contains("cette phrase"), true);
		assertEquals(mnc.getNgrams().contains("est 9"), false);
		assertEquals(mnc.getNgrams().contains("taille"), true);
	}
	
	@Test
	public void testwriteNgramCountFile() throws FileNotFoundException {
		
		MyNgramCounts mnc = new MyNgramCounts();
		
		mnc.scanTextString(sentence, 2);
		
		File myFile = new File("test.txt");
		myFile.delete();
		mnc.writeNgramCountFile("test.txt");
		
		Scanner sc = new Scanner(new File("test.txt"));
		
		while(sc.hasNextLine()){
			String s = sc.nextLine();
			if(s.equals( "<s> 9\t1")){
				assertEquals(true, false);
			}
			if(s.equals("est de\t2")){
				assertEquals(true, false);
			}
		}

	}

	@Test
	public void testreadNgramCountsFile() {
		
		MyNgramCounts mnc = new MyNgramCounts();
		mnc.readNgramCountsFile("test2.txt");
		
		assertEquals(mnc.getNgrams().contains("<s> cette"), true);
		assertEquals(mnc.getNgrams().contains("cette phrase est"), false);
		assertEquals(mnc.getNgrams().contains("cette phrase"), true);
		assertEquals(mnc.getNgrams().contains("est 9"), false);
		assertEquals(mnc.getNgrams().contains("taille"), true);
		
		assertEquals(mnc.getCounts("<s> cette"), 1);
		assertEquals(mnc.getCounts("cette phrase"), 1);
		assertEquals(mnc.getCounts("taille"), 1);
	}
	
	
	
	@Rule
	public TestName name = new TestName();

	@Before
	public void printSeparator()
	{
		System.out.println("\n=== " + name.getMethodName() + " =====================");
	}
	
}
