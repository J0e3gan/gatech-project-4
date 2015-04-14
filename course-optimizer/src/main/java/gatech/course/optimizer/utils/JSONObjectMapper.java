package gatech.course.optimizer.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by 204069126 on 4/14/15.
 */
public class JSONObjectMapper
{
    private static final Logger LOGGER = Logger.getLogger(JSONObjectMapper.class.getName());

    public static String jsonify(Object object)
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to serialize Object to JSON:" + object, e);
            return null;
        }
    }

    public static <T> T objectify(String jsonString, Class<T> objectClass)
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(jsonString, objectClass);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to objectify JSON Object: " + jsonString, e);
            return null;
        }
    }
}
