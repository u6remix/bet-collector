package by.bet.feedreader;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import by.blogobet.parser.util.DateParser;
import by.blogobet.parser.util.NumberParser;

public class ParseTest {

	@Test
	public void parseDateTest() {
		Date date = DateParser.parseTimeLine("Tuesday, July 14th, 2015 22:15");
		System.out.println(date);
		date = DateParser.parseTimeLine("Today, 21:30");
		System.out.println(date);
	}
	
	@Test
	public void parseNumber(){
		double res = NumberParser.parseDouble("   +3.25");
		assertEquals(res,3.25,0);
		res = NumberParser.parseDouble("   0 ");
		assertEquals(res,0,0);
		res = NumberParser.parseDouble("  -5 ");
		assertEquals(res,-5,0);
		res = NumberParser.parseDouble("  4/10 ");
		assertEquals(res,4,0);
	}
}
