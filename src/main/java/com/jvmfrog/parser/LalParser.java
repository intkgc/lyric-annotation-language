package com.jvmfrog.parser;

import com.jvmfrog.lang.Language;
import com.jvmfrog.lang.RULanguage;

import java.util.ArrayList;
import java.util.HashMap;

public class LalParser {
    static {
        Language.languages.put("ru", new RULanguage());
    }

    private Language language;
    private final HashMap<String, String> partSymbols = new HashMap<>();
    private final ArrayList<String> lyricStructure = new ArrayList<>();
    private final ArrayList<LyricPart> parts = new ArrayList<>();

    public static void x() {
    }

    public void parse(String lal) {
        String[] lines = lal.split("\\r?\\n");
        LyricPart currentPart = null;
        LyricAnnotation currentAnnotation = null;

        boolean annotations = false;
        boolean lyrics = false;

        StringBuilder lyricBuilder = new StringBuilder();
        StringBuilder annotationBuilder = new StringBuilder();

        for (int i = 0, linesLength = lines.length; i < linesLength; i++) {
            String line = lines[i];
            if (lyrics) {
                if (line.trim().isEmpty() && lines[i + 1].trim().isEmpty()) {
                    lyrics = false;
                    if (currentPart != null) {
                        addPart(currentPart, lyricBuilder.toString());
                    }
                    lyricBuilder = new StringBuilder();
                } else {
                    lyricBuilder.append(line).append("\n");
                    if (i == linesLength - 1)
                        addPart(currentPart, lyricBuilder.toString());
                    continue;
                }
            }

            if (annotations) {
                if (line.startsWith("@")) {
                    annotations = false;
                } else {
                    if (line.trim().isEmpty()) {
                        annotations = false;
                        lyrics = true;
                    } else {
                        annotationBuilder.append("\n").append(line.trim());
                    }
                }
                if (!annotations) {
                    currentAnnotation.annotation = annotationBuilder.toString();
                    currentPart.annotations.add(currentAnnotation);
                }
            }

            if (line.startsWith("@")) {
                annotations = true;
                annotationBuilder = new StringBuilder();
                if (line.startsWith("@::")) {
                    currentAnnotation = new LyricAnnotation();
                    annotationBuilder.append(line.substring(3).trim());
                } else if (line.matches("@\\d+-\\d+:.*")) {
                    String[] values = line.substring(1, line.indexOf(":")).split("-");
                    currentAnnotation = new LyricAnnotation(
                            Integer.parseInt(values[0]),
                            Integer.parseInt(values[1]));
                    annotationBuilder.append(line.substring(line.indexOf(":") + 1).trim());
                }
                continue;
            }

            if (line.startsWith("|")) {
                String[] keys = line.substring(1).split(":");
                if (keys[0].trim().equals("lang")) {
                    language = Language.languages.get(keys[1].trim());
                    putDefaultPartSymbols();
                } else partSymbols.put(keys[0].trim(), keys[1].trim());
            } else if (line.matches(":\\D\\D\\D") || line.matches("\\\\\\D\\D\\D\\d?")) {
                String symbol = line.substring(1, 4);
                if (line.startsWith(":")) {
                    lyricStructure.add(getPartName(line.substring(1)));
                    currentPart = new LyricPart(symbol, false);
                } else {
                    currentPart = new LyricPart(symbol, true);
                }
                if (lines[i + 1].trim().isEmpty()) {
                    i += 1;
                    lyrics = true;
                }
            } else if (line.matches("/\\D\\D\\D\\d?")) {
                lyricStructure.add(line.substring(1));
            }
        }
    }

    private void addPart(LyricPart part, String lyric) {
        part.lyric = lyric.trim();
        if (!part.isStatic) {
            int num = 1;
            for (LyricPart lyricPart : parts) {
                if (lyricPart.getPartSymbol().substring(0, 1)
                        .equals(part.getPartSymbol().substring(0, 1)))
                    num = lyricPart.getNum() + 1;
            }
            part.setNum(num);
        }
        parts.add(part);
    }

    private void putDefaultPartSymbols() {
        partSymbols.put(".", language.verse());
        partSymbols.put("&", language.chorus());
    }

    private String getPartName(String part) {
        int num = 1;
        for (String s : lyricStructure) {
            if (s.substring(0, 3).equals(part))
                num = Integer.parseInt(s.substring(3)) + 1;
        }
        return part + num;
    }

    public String getFullLyric() {
        StringBuilder builder = new StringBuilder();
        for (String partS : lyricStructure) {
            for (LyricPart part : parts) {
                if (partS.equals(part.getPartSymbol())) {
                    builder.append(formatPart(part)).append("\n").append("\n");
                    break;
                }
            }
        }

        return builder.toString();
    }

    private String formatPart(LyricPart part) {
        StringBuilder builder = new StringBuilder();
        builder.append("[").append(partSymbols.get(part.getPartSymbol().substring(0, 1)));
        if (part.getNum() != -1) builder.append(" ").append(part.getNum());
        builder.append("]").append("\n");
        builder.append(part.lyric);
        return builder.toString();
    }
}
