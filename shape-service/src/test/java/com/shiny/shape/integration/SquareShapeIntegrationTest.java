package com.shiny.shape.integration;

import com.shiny.shape.AbstractBaseTest;
import com.shiny.shape.Application;
import com.shiny.shape.user.dto.Square;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static java.util.Objects.requireNonNull;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SquareShapeIntegrationTest extends AbstractBaseTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private final String API_BASE_PATH = "/api/v1/shape";
    private final Square SQUARE = makeASquare("newName", 0, 0, 200);

    @Before
    public void clearDown() {
        // Clear out all data before every test
        makeApiCallToClearOutAllShapes();
    }

    @Test
    public void testFindSquareShapeSuccess() {

        makeApiCallToGetShareSquare(SQUARE);

        ResponseEntity<Square[]> responseEntities = makeApiCallToGetShareSquare();

        assertEquals(HttpStatus.OK, responseEntities.getStatusCode());
        assertEquals(1, requireNonNull(responseEntities.getBody()).length);

        assertSquare(SQUARE, (Square) responseEntities.getBody()[0]);
    }

    @Test
    public void testShouldNotFindAnySquareShape() {
        ResponseEntity<Square[]> responseEntities = makeApiCallToGetShareSquare();

        assertEquals(HttpStatus.OK, responseEntities.getStatusCode());
        assertEquals(0, requireNonNull(responseEntities.getBody()).length);
    }

    @Test
    public void testCreateSquareShapeSuccess() {
        ResponseEntity<Square> createSquareResponse = makeApiCallToGetShareSquare(SQUARE);
        Square actualResult = createSquareResponse.getBody();

        assertNotNull(actualResult);
        assertEquals(HttpStatus.OK, createSquareResponse.getStatusCode());
        assertSquare(SQUARE, actualResult);
    }

    @Test
    public void testCreateSquareShapeFailGivenThatNameIsNotUnique() {
        final Square square1 = makeASquare("name", 0, 0, 5);
        final Square square2 = makeASquare("name", 10, 10, 5);

        makeApiCallToGetShareSquare(square1);

        ResponseEntity<String> createSquareResponse2 = makeApiCallToCreateSquareShapeResponseAsString(square2);
        assertEquals(HttpStatus.BAD_REQUEST, createSquareResponse2.getStatusCode());
        assertErrorDescription(requireNonNull(createSquareResponse2.getBody()), "org.hibernate.exception.ConstraintViolationException: could not execute statement\"}");
    }

    @Test
    public void testCreateSquareShapeFailGivenThatShapeOverlaps() {
        final Square square1 = makeASquare("name5", 150, 150, 10);
        final Square square2 = makeASquare("name6", 155, 155, 5);

        makeApiCallToGetShareSquare(square1);

        ResponseEntity<String> createSquareResponse2 = makeApiCallToCreateSquareShapeResponseAsString(square2);

        assertEquals(HttpStatus.BAD_REQUEST, createSquareResponse2.getStatusCode());

        assertErrorDescription(requireNonNull(createSquareResponse2.getBody()), "Shape overlapped with existing shape");
    }

    private void assertErrorDescription(String body, String str) {
        assertTrue(body.contains(str));
    }

    private Square makeASquare(String name, int bottomLeftPointX, int bottomLeftPointY, int width) {
        return new Square("square", name, bottomLeftPointX, bottomLeftPointY, width);
    }

    private ResponseEntity<Square[]> makeApiCallToGetShareSquare() {
        final String getUrl = String.format("%s/square", API_BASE_PATH);
        return restTemplate.getForEntity(getRootUrl() + getUrl, Square[].class);
    }

    private ResponseEntity<Square> makeApiCallToGetShareSquare(Square square) {
        final String postUrl = String.format("%s/square/create", API_BASE_PATH);
        return restTemplate.postForEntity(getRootUrl() + postUrl, square, Square.class);
    }

    private ResponseEntity<String> makeApiCallToCreateSquareShapeResponseAsString(Square square) {
        final String postUrl = String.format("%s/square/create", API_BASE_PATH);
        return restTemplate.postForEntity(getRootUrl() + postUrl, square, String.class);
    }

    private void makeApiCallToClearOutAllShapes() {
        final String deleteUrl = String.format("%s/square", API_BASE_PATH);
        restTemplate.delete(deleteUrl);
    }

    private String getRootUrl() {
        return "http://localhost:" + port;
    }
}
