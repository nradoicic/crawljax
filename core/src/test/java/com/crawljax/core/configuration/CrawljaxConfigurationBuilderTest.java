package com.crawljax.core.configuration;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItems;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.Before;
import org.junit.Test;

import com.crawljax.browser.EmbeddedBrowser.BrowserType;
import com.crawljax.core.state.Eventable.EventType;

public class CrawljaxConfigurationBuilderTest {

	private CrawljaxConfiguration config;

	@Before
	public void setup() {
		config = CrawljaxConfiguration.builderFor("http://example.com").build();

	}

	@Test
	public void whenBuidlerWithNoOptionsTheDefaultsAreEnabled() throws Exception {

		assertThat(config.getBrowserConfig(),
		        is(new BrowserConfiguration(BrowserType.firefox)));

		assertThat(config.getProxyConfiguration(), is(ProxyConfiguration.noProxy()));

		assertThat(config.getMaximumDepth(), is(CrawljaxConfiguration.DEFAULT_MAX_DEPTH));
		assertThat(config.getMaximumRuntime(), is(CrawljaxConfiguration.DEFAULT_MAX_RUNTIME));
		assertThat(config.getMaximumStates(), is(CrawljaxConfiguration.DEFAULT_MAX_STATES));

	}

	@Test
	public void verifyDefaultCrawlRulesEnabled() {
		CrawlRules rules = config.getCrawlRules();

		assertThat(rules.getWaitAfterEvent(), is(CrawlRules.DEFAULT_WAIT_AFTER_EVENT));
		assertThat(rules.getWaitAfterReloadUrl(), is(CrawlRules.DEFAULT_WAIT_AFTER_RELOAD));

		assertThat(rules.getInputSpecification().getCrawlElements(), is(empty()));

		assertThat(rules.getInvariants(), is(empty()));
		assertThat(rules.getOracleComparators(), is(empty()));
		assertThat(rules.getIgnoredFrameIdentifiers(), is(empty()));

		assertThat(rules.getCrawlEvents(), IsCollectionContaining.hasItem(EventType.click));

		PreCrawlConfiguration preCrawl = rules.getPreCrawlConfig();

		assertThat(preCrawl.getIncludedElements(), hasSize(4));
		assertThat(preCrawl.getExcludedElements(), is(empty()));
		assertThat(preCrawl.getWaitConditions(), is(empty()));
		assertThat(preCrawl.getFilterAttributeNames(),
		        hasItems("closure_hashcode_(\\w)*", "jquery[0-9]+"));
	}
}
