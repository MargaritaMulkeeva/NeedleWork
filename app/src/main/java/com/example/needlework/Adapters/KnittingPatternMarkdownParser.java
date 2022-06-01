package com.example.needlework.Adapters;

import com.example.needlework.NetWork.Models.knittingPatterns.KnittingPatternContent;

import java.util.List;

public class KnittingPatternMarkdownParser {
    public static String GetMarkdownFromPattern(List<KnittingPatternContent> content) {
        String markdown = "";

        for (int i = 0; i < content.size(); i++) {
            switch (content.get(i).getType()) {
                case "header":
                    markdown += (content.get(i).getContent() + "\n\n");
                    break;
                case "block":
                    markdown += (content.get(i).getContent() + "\n\n");
                    break;
            }
        }

        return markdown;
    }
}
