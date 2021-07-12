package uz.pdp.apelsin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apelsin.entity.Payment;
import uz.pdp.apelsin.entity.template.ApiResponse;
import uz.pdp.apelsin.payload.PaymentDTO;
import uz.pdp.apelsin.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @GetMapping()
    public HttpEntity<?> getById(@RequestParam Integer payment_id){
        ApiResponse response = paymentService.getId(payment_id);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }
    @GetMapping("/list")
    public HttpEntity<?> getAll(){
        List<Payment> response = paymentService.getAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public HttpEntity<?> add(@RequestBody PaymentDTO paymentDTO){
        ApiResponse response = paymentService.addPayment(paymentDTO);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

}
