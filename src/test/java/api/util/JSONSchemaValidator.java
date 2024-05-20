package api.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONSchemaValidator {

    private static final JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void validateJSONSchema (String jsonResponse, String schemaFilePath) throws IOException, ProcessingException {

        //Read JSON Schema file
        String schemaString = new String(Files.readAllBytes(Paths.get(schemaFilePath)));

        //Convert JSON Schema and response into JSONNode
        JsonNode schemaNode = mapper.readTree(schemaString);
        JsonNode responseNode = mapper.readTree(jsonResponse);

        //Create JSON Schema
        JsonSchema schema = factory.getJsonSchema(schemaNode);

        //Validate response against JSON Schema
        ProcessingReport report = schema.validate(responseNode);

        if (!report.isSuccess()) {
            throw new AssertionError("Error in JSON Schema validation. Errors: " + report);
        } else{
            System.out.println("Schema validation is OK");
        }
    }
}
