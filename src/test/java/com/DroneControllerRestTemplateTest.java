package com;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.Book;
import com.model.Drone;
import com.repositories.DroneRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
@ActiveProfiles("test")
public class DroneControllerRestTemplateTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private DroneRepository mockRepository;

    @Before
    public void init() {
        Book book = new Book(1L, "Book Name", "Book Title", new BigDecimal("9.99"));
        Drone drone = new Drone(1,"serialNumber", Drone.model.Middleweight,500,100, Drone.state.IDLE);
        when(mockRepository.findById(1L)).thenReturn(Optional.of(drone));
    }

    @Test
    public void find_droneId_OK() throws JSONException {

       // String expected = "{id:1,name:\"Book Name\",author:\"Book Title\",price:9.99}";
        String expected = "{id:1,serial:\"serialNumber\",model:\"Middleweight\",weightLimit:500,batteryCapacity:100,state:\"IDLE\"}";

        ResponseEntity<String> response = restTemplate.getForEntity("/drone/1", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON_UTF8, response.getHeaders().getContentType());

        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(mockRepository, times(1)).findById(1L);

    }

    @Test
    public void find_alldrone_OK() throws Exception {

        List<Drone> drones = Arrays.asList(
                new Drone(1, "serialNumberA", Drone.model.Middleweight, 500,100,Drone.state.IDLE),
                new Drone(2, "serialNumberB", Drone.model.Middleweight, 500,100,Drone.state.IDLE));

        when(mockRepository.findAll()).thenReturn(drones);

        String expected = om.writeValueAsString(drones);

        ResponseEntity<String> response = restTemplate.getForEntity("/list", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(mockRepository, times(1)).findAll();
    }
/*
    @Test
    public void find_bookIdNotFound_404() throws Exception {

        String expected = "{status:404,error:\"Not Found\",message:\"Book id not found : 5\",path:\"/books/5\"}";

        ResponseEntity<String> response = restTemplate.getForEntity("/books/5", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

    }

    @Test
    public void save_book_OK() throws Exception {

        Book newBook = new Book(1L, "Book Name", "Book Title", new BigDecimal("2.99"));

        when(mockRepository.save(any(Book.class))).thenReturn(newBook);

        String expected = om.writeValueAsString(newBook);

        ResponseEntity<String> response = restTemplate.postForEntity("/books", newBook, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(mockRepository, times(1)).save(any(Book.class));

    }

    @Test
    public void update_book_OK() throws Exception {

        Book updateBook = new Book(1L, "Book Name", "Book Title", new BigDecimal("2.99"));
        when(mockRepository.save(any(Book.class))).thenReturn(updateBook);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(om.writeValueAsString(updateBook), headers);

        ResponseEntity<String> response = restTemplate.exchange("/books/1", HttpMethod.PUT, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(om.writeValueAsString(updateBook), response.getBody(), false);

        verify(mockRepository, times(1)).findById(1L);
        verify(mockRepository, times(1)).save(any(Book.class));

    }

    @Test
    public void patch_bookAuthor_OK() {

        when(mockRepository.save(any(Book.class))).thenReturn(new Book());
        String patchInJson = "{\"author\":\"ultraman\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(patchInJson, headers);

        ResponseEntity<String> response = restTemplate.exchange("/books/1", HttpMethod.PATCH, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(mockRepository, times(1)).findById(1L);
        verify(mockRepository, times(1)).save(any(Book.class));

    }

    @Test
    public void patch_bookPrice_405() throws JSONException {

        String expected = "{status:405,error:\"Method Not Allowed\",message:\"Field [price] update is not allow.\"}";

        String patchInJson = "{\"price\":\"99.99\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(patchInJson, headers);

        ResponseEntity<String> response = restTemplate.exchange("/books/1", HttpMethod.PATCH, entity, String.class);

        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
        JSONAssert.assertEquals(expected, response.getBody(), false);

        verify(mockRepository, times(1)).findById(1L);
        verify(mockRepository, times(0)).save(any(Book.class));
    }

    @Test
    public void delete_book_OK() {

        doNothing().when(mockRepository).deleteById(1L);

        HttpEntity<String> entity = new HttpEntity<>(null, new HttpHeaders());
        ResponseEntity<String> response = restTemplate.exchange("/books/1", HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(mockRepository, times(1)).deleteById(1L);
    }

    private static void printJSON(Object object) {
        String result;
        try {
            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
            System.out.println(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
*/
}
