package com.example.chat.utils;

import com.example.chat.model.Hashtag;
import com.example.chat.model.HashtagsGroup;
import com.example.chat.model.Image;
import com.example.chat.servise.impls.HashtagGroupService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateEtitiesAtStartOfServer implements ApplicationRunner {

    private final HashtagGroupService hashtagGroupService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(hashtagGroupService.countHashtagGroup() == 0) {
            HashtagsGroup ukEntertainment = new HashtagsGroup("Розваги", "uk");
            ukEntertainment.getHashtags().add(new Hashtag("фільми", ukEntertainment ));
            ukEntertainment.getHashtags().add(new Hashtag("відеоігри", ukEntertainment ));
            ukEntertainment.getHashtags().add(new Hashtag("музика", ukEntertainment ));
            ukEntertainment.getHashtags().add(new Hashtag("аніме", ukEntertainment ));
            ukEntertainment.getHashtags().add(new Hashtag("інфлюенсери", ukEntertainment ));
            ukEntertainment.getHashtags().add(new Hashtag("меми", ukEntertainment ));
            ukEntertainment.getHashtags().add(new Hashtag("події", ukEntertainment ));
            ukEntertainment.getHashtags().add(new Hashtag("комікси", ukEntertainment ));

            HashtagsGroup entertainment = new HashtagsGroup("Entertainment", "en");
            entertainment.getHashtags().add(new Hashtag("movies", entertainment));
            entertainment.getHashtags().add(new Hashtag("videogames", entertainment));
            entertainment.getHashtags().add(new Hashtag("music", entertainment));
            entertainment.getHashtags().add(new Hashtag("anime", entertainment));
            entertainment.getHashtags().add(new Hashtag("influensers", entertainment));
            entertainment.getHashtags().add(new Hashtag("memes", entertainment));
            entertainment.getHashtags().add(new Hashtag("events", entertainment));
            entertainment.getHashtags().add(new Hashtag("comics", entertainment));
            entertainment.setUkHashtagsGroup(ukEntertainment);
            hashtagGroupService.saveHashtagGroup(entertainment);

            HashtagsGroup ukLifestyle = new HashtagsGroup("Лайфстайл & хобі", "uk");
            ukLifestyle.getHashtags().add(new Hashtag("природа", ukLifestyle ));
            ukLifestyle.getHashtags().add(new Hashtag("спорт", ukLifestyle ));
            ukLifestyle.getHashtags().add(new Hashtag("хобі", ukLifestyle ));
            ukLifestyle.getHashtags().add(new Hashtag("субкультури", ukLifestyle ));
            ukLifestyle.getHashtags().add(new Hashtag("лайфстайл", ukLifestyle ));
            ukLifestyle.getHashtags().add(new Hashtag("мода", ukLifestyle ));
            ukLifestyle.getHashtags().add(new Hashtag("тварини", ukLifestyle ));
            ukLifestyle.getHashtags().add(new Hashtag("краса", ukLifestyle ));
            ukLifestyle.getHashtags().add(new Hashtag("рослини", ukLifestyle ));

            HashtagsGroup lifestyle = new HashtagsGroup("Lifestyle & hobbies","en");
            lifestyle.getHashtags().add(new Hashtag("nature", lifestyle));
            lifestyle.getHashtags().add(new Hashtag("sport", lifestyle));
            lifestyle.getHashtags().add(new Hashtag("hobbies", lifestyle));
            lifestyle.getHashtags().add(new Hashtag("subcultures", lifestyle));
            lifestyle.getHashtags().add(new Hashtag("lifestyle", lifestyle));
            lifestyle.getHashtags().add(new Hashtag("fashion", lifestyle));
            lifestyle.getHashtags().add(new Hashtag("animals", lifestyle));
            lifestyle.getHashtags().add(new Hashtag("beauty", lifestyle));
            lifestyle.getHashtags().add(new Hashtag("plants", lifestyle));
            lifestyle.setUkHashtagsGroup(ukLifestyle);
            hashtagGroupService.saveHashtagGroup(lifestyle);

            HashtagsGroup ukRelationShips = new HashtagsGroup("Відносини & добробут", "uk");
            ukRelationShips.getHashtags().add(new Hashtag("спільнота", ukRelationShips ));
            ukRelationShips.getHashtags().add(new Hashtag("кохання", ukRelationShips ));
            ukRelationShips.getHashtags().add(new Hashtag("духовність", ukRelationShips ));
            ukRelationShips.getHashtags().add(new Hashtag("здоров’я", ukRelationShips ));
            ukRelationShips.getHashtags().add(new Hashtag("підтримка", ukRelationShips ));
            ukRelationShips.getHashtags().add(new Hashtag("батьківство", ukRelationShips ));

            HashtagsGroup relationShips = new HashtagsGroup("Relationships & wellness","en");
            relationShips.getHashtags().add(new Hashtag("community", relationShips));
            relationShips.getHashtags().add(new Hashtag("love", relationShips));
            relationShips.getHashtags().add(new Hashtag("spirituality", relationShips));
            relationShips.getHashtags().add(new Hashtag("health", relationShips));
            relationShips.getHashtags().add(new Hashtag("support", relationShips));
            relationShips.getHashtags().add(new Hashtag("parenting", relationShips));
            relationShips.setUkHashtagsGroup(ukRelationShips);
            hashtagGroupService.saveHashtagGroup(relationShips);

            HashtagsGroup ukCulture = new HashtagsGroup("Культура & творчість", "uk");
            ukCulture.getHashtags().add(new Hashtag("мистецтво", ukCulture ));
            ukCulture.getHashtags().add(new Hashtag("подорожі", ukCulture ));
            ukCulture.getHashtags().add(new Hashtag("хендмейд", ukCulture ));

            HashtagsGroup culture= new HashtagsGroup("Culture & creativity","en");
            culture.getHashtags().add(new Hashtag("art", culture));
            culture.getHashtags().add(new Hashtag("travel", culture));
            culture.getHashtags().add(new Hashtag("DIY", culture));
             culture.setUkHashtagsGroup(ukCulture);
            hashtagGroupService.saveHashtagGroup(culture);

            HashtagsGroup ukEducation = new HashtagsGroup("Освіта", "uk");
            ukEducation.getHashtags().add(new Hashtag("навчання", ukEducation ));
            ukEducation.getHashtags().add(new Hashtag("технології", ukEducation ));
            ukEducation.getHashtags().add(new Hashtag("книжки", ukEducation ));
            ukEducation.getHashtags().add(new Hashtag("наука", ukEducation ));
            ukEducation.getHashtags().add(new Hashtag("історія", ukEducation ));
            ukEducation.getHashtags().add(new Hashtag("новини", ukEducation ));
            ukEducation.getHashtags().add(new Hashtag("філософія", ukEducation ));

            HashtagsGroup education= new HashtagsGroup("Education","en");
            education.getHashtags().add(new Hashtag("study", education));
            education.getHashtags().add(new Hashtag("tech", education));
            education.getHashtags().add(new Hashtag("books", education));
            education.getHashtags().add(new Hashtag("science", education));
            education.getHashtags().add(new Hashtag("history", education));
            education.getHashtags().add(new Hashtag("news", education));
            education.getHashtags().add(new Hashtag("philosophy", education));
             education.setUkHashtagsGroup(ukEducation);
            hashtagGroupService.saveHashtagGroup(education);

            HashtagsGroup ukProductivity = new HashtagsGroup("Професійність & продуктивність", "uk");
            ukProductivity.getHashtags().add(new Hashtag("співпраця", ukProductivity ));
            ukProductivity.getHashtags().add(new Hashtag("бізнес", ukProductivity ));
            ukProductivity.getHashtags().add(new Hashtag("робота", ukProductivity ));
            ukProductivity.getHashtags().add(new Hashtag("волонтерство", ukProductivity ));
            ukProductivity.getHashtags().add(new Hashtag("мотивація", ukProductivity ));
            ukProductivity.getHashtags().add(new Hashtag("фінанси", ukProductivity ));

            HashtagsGroup productivity= new HashtagsGroup("Professional & productivity","en");
            productivity.getHashtags().add(new Hashtag("collaboration", productivity));
            productivity.getHashtags().add(new Hashtag("business", productivity));
            productivity.getHashtags().add(new Hashtag("work", productivity));
            productivity.getHashtags().add(new Hashtag("volunteering", productivity));
            productivity.getHashtags().add(new Hashtag("motivation", productivity));
            productivity.getHashtags().add(new Hashtag("finance", productivity));
            productivity.setUkHashtagsGroup(ukProductivity);
            hashtagGroupService.saveHashtagGroup(productivity);

            HashtagsGroup ukOther = new HashtagsGroup("інше", "uk");
            ukOther.getHashtags().add(new Hashtag("інше", ukOther ));

            HashtagsGroup other= new HashtagsGroup("other","en");
            other.getHashtags().add(new Hashtag("other", other));
            other.setUkHashtagsGroup(ukOther);
            hashtagGroupService.saveHashtagGroup(other);

        }

    }

    private void createDefaultAvatars() {
        Image image = new Image();
    }
}
