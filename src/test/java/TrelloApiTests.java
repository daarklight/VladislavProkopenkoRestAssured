import static core.TrelloServiceObject.badResponseSpecification;
import static core.TrelloServiceObject.getBoardResponse;
import static core.TrelloServiceObject.goodResponseSpecification;
import static core.TrelloServiceObject.requestBuilder;
import static core.TrelloServiceObject.resourceNotFoundResponseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;

import beans.TrelloBoardResponse;
import core.TrelloProperties;
import core.BoardsData;
import java.util.List;
import java.util.Properties;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

public class TrelloApiTests {

    private static final String BOARD_NAME = "name";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String INCORRECT_BACKGROUND_MESSAGE = "badBackgroundMessage";
    private static final String MISSING_RESOURCE_MESSAGE = "missingResourceMessage";
    private static final String PRIVATE_PERMISSION_LEVEL = "privatePermissionLevel";

    private String boardId;
    private Properties testdataProperties;

    @BeforeClass
    public void setUp() {
        TrelloProperties trelloProperties = new TrelloProperties();
        testdataProperties = trelloProperties.testdataProperties;
    }

    @BeforeMethod
    public void createBoard() {
        TrelloBoardResponse result = getBoardResponse(
            requestBuilder()
                .buildRequest()
                .sendRequestToCreateBoard()
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());
        boardId = result.getId();
    }

    // TEST 1: Check that default board description is empty
    @Test(groups = {"groupToDeleteBoardAfterTestStep"})
    public void checkEmptinessOfDefaultBoardDescription() {
        TrelloBoardResponse result = getBoardResponse(
            requestBuilder()
                .buildRequest()
                .sendRequestToUpdateBoard(boardId)
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());
        assertThat("Default board description is not empty", result.getDesc(), Matchers.blankOrNullString());
    }

    // TEST 2: Change board background to correct value and check color in response
    @Test(groups = {"groupToDeleteBoardAfterTestStep"},
          dataProviderClass = BoardsData.class, dataProvider = "correctColors")
    public void checkCorrectBoardBackgrounds(String color) {
        TrelloBoardResponse result = getBoardResponse(
            requestBuilder()
                .setBackground(color)
                .buildRequest()
                .sendRequestToUpdateBoard(boardId)
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());
        assertThat("Background in response is not consistent with background in request",
            result.getPrefs().getBackground(), Matchers.is(color));
    }

    // TEST 3: Change board background to incorrect value and check response
    @Test(groups = {"groupToDeleteBoardAfterTestStep"},
          dataProviderClass = BoardsData.class, dataProvider = "incorrectColors")
    public void checkIncorrectBoardBackgrounds(String color) {
        TrelloBoardResponse result = getBoardResponse(
            requestBuilder()
                .setBackground(color)
                .buildRequest()
                .sendRequestToUpdateBoard(boardId)
                .then().assertThat()
                .spec(badResponseSpecification())
                .and()
                .body(Matchers.allOf(
                    containsString(testdataProperties.getProperty(INCORRECT_BACKGROUND_MESSAGE)),
                    containsString(testdataProperties.getProperty(ERROR_MESSAGE))))
                .extract().response());
    }

    // TEST 4: Change board description to value of correct length and check description in response
    @Test(groups = {"groupToDeleteBoardAfterTestStep"},
          dataProviderClass = BoardsData.class, dataProvider = "descriptionsWithCorrectLength")
    public void checkCorrectDescriptionLength(String description) {
        TrelloBoardResponse result = getBoardResponse(
            requestBuilder()
                .setDescription(description)
                .buildRequest()
                .sendRequestToUpdateBoard(boardId)
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());
        assertThat("Description in response is not consistent with description in request",
            result.getDesc(), Matchers.is(description));
    }

    // TEST 5: Check the possibility to create boards with the same names
    @Test(groups = {"groupToDeleteBoardAfterTestStep"})
    public void checkIdenticalBoardNames() {
        TrelloBoardResponse result = getBoardResponse(
            requestBuilder()
                .buildRequest()
                .sendRequestToUpdateBoard(boardId)
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());
        assertThat("The name of first board is not equal to assigned name",
            result.getName(), Matchers.is(testdataProperties.getProperty(BOARD_NAME)));

        // Create second board:
        TrelloBoardResponse result2 = getBoardResponse(
            requestBuilder()
                .buildRequest()
                .sendRequestToCreateBoard()
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());
        String secondBoardId = result2.getId();
        assertThat("The name of second board is not equal to assigned name",
            result2.getName(), Matchers.is(testdataProperties.getProperty(BOARD_NAME)));

        // Delete second board:
        requestBuilder()
            .buildRequest()
            .sendRequestToDeleteBoard(secondBoardId);
    }

    // TEST 6: Check that default board is private and that only members have an access
    @Test(groups = {"groupToDeleteBoardAfterTestStep"})
    public void checkPrivacyOfDefaultBoard() {
        TrelloBoardResponse result = getBoardResponse(
            requestBuilder()
                .buildRequest()
                .sendRequestToUpdateBoard(boardId)
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());

        List<String> permissionsFields = Lists.newArrayList(result.getPrefs().getPermissionLevel(),
            result.getPrefs().getComments(), result.getPrefs().getInvitations());
        assertThat("Default board is not membersPrivate",
            permissionsFields, contains("private", "members", "members"));
    }

    // TEST 7: Check that deleted board is missing in response
    @Test
    public void checkBoardIsDeleted() {
        // Delete board:
        requestBuilder()
            .buildRequest()
            .sendRequestToDeleteBoard(boardId);

        requestBuilder()
            .buildRequest()
            .sendRequestToUpdateBoard(boardId)
            .then().assertThat()
            .spec(resourceNotFoundResponseSpecification())
            .body(Matchers.containsString(testdataProperties.getProperty(MISSING_RESOURCE_MESSAGE)));
    }

    // TEST 8: Check that board name in response is consistent with board mame in request
    @Test(groups = {"groupToDeleteBoardAfterTestStep"})
    public void checkBoardName() {
        TrelloBoardResponse result = getBoardResponse(
            requestBuilder()
                .buildRequest()
                .sendRequestToUpdateBoard(boardId)
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());
        assertThat("Board name is not consistent with requested name",
            result.getName(), Matchers.is(testdataProperties.getProperty(BOARD_NAME)));
    }

    // TEST 9: Check that public comment options are disabled with private permission level
    @Test(groups = {"groupToDeleteBoardAfterTestStep"},
          dataProviderClass = BoardsData.class, dataProvider = "whoCanCommentCardsOnTheBoard-publicOptions")
    public void checkPublicCommentOptionsWithPrivatePermission(String comments) {
        requestBuilder()
            .setPermissions(testdataProperties.getProperty(PRIVATE_PERMISSION_LEVEL))
            .setOptionWhoCanCommentCardBoard(comments)
            .buildRequest()
            .sendRequestToUpdateBoard(boardId)
            .then().assertThat()
            .statusCode(400);
    }

    // TEST 10: Check that all permission levels are enabled and correct permission status is returned in response
    @Test(groups = {"groupToDeleteBoardAfterTestStep"},
          dataProviderClass = BoardsData.class, dataProvider = "permissionLevels")
    public void checkBoardPermissionLevels(
        String permissions) {
        TrelloBoardResponse result = getBoardResponse(
            requestBuilder()
                .setPermissions(permissions)
                .buildRequest()
                .sendRequestToUpdateBoard(boardId)
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());
        assertThat("Permission level in response is consistent with level in request",
            result.getPrefs().getPermissionLevel(), Matchers.is(permissions));
    }

    @AfterMethod(groups = {"groupToDeleteBoardAfterTestStep"})
    public void deleteCreatedBoard() {
        requestBuilder()
            .buildRequest()
            .sendRequestToDeleteBoard(boardId);
    }
}
