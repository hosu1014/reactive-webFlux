package yoonho.demo.reactive.base.masking;

import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;

import yoonho.demo.reactive.base.masking.impl.MaskingDataAccountNoSerializer;
import yoonho.demo.reactive.base.masking.impl.MaskingDataCardNoSerializer;
import yoonho.demo.reactive.base.masking.impl.MaskingDeserializer;

public class MaskAnnotationIntrospector extends NopAnnotationIntrospector {
    private static final long serialVersionUID = 1L;

    @Override
    public Object findSerializer(Annotated annotated) {
        Masking annotation = annotated.getAnnotation(Masking.class);
        if (annotation != null) {
        	MaskingType type = annotated.getAnnotation(Masking.class).type();
        	
        	switch(type) {
        	case CARD_NO : 
        		return MaskingDataCardNoSerializer.class;
        	case ACCOUT_NO : 
        		return MaskingDataAccountNoSerializer.class;
        	default : 
        		return MaskingDataAccountNoSerializer.class;
        	}
        }

        return null;
    }

    @Override
    public Object findDeserializer(Annotated annotated) {
    	Masking annotation = annotated.getAnnotation(Masking.class);
        if (annotation != null) {
            return MaskingDeserializer.class;
        }

        return null;
    }

}
