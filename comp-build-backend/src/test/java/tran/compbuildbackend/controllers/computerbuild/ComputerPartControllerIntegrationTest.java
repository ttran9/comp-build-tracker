package tran.compbuildbackend.controllers.computerbuild;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import tran.compbuildbackend.controllers.utility.WebUtility;
import tran.compbuildbackend.domain.computerbuild.ComputerPart;
import tran.compbuildbackend.domain.utility.ComputerPartUtility;

import java.net.URI;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;
import static tran.compbuildbackend.constants.computerbuild.ComputerBuildConstants.*;
import static tran.compbuildbackend.constants.fields.ErrorKeyConstants.MESSAGE_KEY;
import static tran.compbuildbackend.constants.fields.FieldConstants.ID_FIELD;
import static tran.compbuildbackend.constants.mapping.MappingConstants.COMPUTER_PART_API;
import static tran.compbuildbackend.constants.mapping.MappingConstants.URL_SEPARATOR;
import static tran.compbuildbackend.constants.tests.TestUtility.*;
import static tran.compbuildbackend.constants.users.UserConstants.*;
import static tran.compbuildbackend.controllers.utility.WebUtility.loginHelper;
import static tran.compbuildbackend.domain.utility.ComputerBuildDetailUtility.*;
import static tran.compbuildbackend.domain.utility.DateUtility.convertDateToString;

@Profile("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ComputerPartControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String token;

    private String computerBuildIdentifier;

    @Before
    public void setUp() throws Exception {
        token = loginHelper(USER_NAME_TO_TEST_OWNERSHIP_ENDPOINTS, USER_PASSWORD, restTemplate);
        computerBuildIdentifier = setComputerBuildIdentifier(restTemplate);
    }

    /*
     * testing a successful computer part creation (happy path)
     */
    @Test
    public void testCreateComputerPartSuccess() throws Exception {
        generateComputerPart();
    }

    /*
     * testing a computer part creation where the user is not the owner so it is expected that the computer part
     * that is returned will have null fields and was not created.
     */
    @Test
    public void testCreateComputerPartNotAsOwner() throws Exception {
        LinkedHashMap contents = generateComputerDetailFailure(TEST_COMPUTER_PART_PLACE_PURCHASED_AT,true);
        assertNotNull(contents.get(MESSAGE_KEY));
        assertEquals(COMPUTER_BUILD_CANNOT_BE_MODIFIED, contents.get(MESSAGE_KEY));
    }

    /*
     * testing a computer part creation where the user is the owner but there is a field that was not filled out and the
     * returned computer part will have null fields and was not created.
     */
    @Test
    public void testCreateComputerPartFailure() throws Exception {
        LinkedHashMap contents = generateComputerDetailFailure(null,false);
        assertNotNull(contents.get(PLACE_PURCHASED_AT_KEY));
        assertEquals(FIELD_CANNOT_BE_NULL, contents.get(PLACE_PURCHASED_AT_KEY));
    }

    /*
     * testing a successful computer part upgrade.
     */
    @Test
    public void upgradeComputerPartSuccess() throws Exception {
        ComputerPart response = (ComputerPart) updateComputerPart(false, true, TEST_COMPUTER_PART_PLACE_PURCHASED_AT_TWO);
        assertNotNull(response);
        assertNotEquals(TEST_COMPUTER_PART_PLACE_PURCHASED_AT, response.getPlacePurchasedAt());
        assertEquals(TEST_COMPUTER_PART_PLACE_PURCHASED_AT_TWO, response.getPlacePurchasedAt());
    }

    /*
     * testing a computer part upgrade when the user logged in is not the owner so the computer part cannot be updated.
     */
    @Test
    public void upgradeComputerPartNotAsOwner() throws Exception {
        LinkedHashMap responseContent = (LinkedHashMap) updateComputerPart(true, false, TEST_COMPUTER_PART_PLACE_PURCHASED_AT_TWO);
        assertNotNull(responseContent);
        assertNotNull(responseContent.get(MESSAGE_KEY));
        assertEquals(COMPUTER_BUILD_CANNOT_BE_MODIFIED, responseContent.get(MESSAGE_KEY));
    }

    /*
     * testing a computer part upgrade when the user does not enter in a required field so there is an error message displayed.
     */
    @Test
    public void upgradeComputerPartFailure() throws Exception {
        LinkedHashMap responseContent = (LinkedHashMap) updateComputerPart(false, false, null);
        assertNotNull(responseContent);
        assertNotNull(responseContent.get(PLACE_PURCHASED_AT_KEY));
        assertEquals(FIELD_CANNOT_BE_NULL, responseContent.get(PLACE_PURCHASED_AT_KEY));
    }

    /*
     * testing a successful computer part deletion.
     */
    @Test
    public void deleteComputerPartSuccess() throws Exception {
        LinkedHashMap contents = createDirectionRequest(false, HttpMethod.DELETE, HttpStatus.OK.value(),
                true, true);
        assertNotNull(contents.get(MESSAGE_KEY));
        assertEquals(contents.get(MESSAGE_KEY), COMPUTER_PART_DELETE_MESSAGE);
    }

    /*
     * testing a computer part deletion where the user logged in is not the owner so the deletion cannot be done.
     */
    @Test
    public void deleteComputerPartNotAsOwner() throws Exception {
        LinkedHashMap contents = createDirectionRequest(true, HttpMethod.DELETE, HttpStatus.BAD_REQUEST.value(),
                true, false);
        assertNotNull(contents.get(MESSAGE_KEY));
        assertEquals(contents.get(MESSAGE_KEY), COMPUTER_BUILD_CANNOT_BE_MODIFIED);
    }

    /*
     * testing a computer part deletion where the user logged in has entered in the incorrect identifier so the computer part
     * is not deleted.
     */
    @Test
    public void deleteComputerPartFailure() throws Exception {
        LinkedHashMap contents = createDirectionRequest(false, HttpMethod.DELETE, HttpStatus.BAD_REQUEST.value(),
                true, false);
        assertNotNull(contents.get(MESSAGE_KEY));
        assertEquals(contents.get(MESSAGE_KEY), COMPUTER_PART_CANNOT_BE_DELETED);
    }

    /*
     * testing when the user is logged in and attempts to retrieve a computer part by the correct identifier.
     */
    @Test
    public void getComputerPartLoggedInSuccess() throws Exception {
        LinkedHashMap contents = createDirectionRequest(false, HttpMethod.GET, HttpStatus.OK.value(),
                true, true);
        assertEquals(TEST_COMPUTER_PART_NAME, contents.get(NAME_KEY));
        assertEquals(TEST_COMPUTER_PART_PURCHASE_DATE, contents.get(PURCHASE_DATE_KEY));
        assertEquals(TEST_COMPUTER_PART_PLACE_PURCHASED_AT, contents.get(PLACE_PURCHASED_AT_KEY));
        assertEquals(TEST_COMPUTER_PART_OTHER_NOTES, contents.get(OTHER_NOTES_KEY));
        assertEquals(TEST_COMPUTER_PART_PRICE, Double.valueOf(contents.get(PRICE_KEY).toString()), 0);
        assertNotNull(contents.get(UNIQUE_IDENTIFIER_KEY));
        assertNotNull(contents.get(ID_FIELD));
    }

    /*
     * testing when the user is not logged in and attempts to retrieve a computer part by the correct identifier.
     */
    @Test
    public void getComputerPartNotLoggedInSuccess() throws Exception {
        LinkedHashMap contents = createDirectionRequest(false, HttpMethod.GET, HttpStatus.OK.value(),
                false, true);
        assertEquals(TEST_COMPUTER_PART_NAME, contents.get(NAME_KEY));
        assertEquals(TEST_COMPUTER_PART_PURCHASE_DATE, contents.get(PURCHASE_DATE_KEY));
        assertEquals(TEST_COMPUTER_PART_PLACE_PURCHASED_AT, contents.get(PLACE_PURCHASED_AT_KEY));
        assertEquals(TEST_COMPUTER_PART_OTHER_NOTES, contents.get(OTHER_NOTES_KEY));
        assertEquals(TEST_COMPUTER_PART_PRICE, Double.valueOf(contents.get(PRICE_KEY).toString()), 0);
        assertNotNull(contents.get(UNIQUE_IDENTIFIER_KEY));
        assertNotNull(contents.get(ID_FIELD));
    }

    /*
     * testing when the user is logged in but provides the incorrect identifier when attempting to retrieve
     * a computer part.
     */
    @Test
    public void getComputerPartLoggedInFailure() throws Exception {
        LinkedHashMap contents = createDirectionRequest(false, HttpMethod.GET, HttpStatus.BAD_REQUEST.value(),
                true, false);
        assertEquals(INVALID_COMPUTER_PART, contents.get(MESSAGE_KEY));
    }

    /*
     * testing when the user is not logged in and provides the incorrect identifier when attempting to retrieve
     * a computer part.
     */
    @Test
    public void getComputerPartNotLoggedInFailure() throws Exception {
        LinkedHashMap contents = createDirectionRequest(false, HttpMethod.GET, HttpStatus.BAD_REQUEST.value(),
                false, false);
        assertEquals(INVALID_COMPUTER_PART, contents.get(MESSAGE_KEY));
    }

    /*
     * helper method to create a patch request that is successful and returns a computer part.
     */
    private ComputerPart updateRequestSuccess(String url, HttpEntity<String> entity) throws Exception {
        URI uri = new URI(url);
        return restTemplate.patchForObject(uri, entity, ComputerPart.class);
    }

    /*
     * helper method to create a computer part and verify some of its fields.
     */
    private ComputerPart createComputerPart(String content, String createComputerBuildURL, int expectedStatusCode) throws Exception {
        Object response = createRequest(createComputerBuildURL, WebUtility.getEntityWithToken(content, token),
                expectedStatusCode, HttpMethod.POST, restTemplate);
        LinkedHashMap contents = (LinkedHashMap) response;
        Gson gson = new Gson();
        String json = gson.toJson(contents,LinkedHashMap.class);
        ObjectMapper objectMapper = new ObjectMapper();
        ComputerPart computerPart = objectMapper.readValue(json, ComputerPart.class);

        assertEquals(TEST_COMPUTER_PART_NAME, computerPart.getName());
        assertEquals(TEST_COMPUTER_PART_PURCHASE_DATE, convertDateToString(computerPart.getPurchaseDate()));
        assertEquals(TEST_COMPUTER_PART_PLACE_PURCHASED_AT, computerPart.getPlacePurchasedAt());
        assertEquals(TEST_COMPUTER_PART_OTHER_NOTES, computerPart.getOtherNotes());
        assertEquals(TEST_COMPUTER_PART_PRICE, computerPart.getPrice(), 0);
        assertNotNull(computerPart.getId());
        assertNotNull(computerPart.getUniqueIdentifier());
       return computerPart;
    }

    /*
     * helper method to persist/create a computer part object for testing.
     */
    private ComputerPart generateComputerPart() throws Exception {
        String content = ComputerPartUtility.getComputerPartAsJson(TEST_COMPUTER_PART_NAME, TEST_COMPUTER_PART_PURCHASE_DATE,
                TEST_COMPUTER_PART_PLACE_PURCHASED_AT, TEST_COMPUTER_PART_OTHER_NOTES, TEST_COMPUTER_PART_PRICE);
        String createComputerPartBuildURL = BASE_URL + COMPUTER_PART_API + computerBuildIdentifier;
        return createComputerPart(content, createComputerPartBuildURL, HttpStatus.CREATED.value());
    }

    /*
     * helper method to create a computer part with some form of error.
     * one example would be where the user is not the owner.
     * another example would be where the user is the owner but passes in invalid data.
     */
    private LinkedHashMap generateComputerDetailFailure(String placePurchasedAt, boolean logInSecondTime) throws Exception {
        String content = ComputerPartUtility.getComputerPartAsJson(TEST_COMPUTER_PART_NAME, TEST_COMPUTER_PART_PURCHASE_DATE,
                placePurchasedAt, TEST_COMPUTER_PART_OTHER_NOTES, TEST_COMPUTER_PART_PRICE);
        String createComputerPartBuildURL = BASE_URL + COMPUTER_PART_API + computerBuildIdentifier;
        if(logInSecondTime) {
            // log in as someone else..
            token = loginHelper(ANOTHER_USER_NAME_TO_CREATE_NEW_USER, USER_PASSWORD, restTemplate);
        }

        return createComputerDetailFailure(content, createComputerPartBuildURL, HttpStatus.BAD_REQUEST.value(),
                token, restTemplate);
    }

    /*
     * helper method to update a computer part for successful and failing cases.
     */
    private Object updateComputerPart(boolean logInSecondTime, boolean isSuccessfulUpdate, String placePurchasedAt) throws Exception {
        ComputerPart computerPart = generateComputerPart();

        if(logInSecondTime) {
            // log in as someone else..
            token = loginHelper(ANOTHER_USER_NAME_TO_CREATE_NEW_USER, USER_PASSWORD, restTemplate);
        }

        String computerPartIdentifier = computerPart.getUniqueIdentifier();
        String updateComputerPartURL = BASE_URL + COMPUTER_PART_API + computerBuildIdentifier + URL_SEPARATOR + computerPartIdentifier;
        // update the computer part that was created above.
        String content = ComputerPartUtility.getComputerPartAsJson(TEST_COMPUTER_PART_NAME, TEST_COMPUTER_PART_PURCHASE_DATE,
                placePurchasedAt, TEST_COMPUTER_PART_OTHER_NOTES, TEST_COMPUTER_PART_PRICE,
                computerBuildIdentifier, computerPart.getId(), computerPartIdentifier);

        return isSuccessfulUpdate ? updateRequestSuccess(updateComputerPartURL, WebUtility.getEntityWithToken(content, token)) :
            updateRequest(updateComputerPartURL, WebUtility.getEntityWithToken(content, token), restTemplate);
    }

    /*
     * helper method to get or delete a computer part.
     */
    private LinkedHashMap createDirectionRequest(boolean logInSecondTime, HttpMethod httpMethod, int expectedStatusCode,
                                                 boolean loggedIn, boolean isSuccess) throws Exception {
        ComputerPart computerPart = generateComputerPart();

        if(logInSecondTime) {
            token = loginHelper(ANOTHER_USER_NAME_TO_CREATE_NEW_USER, USER_PASSWORD, restTemplate);
        }
        String uniqueIdentifier = computerPart.getUniqueIdentifier();
        String url = isSuccess ? BASE_URL + COMPUTER_PART_API + computerBuildIdentifier + URL_SEPARATOR + uniqueIdentifier :
                BASE_URL + COMPUTER_PART_API + computerBuildIdentifier + URL_SEPARATOR + uniqueIdentifier + INVALID_IDENTIFIER_SUFFIX;

        Object response = loggedIn ? createRequest(url, WebUtility.getEntityWithToken(null, token), expectedStatusCode, httpMethod, restTemplate) :
                createRequest(url, WebUtility.getEntity(null), expectedStatusCode, httpMethod, restTemplate);
        assertNotNull(response);
        LinkedHashMap contents = (LinkedHashMap) response;
        assertNotNull(contents);
        return contents;
    }
}