package yoonho.demo.reactive.base.masking;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class MaskingDataAccountNoSerializer extends StdSerializer<String> {
    private static final long serialVersionUID = 1L;
    private static final String maskPattern = "(.*)([\\w]{4}$)";
    private static final String targetRegex = "[\\w\\W]"; 
    private static final String maskingString = "*";
    
    public MaskingDataAccountNoSerializer() {
        super(String.class);
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    	Matcher matcher = Pattern.compile(maskPattern).matcher(value);
    	String actnNo = "";
    	if(matcher.matches() && matcher.groupCount() == 2) {
    		actnNo = matcher.group(1).replaceAll(targetRegex, maskingString)
    			.concat(matcher.group(2))
    			;
    	}

    	gen.writeString(actnNo);
    }

}
