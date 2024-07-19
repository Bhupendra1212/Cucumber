package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	@Before("@DeletePlace")
	public void beforeTestScenario() throws IOException {
		stepDefination m = new stepDefination();
		if (stepDefination.place_id == null) {
			m.add_place_Payload_with("deepa", "Marathi", "Nagpur");
			m.user_calls_with_http_request("AddPlaceAPI", "POST");
			m.verify_Place_Id_created_maps_to_using("deepa", "getPlaceAPI");
		}

	}

}
