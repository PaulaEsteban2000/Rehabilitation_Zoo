package rehabilitationzoo.xml.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.sql.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import rehabilitationzoo.db.pojos.LightType;

public class SQLLightTypeAdapter extends XmlAdapter<String, LightType> {
	
	
	@Override
	public String marshal(LightType sqlLight) throws Exception {
		return sqlLight.toString();
	}

	@Override
	public LightType unmarshal(String string) throws Exception {
		return LightType.valueOf(string);
	}
	
	

}

