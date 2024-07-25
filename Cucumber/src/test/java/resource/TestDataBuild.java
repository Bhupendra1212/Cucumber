package resource;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Locations;


public class TestDataBuild {
	public  AddPlace AddPlacePayload(String name,String language,String address) {
		
		AddPlace place=new AddPlace();
		place.setAccuracy(50);
		place.setAddress(address);
		place.setLanguage(language);
		place.setName(name);
		place.setPhone_number("(+91) 983 893 33937");
		List<String> mylist = new ArrayList<String>();
		mylist.add("shoe park");
		mylist.add("shop");
		place.setTypes(mylist);

		Locations l = new Locations();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		place.setLocation(l);
		return place;

	}
	public String DeletePlacePayload(String place_id) {
		return "{\r\n"
		+ "    \"place_id\":\""+place_id+"\"\r\n"
		+ "}";

	}
}
