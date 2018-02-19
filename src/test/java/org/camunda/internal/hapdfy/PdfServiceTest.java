package org.camunda.internal.hapdfy;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author mschoe
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PdfServiceTest {

  @Autowired
  private PdfService pdfService;

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void shouldCreatePdfFileFromTemplate() throws Exception {

    String templateFileName = "/pdfTemplate/pdf-template-1.pdf";
    Map<String, String> inputParameters = new HashMap<>();
    inputParameters.put("CUSTOMER_NAME", "Pillemann und Söhne GmbH & Cö. KG");
    URL templateFileUrl = this.getClass().getResource(templateFileName);

    byte[] fileBytes = Files.readAllBytes(Paths.get(templateFileUrl.toURI()));
    byte[] getPdfFileFromTemplate = pdfService.createPdfFileFromTemplate(fileBytes, inputParameters);

    OutputStream out = new FileOutputStream(folder.getRoot() + "/customer-information-1.pdf");
    out.write(getPdfFileFromTemplate);
    out.close();

    File file = new File(folder.getRoot() + "/customer-information-1.pdf");
    assertTrue(file.exists());
  }

}