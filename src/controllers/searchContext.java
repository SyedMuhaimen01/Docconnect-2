package controllers;

import java.io.IOException;

public class searchContext {
	  private SearchStrategy searchStrategy;

	    public void setSearchStrategy(SearchStrategy searchStrategy) {
	        this.searchStrategy = searchStrategy;
	    }

	    public void executeSearch(String userInput) throws IOException {
	        searchStrategy.search(userInput);
	    }
}
