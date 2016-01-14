package langModel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class MyLaplaceLanguageModelTest {

	String sentence1 = "<s> Antoine écoute Thom </s>";
	String sentence2 = "<s> Denis écoute une autre chanson </s>";
	String sentence3 = "<s> Elle écoute une chanson de Lionel </s>";
	
	@Test
	public void getNgramProbTest() {
		LanguageModel mnlm1 = new MyLaplaceLanguageModel();
		MyNgramCounts mnc1 = new MyNgramCounts();
		
		mnc1.scanTextString(sentence1, 1);
		mnc1.scanTextString(sentence2, 1);
		mnc1.scanTextString(sentence3, 1);
		
		mnlm1.setNgramCounts(mnc1);
	
		
		double res = mnlm1.getNgramProb("<s>");
		assertEquals(res, (double)4/32, 0.0001);
		res = mnlm1.getNgramProb("une");
		assertEquals(res, (double)3/32, 0.0001);
		res = mnlm1.getNgramProb("pomme");
		assertEquals(res, (double)1/32, 0.0001);
		
		res = mnlm1.getNgramProb("<s>");
		assertEquals(res, (double)4/32, 0.0001);
		res = mnlm1.getNgramProb("Elle");
		assertEquals(res, (double)2/32, 0.0001);
		res = mnlm1.getNgramProb("écoute");
		assertEquals(res, (double)4/32, 0.0001);
		res = mnlm1.getNgramProb("</s>");
		assertEquals(res, (double)4/32, 0.0001);
		
		
		
		LanguageModel mnlm2 = new MyLaplaceLanguageModel();
		MyNgramCounts mnc2 = new MyNgramCounts();
		
		mnc2.scanTextString(sentence1, 2);
		mnc2.scanTextString(sentence2, 2);
		mnc2.scanTextString(sentence3, 2);
		
		mnlm2.setNgramCounts(mnc2);
		
		res = mnlm2.getNgramProb("<s>");
		assertEquals(res, (double)1.0, 0.0001);
		res = mnlm2.getNgramProb("<s> Antoine");
		assertEquals(res, (double)2/15, 0.0001);
		res = mnlm2.getNgramProb("une autre");
		assertEquals(res, (double)2/14, 0.0001);
		res = mnlm1.getNgramProb("une pomme");
		assertEquals(res, (double)1/32, 0.0001);
	}

	
	
	@Test
	public void getSentenceProbTest(){
		
		LanguageModel mnlm1 = new MyLaplaceLanguageModel();
		MyNgramCounts mnc1 = new MyNgramCounts();
		
		mnc1.scanTextString(sentence1, 1);
		mnc1.scanTextString(sentence2, 1);
		mnc1.scanTextString(sentence3, 1);
		
		mnlm1.setNgramCounts(mnc1);
		
		double res = mnlm1.getSentenceProb("<s> Antoine dépend de Thom </s>");
		System.out.println(res);
		assertEquals(res, 0, 0.0001);
		res = mnlm1.getSentenceProb("<s> Elle écoute une chanson</s>");
		System.out.println(res);
		assertEquals(res, (4/32)*(2/32)*(4/32)*(3/32)*(2/32)*(4/32), 0.0001);
		
		
		LanguageModel mnlm2 = new MyLaplaceLanguageModel();
		MyNgramCounts mnc2 = new MyNgramCounts();
		
		mnc2.scanTextString(sentence1, 2);
		mnc2.scanTextString(sentence2, 2);
		mnc2.scanTextString(sentence3, 2);
		
		mnlm2.setNgramCounts(mnc2);
	
		res = mnlm1.getSentenceProb("<s> Antoine dépend de Thom </s>");
		System.out.println(res);
		assertEquals(res, (2/12)*(2/15)*(1/12)*(1/12)*(1/13)*(2/15), 0.0001);
		res = mnlm1.getSentenceProb("<s> Elle écoute une chanson </s>");
		System.out.println(res);
		assertEquals(res, (2/12)*(2/15)*(2/12)*(3/15)*(2/14)*(2/14) , 0.0001);
		
		
	}
	
	@Rule
	public TestName name = new TestName();

	@Before
	public void printSeparator()
	{
		System.out.println("\n=== " + name.getMethodName() + " =====================");
	}
}
