package monetmonitor;


/**
 * 
 * @author llenterak
 * Command line interface output 
 */
public class StatusShowerCLI implements StatusShower {
	public void showStatus(String status) {
		System.out.println(status);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
}
 