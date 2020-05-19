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

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIntegrationTest extends AbstractBaseTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    final String API_BASE_PATH = "/api/v1/shape";

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    final Square square = makeASquare("square", "newName", 0, 0, 200);

    @Before
    public void clearDown() {
        // Clear out all data before every test
        clearOutAllShapes();
    }

    @Test
    public void testFindSquareShapeSuccess() {

        makeGetShareSquareApiRequest(square);

        ResponseEntity<Square[]> responseEntities = makeGetShareSquareApiRequest();

        assertEquals(HttpStatus.OK, responseEntities.getStatusCode());
        assertEquals(1, responseEntities.getBody().length);

        assertSquare(square, (Square) responseEntities.getBody()[0]);
    }

    @Test
    public void testShouldNotFindAnySquareShape() {
        ResponseEntity<Square[]> responseEntities = makeGetShareSquareApiRequest();

        assertEquals(HttpStatus.OK, responseEntities.getStatusCode());
        assertEquals(0, responseEntities.getBody().length);
    }

    @Test
    public void testCreateSquareShapeSuccess() {
        ResponseEntity<Square> createSquareResponse = makeGetShareSquareApiRequest(square);
        Square actualResult = createSquareResponse.getBody();

        assertEquals(HttpStatus.OK, createSquareResponse.getStatusCode());
        assertSquare(square, actualResult);
    }

    @Test
    public void testCreateSquareShapeFailGivenThatNameIsNotUnique() {
        final Square square1 = makeASquare("square", "name", 0, 0, 5);
        final Square square2 = makeASquare("square", "name", 10, 10, 5);

        makeGetShareSquareApiRequest(square1);

        ResponseEntity<String> createSquareResponse2 = makeCreateSquareShapeApiRequestResponseAsString(square2);
        assertEquals(HttpStatus.BAD_REQUEST, createSquareResponse2.getStatusCode());
        assertErrorDescription(createSquareResponse2.getBody(), "org.hibernate.exception.ConstraintViolationException: could not execute statement\"}");
    }

    @Test
    public void testCreateSquareShapeFailGivenThatShapeOverlaps() {
        final Square square1 = makeASquare("square", "name5", 150, 150, 10);
        final Square square2 = makeASquare("square", "name6", 155, 155, 5);

        makeGetShareSquareApiRequest(square1);

        ResponseEntity<String> createSquareResponse2 = makeCreateSquareShapeApiRequestResponseAsString(square2);

        assertEquals(HttpStatus.BAD_REQUEST, createSquareResponse2.getStatusCode());

        assertErrorDescription(createSquareResponse2.getBody(), "Shape overlapped with existing shape");
    }

    private void assertErrorDescription(String body, String str) {
        assertTrue(body.contains(str));
    }

    private Square makeASquare(String square, String name, int bottomLeftPointX, int bottomLeftPointY, int width) {
        return new Square(square, name, bottomLeftPointX, bottomLeftPointY, width);
    }

    private ResponseEntity<Square[]> makeGetShareSquareApiRequest() {
        final String getUrl = String.format("%s/square", API_BASE_PATH);
        return restTemplate.getForEntity(getRootUrl() + getUrl, Square[].class);
    }

    private ResponseEntity<Square> makeGetShareSquareApiRequest(Square square) {
        final String postUrl = String.format("%s/square/create", API_BASE_PATH);
        return restTemplate.postForEntity(getRootUrl() + postUrl, square, Square.class);
    }

    private ResponseEntity<String> makeCreateSquareShapeApiRequestResponseAsString(Square square) {
        final String postUrl = String.format("%s/square/create", API_BASE_PATH);
        return restTemplate.postForEntity(getRootUrl() + postUrl, square, String.class);
    }

    private void clearOutAllShapes() {
        final String deleteUrl = String.format("%s/square", API_BASE_PATH);
        restTemplate.delete(deleteUrl);
    }

}
