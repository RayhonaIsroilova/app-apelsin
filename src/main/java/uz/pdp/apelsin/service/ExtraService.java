package uz.pdp.apelsin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.apelsin.entity.Customer;
import uz.pdp.apelsin.entity.Invoice;
import uz.pdp.apelsin.entity.Order;
import uz.pdp.apelsin.repository.CustomerRepository;
import uz.pdp.apelsin.repository.DetailRepository;
import uz.pdp.apelsin.repository.InvoiceRepository;
import uz.pdp.apelsin.repository.OrderRepository;

import java.util.List;

@Service
public class ExtraService {
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DetailRepository detailRepository;

    public List<Invoice> getExpiredInvoices(){
        return invoiceRepository.getExpiredInvoices();
    }

    public List<Customer> getCustomerLastOrder(String year){
        return customerRepository.getCustomersWithoutOrders(year);
    }

    public List<Order> getOrdersWithoutDetails(String year){
        return orderRepository.getOrdersWithoutDetails(year);
    }

    public List<?> getHighDemandProducts(){
        return detailRepository.getHighDemandProducts();
    }

    public List<?> getBulkProducts(){
        return detailRepository.getBulkProducts();
    }

    public List<?> getNumberOfProductsInYear(Integer year){
        return customerRepository.getNumberOfProductsInYear(year);
    }

    public List<?> getCustomerLastOrder(){
        return orderRepository.getCustomerLastOrder();
    }

    public List<?> getOverpaidInvoices(){
        return invoiceRepository.getOverpaidInvoices();
    }
}
