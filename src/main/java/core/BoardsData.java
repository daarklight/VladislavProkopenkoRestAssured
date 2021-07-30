package core;

import static constants.AccessStatuses.OBSERVERS_ACCESS;
import static constants.AccessStatuses.ORG_ACCESS;
import static constants.AccessStatuses.PRIVATE_ACCESS;
import static constants.AccessStatuses.PUBLIC_ACCESS;
import static constants.Colors.BLACK;
import static constants.Colors.BLUE;
import static constants.Colors.GREEN;
import static constants.Colors.GREY;
import static constants.Colors.LIME;
import static constants.Colors.MAGENTA;
import static constants.Colors.ORANGE;
import static constants.Colors.PINK;
import static constants.Colors.PURPLE;
import static constants.Colors.RED;
import static constants.Colors.SKY;
import static constants.Colors.WHITE;
import static constants.Colors.YELLOW;
import static constants.Descriptions.DESCRIPTION_OF_1710_DIGITS;
import static constants.Descriptions.EMPTY_DESCRIPTION;
import static constants.Descriptions.SHORT_DESCRIPTION;

import org.testng.annotations.DataProvider;

public class BoardsData {

    @DataProvider(name = "correctColors")
    public static Object[][] correctColors() {
        return new Object[][] {
            {ORANGE},
            {BLUE},
            {GREEN},
            {RED},
            {PURPLE},
            {PINK},
            {LIME},
            {SKY},
            {GREY}
        };
    }

    @DataProvider(name = "incorrectColors")
    public static Object[][] incorrectColors() {
        return new Object[][] {
            {WHITE},
            {MAGENTA},
            {YELLOW},
            {BLACK}
        };
    }

    @DataProvider(name = "descriptionsWithCorrectLength")
    public static Object[][] descriptionsWithCorrectLength() {
        return new Object[][] {
            {EMPTY_DESCRIPTION},
            {SHORT_DESCRIPTION},
            {DESCRIPTION_OF_1710_DIGITS}
        };
    }

    @DataProvider(name = "whoCanCommentCardsOnTheBoard-publicOptions")
    public static Object[][] whoCanCommentCardsOnTheBoard() {
        return new Object[][] {
            {OBSERVERS_ACCESS},
            {ORG_ACCESS},
            {PUBLIC_ACCESS}
        };
    }

    @DataProvider(name = "permissionLevels")
    public static Object[][] permissionLevels() {
        return new Object[][] {
            {ORG_ACCESS},
            {PRIVATE_ACCESS},
            {PUBLIC_ACCESS}
        };
    }
}
