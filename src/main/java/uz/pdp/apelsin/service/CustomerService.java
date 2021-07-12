package uz.pdp.apelsin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.apelsin.entity.Customer;
import uz.pdp.apelsin.entity.template.ApiResponse;
import uz.pdp.apelsin.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    public ApiResponse getId(Integer id) {
        Optional<Customer> byId = customerRepository.findById(id);
        return byId.map(customer -> new ApiResponse("Take it", true, customer)).orElseGet(() -> new ApiResponse("THis id not found", false));
    }

    public ApiResponse add(Customer customer) {
        boolean b = customerRepository.existsByPhoneNumber(customer.getPhoneNumber());
        if (b)
            return new ApiResponse("This phone number already exist", false);
        customerRepository.save(new Customer(customer.getName(), customer.getCountry(),
                customer.getText(), customer.getPhoneNumber()));
        return new ApiResponse("Saved success", true);
    }

    public ApiResponse edit(Customer customer, Integer id) {
        Optional<Customer> byId = customerRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found", false);
        Customer customer1 = byId.get();
        boolean b = customerRepository.existsByPhoneNumber(customer1.getPhoneNumber());
        if (b)
            return new ApiResponse("This phone number already exist from another customer", false);
        customer1.setName(customer.getName());
        customer1.setCountry(customer.getCountry());
        customer1.setText(customer.getText());
        customer1.setPhoneNumber(customer.getPhoneNumber());
        customerRepository.save(customer1);
        return new ApiResponse("Edited successfully", true, customer1);
    }

    public ApiResponse delete(Integer id) {
        Optional<Customer> byId = customerRepository.findById(id);
        if (!byId.isPresent())
            return new ApiResponse("This id not found", false);
        customerRepository.deleteById(id);
        return new ApiResponse("Delete successfully", true);
    }
}
