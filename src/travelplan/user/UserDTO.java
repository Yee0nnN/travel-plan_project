package travelplan.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDTO {
	private String USER_ID;    
	private String USER_PASSWORD;  
	private String USER_NAME;       
	private String USER_EMAIL;      
}
