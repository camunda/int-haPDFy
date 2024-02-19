package org.camunda.internal.hapdfy;

import static org.assertj.core.api.BDDAssertions.then;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = {"management.server.port=0"})
//@TestPropertySource(properties = {"management.server.port=0"})
public class ControllerTest {

  @LocalServerPort
  private int port;

  @LocalManagementPort
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

    ResponseEntity<byte[]> entity = this.testRestTemplate
    		.withBasicAuth("test", "test")
    		.postForEntity(
			  new URI("http://localhost:" + this.port + "/pdf/generateFromTemplate"),
			  pdfPayload,
			  byte[].class
			 );

    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  @SuppressWarnings("rawtypes")
  public void shouldReturn200WhenSendingRequestToStatusEndpoint() throws Exception {
    ResponseEntity<Map> entity = this.testRestTemplate
    	.withBasicAuth("test", "test")
		.getForEntity("http://localhost:" + this.port + "/pdf/status", Map.class);

    Map body = entity.getBody();
    then(body.containsKey("status")).isTrue();
    then(body.get("status")).isEqualTo("online");
    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void shouldReturn200WhenSendingRequestToManagementHealthEndpoint() throws Exception {
    @SuppressWarnings("rawtypes")
    ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
        "http://localhost:" + this.mgtPort + "/actuator/health", Map.class);
    then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

}