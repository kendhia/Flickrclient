package me.projects.kendhia.flickrclient;

import android.content.SearchRecentSuggestionsProvider;

public class MySuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "me.projects.kendhia.flickrclient.MySuggestionProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}