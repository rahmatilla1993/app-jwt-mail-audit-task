package com.example.appjwtmailaudittask.controller;

import com.example.appjwtmailaudittask.entity.Turni_Ket;
import com.example.appjwtmailaudittask.payload.ApiResponse;
import com.example.appjwtmailaudittask.payload.TurniKetDto;
import com.example.appjwtmailaudittask.service.Turni_KetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turni_ket")
public class Turni_KetController {

    @Autowired
    Turni_KetService turni_ketService;

    @GetMapping
    public HttpEntity<?> getAllInfo() {
        List<Turni_Ket> allInfo = turni_ketService.getAllInfo();
        return ResponseEntity.ok(allInfo);
    }

    @PostMapping
    public HttpEntity<?> addInfo(@RequestBody TurniKetDto turniKetDto) {
        ApiResponse apiResponse = turni_ketService.addInfo(turniKetDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.NOT_FOUND).body(apiResponse);
    }
}
