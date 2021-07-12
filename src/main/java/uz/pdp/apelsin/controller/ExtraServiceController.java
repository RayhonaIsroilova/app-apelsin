package uz.pdp.apelsin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apelsin.service.ExtraService;

@RestController
@RequestMapping("/extra")
public class ExtraServiceController {
    @Autowired
    ExtraService extraService;

    @GetMapping("/expired_invoices")
    public HttpEntity<?> getExpiredInvoices() {
        return ResponseEntity.ok(extraService.getExpiredInvoices());
    }

    @GetMapping("/customers_without_orders")
    public HttpEntity<?> getCustomersWithoutOrders(@RequestParam String year) {
        return ResponseEntity.ok(extraService.getCustomerLastOrder(year));
    }

    @GetMapping("/orders_without_details")
    public HttpEntity<?> getOrdersWithoutDetails(@RequestParam String year) {
        return ResponseEntity.ok(extraService.getOrdersWithoutDetails(year));
    }

    @GetMapping("/high_demand_products")
    public HttpEntity<?> getHighDemandProducts() {
        return ResponseEntity.ok(extraService.getHighDemandProducts());
    }

    @GetMapping("/bulk_products")
    public HttpEntity<?> getBulkProducts() {
        return ResponseEntity.ok(extraService.getBulkProducts());
    }

    @GetMapping("/number_of_products_in_year")
    public HttpEntity<?> getNumberOfProductsInYear(@RequestParam Integer year) {
        return ResponseEntity.ok(extraService.getNumberOfProductsInYear(year));
    }

    @GetMapping("/customers_last_orders")
    public HttpEntity<?> getCustomerLastOrder() {
        return ResponseEntity.ok(extraService.getCustomerLastOrder());
    }

    @GetMapping("/overpaid_invoices")
    public HttpEntity<?> getOverpaidInvoices() {
        return ResponseEntity.ok(extraService.getOverpaidInvoices());
    }
}
