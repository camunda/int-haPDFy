package org.camunda.internal.hapdfydocumentcreator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mschoe
 */
public class PdfPayload {
  private byte[] template;
  private Map<String, String> inputParameter;

  public byte[] getTemplate() {
    return template;
  }

  public void setTemplate(byte[] template) {
    this.template = template;
  }

  public Map<String, String> getInputParameter() {
    return inputParameter;
  }

  public void setInputParameter(Map<String, String> inputParameter) {
    this.inputParameter = inputParameter;
  }
}
