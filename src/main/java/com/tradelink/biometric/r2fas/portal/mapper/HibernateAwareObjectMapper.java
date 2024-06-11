package com.tradelink.biometric.r2fas.portal.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

public class HibernateAwareObjectMapper extends ObjectMapper {

	private static final long serialVersionUID = 1L;

	public HibernateAwareObjectMapper() {
	   Hibernate5Module module = new Hibernate5Module();
	   // module.configure(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION, true);
	   module.disable(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION);
       registerModule(module);
       
    }

}
