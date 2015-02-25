package utils;

import org.jetbrains.annotations.NotNull;

public enum Highlighter {
    ON,
    OFF;

    private static Highlighter highlighter = OFF;

    @NotNull
    public static Highlighter getHighlighter() {
        if (highlighter == OFF) {
            highlighter = Highlighter.valueOf(System.getProperty("highlighter", "off").toUpperCase());
        }
        return highlighter;
    }
}
