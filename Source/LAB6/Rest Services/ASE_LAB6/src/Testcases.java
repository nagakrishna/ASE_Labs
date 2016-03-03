import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.internal.ExactComparisonCriteria;

public class Testcases {

	ReplaceSpaces r = new ReplaceSpaces();
	String text = "kansas city";
	String newText = r.getText(text);
	String ExpectedText = "kansas%20city";
	
	
	@Test
	public void test1() {
		assertEquals(ExpectedText, newText);
	}
	
	String place = "1kansas";
	Boolean ExpectedResult = true;
	
	Boolean ActualResult = r.verifyPlaceNotANumber(place);
	@Test
	public void test2(){
		assertEquals(ExpectedResult, ActualResult);
	}

}
