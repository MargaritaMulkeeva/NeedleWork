package com.example.needlework.Adapters;

import com.example.needlework.NetWork.Models.KnittingPatternContent;

import java.util.List;

public class KnittingPatternMarkdownParser {
    public static String GetMarkdownFromPattern(List<KnittingPatternContent> content) {
        String markdown = "";

        for (int i = 0; i < content.size(); i++) {
            switch (content.get(i).getType()) {
                case "header":
                    markdown.concat("#" + content.get(i).getContent() + "<br />");
                    break;
                case "block":
                    markdown.concat(content.get(i).getContent() + "<br />");
                    break;
            }
        }

        return markdown;
    }
}
