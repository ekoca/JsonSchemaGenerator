package com.emre.json.builder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.emre.json.domain.JsonOperation;
import com.emre.json.domain.Person;
import com.emre.json.domain.StatusResponse;
import com.emre.utils.StringUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsonschema.JsonSchema;

/**
 * Json schema generator from POJOs used in web services.
 * 
 * @author Emre Koca
 */
@SuppressWarnings("deprecation")
public class JsonSchemaGenerator {

	/** add classes for the schema generator here */
	// static Class<?>[] clazz =
	static Map<String, Class<?>[]> classes = getFoldedrsAndClasses();

	// { StatusResponse.class, Cart.class,
	// LeadAllocationResult.class, PromoResponse.class,
	// UserValidationResponse.class, FS2014JanConditionJson.class };

	public static void main(String[] args) throws JsonGenerationException,
			IOException {

		ObjectMapper mapper = new ObjectMapper();
		// For Jackson lib version < 1.9 // import org.codehaus.jackson.map.SerializationConfig.Feature;
		// mapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
		// For Jackson lib version > 1.9
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		//or mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		for (Map.Entry<String, Class<?>[]> schema : classes.entrySet()) {
			File folder = new File(schema.getKey());
			folder.mkdirs();
			for (Class<?> c : schema.getValue()) {
				writeSchema(mapper, folder, c);
			}
		}
	}

	protected static void writeSchema(ObjectMapper mapper, File folder,
			Class<?> c) throws IOException {
		File schemaFile = new File(folder, c.getSimpleName() + ".json");
		System.out.println("-------------------------- " + schemaFile.getName()
				+ " --------------------------");

		String schema = createJsonSchema(c, mapper);
		System.out.println(schema);
		StringUtil.write(schemaFile, schema);
	}

	protected static String createJsonSchema(Class<?> clazz, ObjectMapper mapper) {

		try {
			/* The default configuration of an ObjectMapper instance is to only access properties 
			 * that are public fields or have public getters/setters. 
			 * An alternative to changing the class definition to make a field public or to provide a public getter/setter is 
			 * to specify (to the underlying VisibilityChecker) a different property visibility rule. 
			 */
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			JsonSchema jsonSchema = mapper.generateJsonSchema(clazz);
			// For Jackson lib version < 1.9
			//return mapper.defaultPrettyPrintingWriter().writeValueAsString(jsonSchema);
			// For Jackson lib version > 1.9
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			//or mapper.configure(SerializationFeature.INDENT_OUTPUT,true);
			return mapper.writeValueAsString(
					jsonSchema);
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return "";
		}
	}

	static Map<String, Class<?>[]> getFoldedrsAndClasses() {

		Map<String, Class<?>[]> folders = new HashMap<String, Class<?>[]>();

		addFolder(folders, "schema-responses", StatusResponse.class);
		addFolder(folders, "schema-jsons", JsonOperation.class, Person.class);

		return folders;
	}

	static void addFolder(Map<String, Class<?>[]> folders, String folder,
			Class<?>... classes) {
		folders.put(folder, classes);
	}
}