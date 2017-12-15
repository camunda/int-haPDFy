package org.camunda.internal.hapdfydocumentcreator;

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
import java.nio.file.Files;
import java.nio.file.Path;
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
    String templateFileName = "./src/test/resources/pdfTemplate/pdf-template-1.pdf";
    Map<String, String> inputParameters = new HashMap<>();
    inputParameters.put("CUSTOMER_NAME", "Pillemann und Söhne GmbH & Cö. KG");

    Path pdfPath = Paths.get(templateFileName);

    OutputStream out = new FileOutputStream(folder.getRoot() + "/customer-information-1.pdf");
    out.write(pdfService.createPdfFileFromTemplate(Files.readAllBytes(pdfPath), inputParameters));
    out.close();

    File file = new File(folder.getRoot() + "/customer-information-1.pdf");
    assertTrue(file.exists());
  }

}