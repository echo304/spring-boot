package com.sangboak.core.validator;

import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class StringContentValidator implements ConstraintValidator<AllowedContent, String> {

    static final List<String> NOT_ALLOWED_WORDS = List.of(
            "나쁜말",
            "험한말",
            "무서운말",
            "바보",
            "똥"
    );
    static final Pattern NOT_ALLOWED_LINK = Pattern.compile(
            "^(https?://)?(?:[^@/\\n]+@)?(?:www\\.)?([\\w.]+)?(naver\\.com|daum\\.net).?",
            Pattern.CASE_INSENSITIVE);

    @Override
    public void initialize(AllowedContent sanePostContent) {

    }

    @Override
    public boolean isValid(String content, ConstraintValidatorContext ctx) {
        boolean isContentNotAllowed =
                notAllowedWordsContained(content) ||
                        notAllowedLinkContained(content);

        return !isContentNotAllowed;
    }

    private boolean notAllowedWordsContained(String content) {
        return NOT_ALLOWED_WORDS.stream().anyMatch(content::contains);
    }

    private boolean notAllowedLinkContained(String content) {
        Document doc = Jsoup.parseBodyFragment(content);
        Elements aTags = doc.getElementsByTag("a");
        for (Element element : aTags) {
            String href = element.attr("href");
            if (NOT_ALLOWED_LINK.matcher(href).matches()) {
                return true;
            }
        }
        return false;
    }
}
