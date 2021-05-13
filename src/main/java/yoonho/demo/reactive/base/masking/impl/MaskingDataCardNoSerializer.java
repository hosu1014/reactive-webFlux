package yoonho.demo.reactive.base.masking.impl;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class MaskingDataCardNoSerializer extends StdSerializer<String> {
    private static final long serialVersionUID = 1L;
    private static final String cardNoMaskPattern = "([0-9*]{4})([0-9*]{4})([0-9*]{4})([0-9*]*)";
    private static final String targetRegex = "[\\w\\W]"; 
    private static final String maskingString = "*";
    private static final String splitString = "-";

    public MaskingDataCardNoSerializer() {
        super(String.class);
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        // Masking data; for our example we are adding 'MASK'
    	Matcher cardNoMatcher = Pattern.compile(cardNoMaskPattern).matcher(value);
    	String cardNo = value;
    	if(cardNoMatcher.matches()) {
    		cardNo = cardNoMatcher.group(1)
    			.concat(splitString)
    			.concat(cardNoMatcher.group(2).replaceAll(targetRegex, maskingString))
    			.concat(splitString)
    			.concat(cardNoMatcher.group(3).replaceAll(targetRegex, maskingString))
    			.concat(splitString)
    			.concat(cardNoMatcher.group(4))
    			;
    	}

    	gen.writeString(cardNo);
    }

}
