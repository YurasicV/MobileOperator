package app.controller;

import app.entity.Call;
import app.service.CallService;
import app.service.PhoneNumberService;
import app.validation.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<Call> createCall(@Valid @RequestBody Call call) throws CustomException {
        if (callService.exists(call)) {
            throw new CustomException("Such call already exists", HttpStatus.CONFLICT);
        }
        if (!phoneNumberService.exists(call.getCallerPhoneNumber())) {
            throw new CustomException(String.format("Caller phone number %s doesn't exist",
                    call.getCallerPhoneNumber()), HttpStatus.NOT_FOUND);
        }
        if (!phoneNumberService.exists(call.getRecipientPhoneNumber())) {
            throw new CustomException(String.format("Recipient phone number %s doesn't exist",
                    call.getRecipientPhoneNumber()), HttpStatus.NOT_FOUND);
        }
        callService.save(call);
        return new ResponseEntity<>(call, HttpStatus.CREATED);
    }
}