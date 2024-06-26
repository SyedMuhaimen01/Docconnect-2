package controllers;

import java.io.IOException;

public interface SearchStrategy {
    void search(String userInput) throws IOException;
}
