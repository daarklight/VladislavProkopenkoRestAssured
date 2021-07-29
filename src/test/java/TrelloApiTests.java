
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
import core.DataProvidersBoard;
import java.util.List;
import org.hamcrest.Matchers;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

public class TrelloApiTests extends TrelloProperties {

    private static final String BOARD_NAME = "name";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String INCORRECT_BACKGROUND_MESSAGE = "badBackgroundMessage";
    private static final String MISSING_RESOURCE_MESSAGE = "missingResourceMessage";
    private static final String PRIVATE_PERMISSION_LEVEL = "privatePermissionLevel";

    private String boardId = null;

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

    // TEST 1
    @Test(groups = {"groupToDeleteBoardAfterTestStep"})
    public void checkThatDefaultBoardDescriptionIsEmpty() {
        TrelloBoardResponse result = getBoardResponse(
            requestBuilder()
                .buildRequest()
                .sendRequestToUpdateBoard(boardId)
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());
        assertThat("Default board description is not empty", result.getDesc(), Matchers.blankOrNullString());
    }

    // TEST 2
    @Test(groups = {"groupToDeleteBoardAfterTestStep"},
          dataProviderClass = DataProvidersBoard.class, dataProvider = "correctColors")
    public void changeBoardBackgroundToCorrectValueAndCheckColorInResponse(String color) {
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

    // TEST 3
    @Test(groups = {"groupToDeleteBoardAfterTestStep"},
          dataProviderClass = DataProvidersBoard.class, dataProvider = "incorrectColors")
    public void changeBoardBackgroundToIncorrectValueAndCheckResponse(String color) {
        TrelloBoardResponse result = getBoardResponse(
            requestBuilder()
                .setBackground(color)
                .buildRequest()
                .sendRequestToUpdateBoard(boardId)
                .then().assertThat()
                .spec(badResponseSpecification())
                .and()
                .body(Matchers.allOf(containsString(properties.getProperty(INCORRECT_BACKGROUND_MESSAGE)),
                    containsString(properties.getProperty(ERROR_MESSAGE))))
                .extract().response());
    }

    // TEST 4
    @Test(groups = {"groupToDeleteBoardAfterTestStep"},
          dataProviderClass = DataProvidersBoard.class, dataProvider = "descriptionsWithCorrectLength")
    public void changeBoardDescriptionToValueOfCorrectLengthAndCheckDescriptionInResponse(String description) {
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

    // TEST 5
    @Test(groups = {"groupToDeleteBoardAfterTestStep"})
    public void checkThePossibilityToCreateBoardsWithTheSameNames() {
        TrelloBoardResponse result = getBoardResponse(
            requestBuilder()
                .buildRequest()
                .sendRequestToUpdateBoard(boardId)
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());
        assertThat("The name of first board is not equal to assigned name",
            result.getName(), Matchers.is(properties.getProperty(BOARD_NAME)));

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
            result2.getName(), Matchers.is(properties.getProperty(BOARD_NAME)));

        // Delete second board:
        requestBuilder()
            .buildRequest()
            .sendRequestToDeleteBoard(secondBoardId);
    }

    // TEST 6
    @Test(groups = {"groupToDeleteBoardAfterTestStep"})
    public void checkThatDefaultBoardIsMembersPrivate() {
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

    // TEST 7
    @Test
    public void checkThatDeletedBoardIsMissingInResponse() {
        // Delete board:
        requestBuilder()
            .buildRequest()
            .sendRequestToDeleteBoard(boardId);

        requestBuilder()
            .buildRequest()
            .sendRequestToUpdateBoard(boardId)
            .then().assertThat()
            .spec(resourceNotFoundResponseSpecification())
            .body(Matchers.containsString(properties.getProperty(MISSING_RESOURCE_MESSAGE)));
    }

    // TEST 8
    @Test(groups = {"groupToDeleteBoardAfterTestStep"})
    public void checkThatBoardNameIsConsistentWithTheNameInRequest() {
        TrelloBoardResponse result = getBoardResponse(
            requestBuilder()
                .buildRequest()
                .sendRequestToUpdateBoard(boardId)
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());
        assertThat("Board name is not consistent with requested name",
            result.getName(), Matchers.is(properties.getProperty(BOARD_NAME)));
    }

    // TEST 9
    @Test(groups = {"groupToDeleteBoardAfterTestStep"},
          dataProviderClass = DataProvidersBoard.class, dataProvider = "whoCanCommentCardsOnTheBoard-publicOptions")
    public void checkThatPublicCommentOptionsAreDisabledWithPrivatePermissionLevel(String comments) {
        requestBuilder()
            .setPermissions(properties.getProperty(PRIVATE_PERMISSION_LEVEL))
            .setOptionWhoCanCommentCardBoard(comments)
            .buildRequest()
            .sendRequestToUpdateBoard(boardId)
            .then().assertThat()
            .statusCode(400);
    }

    // TEST 10
    @Test(groups = {"groupToDeleteBoardAfterTestStep"},
          dataProviderClass = DataProvidersBoard.class, dataProvider = "permissionLevels")
    public void checkThatAllPermissionLevelsAreEnabledAndCorrectPermissionStatusIsReturnedInResponse(
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

