package app.controller;

import app.entity.Call;
import app.service.CallService;
import app.service.PhoneNumberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CallController {
    private CallService callService;
    private PhoneNumberService phoneNumberService;

    public CallController(CallService callService, PhoneNumberService phoneNumberService) {
        this.callService = callService;
        this.phoneNumberService = phoneNumberService;
    }

    @PostMapping("/call/")
    public ResponseEntity<Call> createCall(@RequestBody Call call) {
        if (callService.exists(call)) {
            return new ResponseEntity<>(call, HttpStatus.CONFLICT);
        }
        if (!phoneNumberService.exists(call.getCallerPhoneNumber())) {
            return new ResponseEntity<>(call, HttpStatus.NOT_FOUND);
        }
        if (!phoneNumberService.exists(call.getRecipientPhoneNumber())) {
            return new ResponseEntity<>(call, HttpStatus.NOT_FOUND);
        }
        callService.save(call);
        return new ResponseEntity<>(call, HttpStatus.CREATED);
    }
}