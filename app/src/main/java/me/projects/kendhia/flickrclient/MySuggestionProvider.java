package me.projects.kendhia.flickrclient;

import android.content.SearchRecentSuggestionsProvider;

/**
 * This is the provider to host the recent search queries
 */
public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = MySuggestionProvider.class.getName(); ;
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}