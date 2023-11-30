package com.example.chat.utils;

import com.example.chat.model.Hashtag;
import com.example.chat.servise.HashtagService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateHashtagsAtStartOfServer implements ApplicationRunner {

    private HashtagService hashtagService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(hashtagService.countHashtags() == 0) {
            hashtagService.saveHashtag(new Hashtag("movies", "фільми","Entertainment", "Розваги"));
            hashtagService.saveHashtag(new Hashtag("videogames", "ігри","Entertainment", "Розваги"));
            hashtagService.saveHashtag(new Hashtag("music", "музика","Entertainment", "Розваги"));
            hashtagService.saveHashtag(new Hashtag("anime", "аніме","Entertainment", "Розваги"));
            hashtagService.saveHashtag(new Hashtag("influensers", "інфлюенсери","Entertainment", "Розваги"));
            hashtagService.saveHashtag(new Hashtag("memes", "меми","Entertainment", "Розваги"));
            hashtagService.saveHashtag(new Hashtag("events", "події","Entertainment", "Розваги"));
            hashtagService.saveHashtag(new Hashtag("comics", "комікси","Entertainment", "Розваги"));

            hashtagService.saveHashtag(new Hashtag("nature", "природа","Lifestyle & hobbies", "Лайфстайл & хобі"));
            hashtagService.saveHashtag(new Hashtag("sport", "спорт","Lifestyle & hobbies", "Лайфстайл & хобі"));
            hashtagService.saveHashtag(new Hashtag("hobbies", "хобі","Lifestyle & hobbies", "Лайфстайл & хобі"));
            hashtagService.saveHashtag(new Hashtag("subcultures", "субкультури","Lifestyle & hobbies", "Лайфстайл & хобі"));
            hashtagService.saveHashtag(new Hashtag("lifestyle", "лайфстайл","Lifestyle & hobbies", "Лайфстайл & хобі"));
            hashtagService.saveHashtag(new Hashtag("fashion", "мода","Lifestyle & hobbies", "Лайфстайл & хобі"));
            hashtagService.saveHashtag(new Hashtag("animals", "тварини","Lifestyle & hobbies", "Лайфстайл & хобі"));
            hashtagService.saveHashtag(new Hashtag("beauty", "тварини","Lifestyle & hobbies", "Лайфстайл & хобі"));
            hashtagService.saveHashtag(new Hashtag("plants", "рослини","Lifestyle & hobbies", "Лайфстайл & хобі"));

            hashtagService.saveHashtag(new Hashtag("community", "спільнота","Relationships & wellness", "Відносини & добробут"));
            hashtagService.saveHashtag(new Hashtag("love", "кохання","Relationships & wellness", "Відносини & добробут"));
            hashtagService.saveHashtag(new Hashtag("spirituality", "духовність","Relationships & wellness", "Відносини & добробут"));
            hashtagService.saveHashtag(new Hashtag("health", "здоров’я","Relationships & wellness", "Відносини & добробут"));
            hashtagService.saveHashtag(new Hashtag("support", "підтримка","Relationships & wellness", "Відносини & добробут"));
            hashtagService.saveHashtag(new Hashtag("parenting", "батьківство","Relationships & wellness", "Відносини & добробут"));

            hashtagService.saveHashtag(new Hashtag("art", "мистецтво","Culture & creativity", "Культура & творчість"));
            hashtagService.saveHashtag(new Hashtag("travel", "подорожі","Culture & creativity", "Культура & творчість"));
            hashtagService.saveHashtag(new Hashtag("DIY", "хендмейд","Culture & creativity", "Культура & творчість"));

            hashtagService.saveHashtag(new Hashtag("study", "навчання","Education", "Освіта"));
            hashtagService.saveHashtag(new Hashtag("tech", "технології","Education", "Освіта"));
            hashtagService.saveHashtag(new Hashtag("books", "книжки","Education", "Освіта"));
            hashtagService.saveHashtag(new Hashtag("science", "наука","Education", "Освіта"));
            hashtagService.saveHashtag(new Hashtag("history", "історія","Education", "Освіта"));
            hashtagService.saveHashtag(new Hashtag("news", "історія","Education", "Освіта"));
            hashtagService.saveHashtag(new Hashtag("philosophy", "філософія","Education", "Освіта"));

            hashtagService.saveHashtag(new Hashtag("collaboration", "співпраця","Professional & productivity", "Професійність & продуктивність"));
            hashtagService.saveHashtag(new Hashtag("business", "бізнес","Professional & productivity", "Професійність & продуктивність"));
            hashtagService.saveHashtag(new Hashtag("work", "робота","Professional & productivity", "Професійність & продуктивність"));
            hashtagService.saveHashtag(new Hashtag("volunteering", "волонтерство","Professional & productivity", "Професійність & продуктивність"));
            hashtagService.saveHashtag(new Hashtag("motivation", "мотивація","Professional & productivity", "Професійність & продуктивність"));
            hashtagService.saveHashtag(new Hashtag("finance", "фінанси","Professional & productivity", "Професійність & продуктивність"));

            hashtagService.saveHashtag(new Hashtag("other", "інше","other", "інше"));
        }

    }
}
