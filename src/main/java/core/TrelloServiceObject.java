package core;

import static org.hamcrest.Matchers.lessThan;

import beans.TrelloBoardResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpStatus;

public class TrelloServiceObject extends TrelloProperties {

    private static final String TRELLO_KEY = "key";
    private static final String TRELLO_TOKEN = "token";
    private static final String BOARD_NAME = "name";
    private static final String URL = "url";

    private Method requestMethod;

    private Map<String, String> parameters;

    private TrelloServiceObject(Map<String, String> parameters, Method method) {
        this.parameters = parameters;
        this.requestMethod = method;
    }

    public static ApiRequestBuilder requestBuilder() {
        return new ApiRequestBuilder();
    }

    public static class ApiRequestBuilder {
        private Map<String, String> parameters = new HashMap<>();
        private Method requestMethod = Method.POST;

        public ApiRequestBuilder setMethod(Method method) {
            requestMethod = method;
            return this;
        }

        public ApiRequestBuilder setBackground(String background) {
            parameters.put("prefs/background", background);
            return this;
        }

        public ApiRequestBuilder setDescription(String description) {
            parameters.put("desc", description);
            return this;
        }

        public ApiRequestBuilder setPermissions(String permissions) {
            parameters.put("prefs/permissionLevel", permissions);
            return this;
        }

        public ApiRequestBuilder setOptionWhoCanCommentCardBoard(String comments) {
            parameters.put("prefs/comments", comments);
            return this;
        }

        public TrelloServiceObject buildRequest() {
            return new TrelloServiceObject(parameters, requestMethod);
        }
    }

    public Response sendRequestToCreateBoard() {
        return RestAssured
            .given(requestSpecification()).log().all()
            .queryParams(parameters)
            .queryParams(BOARD_NAME, properties.getProperty(BOARD_NAME))
            .request(requestMethod, properties.getProperty(URL))
            .prettyPeek();
    }

    public Response sendRequestToUpdateBoard(String boardId) {
        return RestAssured
            .given(requestSpecification()).log().all()
            .queryParams(parameters)
            .request(Method.PUT, properties.getProperty(URL) + boardId)
            .prettyPeek();
    }

    public Response sendRequestToDeleteBoard(String boardId) {
        return RestAssured
            .given(requestSpecification()).log().all()
            .request(Method.DELETE, properties.getProperty(URL) + boardId)
            .prettyPeek();
    }

    public static TrelloBoardResponse getBoardResponse(Response response) {
        TrelloBoardResponse boardResponse = new Gson()
            .fromJson(response.asString().trim(), new TypeToken<TrelloBoardResponse>() {
            }.getType());
        return boardResponse;
    }

    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .addQueryParam(TRELLO_KEY, properties.getProperty(TRELLO_KEY))
            .addQueryParam(TRELLO_TOKEN, properties.getProperty(TRELLO_TOKEN))
            .build();
    }

    public static ResponseSpecification goodResponseSpecification() {
        return new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectResponseTime(lessThan(10000L))
            .expectStatusCode(HttpStatus.SC_OK)
            .build();
    }

    public static ResponseSpecification badResponseSpecification() {
        return new ResponseSpecBuilder()
            .expectContentType(ContentType.JSON)
            .expectResponseTime(lessThan(10000L))
            .expectStatusCode(HttpStatus.SC_BAD_REQUEST)
            .build();
    }

    public static ResponseSpecification resourceNotFoundResponseSpecification() {
        return new ResponseSpecBuilder()
            .expectContentType(ContentType.TEXT)
            .expectResponseTime(lessThan(10000L))
            .expectStatusCode(HttpStatus.SC_NOT_FOUND)
            .build();
    }
}

