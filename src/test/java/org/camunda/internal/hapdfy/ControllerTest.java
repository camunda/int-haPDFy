package org.camunda.internal.hapdfy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class ControllerTest {

  @LocalServerPort
  private int port;

  @Value("${local.management.port}")
  private int mgtPort;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  public void shouldReturn200WhenSendingPdfRequestToController() throws Exception {
    String templateFileName = "/pdfTemplate/pdf-template-1.pdf";
    Map<String, String> inputParameters = new HashMap<>();
    inputParameters.put("CUSTOMER_NAME", "Pillemann und Söhne GmbH & Cö. KG");
    URL templateFileUrl = this.getClass().getResource(templateFileName);

    byte[] fileBytes = Files.readAllBytes(Paths.get(templateFileUrl.toURI()));

    PdfPayload pdfPayload = new PdfPayload();
    pdfPayload.setInputParameter(inputParameters);
    pdfPayload.setTemplate(fileBytes);

    @SuppressWarnings("rawtypes")
    ResponseEntity<byte[]> entity = this.testRestTemplate.postForEntity(
        new URI("http://localhost:" + this.port + "/pdf/generateFromTemplate"),
        pdfPayload,
        byte[].class);

    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void shouldReturn200WhenSendingRequestToStatusEndpoint() throws Exception {
    @SuppressWarnings("rawtypes")
    ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
        "http://localhost:" + this.port + "/pdf/status", Map.class);

    Map body = entity.getBody();
    then(body.containsKey("status")).isTrue();
    then(body.get("status")).isEqualTo("online");
    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void shouldReturn200WhenSendingRequestToManagementHealthEndpoint() throws Exception {
    @SuppressWarnings("rawtypes")
    ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
        "http://localhost:" + this.mgtPort + "/health", Map.class);
    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

}