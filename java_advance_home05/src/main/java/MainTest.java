import ua.lviv.lgs.dommain.Customer;
import ua.lviv.lgs.service.CustomerService;
import ua.lviv.lgs.service.impl.CustomerServiceImpl;

public class MainTest {
	
	public static void main(String[] args) {
		
		CustomerService customerService = new CustomerServiceImpl();
		
		customerService.create(new Customer("Volodymyr", "Ostapenko", "test4@mail.com", "test"));
		customerService.create(new Customer("Pavlo", "Pavlo", "test5@mail.com", "test"));
		customerService.create(new Customer("Volodymyr", "Volodymyr", "test6@mail.com", "test"));
	
	}
	
	

}
