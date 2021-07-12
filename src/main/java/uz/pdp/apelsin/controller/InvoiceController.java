package uz.pdp.apelsin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.apelsin.entity.Invoice;
import uz.pdp.apelsin.entity.template.ApiResponse;
import uz.pdp.apelsin.service.InvoiceService;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @GetMapping
    public HttpEntity<?> getByProductId(@RequestParam Integer product_id) {
        ApiResponse response = invoiceService.getInvoice(product_id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    @GetMapping("/list")
    public HttpEntity<?> getAll() {
        List<Invoice> response = invoiceService.getAll();
        return ResponseEntity.ok(response);
    }
}
