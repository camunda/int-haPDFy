# haPDFy - the happy pdf file generator

URL: https://stage.hapdfy.int.camunda.com

## API Endpoints

### Status

A simple status check for the microservice

- GET `/status`    
    * returns `{"status":"online"}`


### Generate from Template

Generate a pdf file from a pdf template. The template file must contain text fileds.

- POST `/pdf/generateFromTemplate`

    * request body
      * a JSON object which contains the PDF template as byte array
      * a string map wich contains the text field identifier and their values
    * returns the pdf file as Byte Array



