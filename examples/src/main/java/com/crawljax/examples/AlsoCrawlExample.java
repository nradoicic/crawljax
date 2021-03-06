package com.crawljax.examples;

import com.crawljax.core.CrawljaxController;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;
import com.crawljax.core.configuration.InputSpecification;

/**
 * Crawls google.com in IE.
 */
public final class AlsoCrawlExample {

	private static final String URL = "http://www.google.com";

	private static final String ALL_ANCHORS = "a";
	private static final String LANGUAGE_TOOLS = "Language Tools";

	private static final String HEADER_XPATH = "//DIV[@id='guser']";

	private static final int MAX_CRAWL_DEPTH = 1;
	private static final int MAX_STATES = 10;

	/**
	 * Entry point
	 */
	public static void main(String[] args) {
		CrawljaxConfigurationBuilder builder = CrawljaxConfiguration.builderFor(URL);
		
		builder.alsoCrawl("http://video.google.ca/");
		builder.alsoCrawl("http://maps.google.ca/");
		builder.alsoCrawl("https://plus.google.com"); 
		builder.alsoCrawl("https://play.google.com"); 
		builder.alsoCrawl("http://www.youtube.com"); 
		builder.alsoCrawl("http://news.google.ca"); 
		builder.alsoCrawl("https://mail.google.com"); 
		builder.alsoCrawl("https://drive.google.com"); 
		builder.alsoCrawl("https://www.google.com"); 
		builder.alsoCrawl("http://translate.google.ca"); 
		builder.alsoCrawl("http://books.google.ca"); 
		builder.alsoCrawl("http://www.blogger.com"); 
		builder.alsoCrawl("http://picasaweb.google.ca"); 
		builder.alsoCrawl("https://accounts.google.com"); 
		
		builder.crawlRules().clickDefaultElements();
		builder.crawlRules().dontClick(ALL_ANCHORS).underXPath(HEADER_XPATH);
		builder.crawlRules().dontClick(ALL_ANCHORS).withText(LANGUAGE_TOOLS);

		// limit the crawling scope
		builder.setMaximumStates(MAX_STATES);
		builder.setMaximumDepth(MAX_CRAWL_DEPTH);

		builder.crawlRules().setInputSpec(getInputSpecification());

		CrawljaxController crawljax = new CrawljaxController(builder.build());
		crawljax.run();
	}

	private static InputSpecification getInputSpecification() {
		InputSpecification input = new InputSpecification();

		// enter "Crawljax" in the search field
		input.field("q").setValue("Crawljax");
		return input;
	}

	private AlsoCrawlExample() {
		// Utility class
	}
}
