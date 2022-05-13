package com.spring.agendalive.controller;

import com.spring.agendalive.document.LiveDocument;
import com.spring.agendalive.service.LiveService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@OpenAPIDefinition(info = @Info(title = "Live event  API", version = "1.0", description = "REST API responsible to allow CRUD live event."))
public class LiveController {

    @Autowired
    LiveService liveService;

    @GetMapping("/lives")
    @Operation(summary = "Return live event  List")
    public ResponseEntity<Page<LiveDocument>> getAllLives(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                          @RequestParam(required = false) String flag){
        Page<LiveDocument> livePage = liveService.findAll(pageable, flag);
        if(livePage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<Page<LiveDocument>>(livePage, HttpStatus.OK);
        }
    }

    @GetMapping("/lives/{id}")
    @Operation(summary = "Return live event by ID")
    public ResponseEntity<LiveDocument> getOneLive(@PathVariable(value="id") String id){
        Optional<LiveDocument> liveO = liveService.findById(id);
        if(!liveO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<LiveDocument>(liveO.get(), HttpStatus.OK);
        }
    }

    @PostMapping("/lives")
    @Operation(summary = "Add a live event")
    public ResponseEntity<LiveDocument> saveLive(@RequestBody @Valid LiveDocument live) {
        live.setRegistrationDate(LocalDateTime.now());
        return new ResponseEntity<LiveDocument>(liveService.save(live), HttpStatus.CREATED);
    }

    @DeleteMapping("/lives/{id}")
    @Operation(summary = "Delete e live event by ID")
    public ResponseEntity<?> deleteLive(@PathVariable(value="id") String id) {
        Optional<LiveDocument> liveO = liveService.findById(id);
        if(!liveO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            liveService.delete(liveO.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/lives/{id}")
    @Operation(summary = "Update a live event by ID")
    public ResponseEntity<LiveDocument> updateLive(@PathVariable(value="id") String id,
                                                      @RequestBody @Valid LiveDocument liveDocument) {
        Optional<LiveDocument> liveO = liveService.findById(id);
        if(!liveO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            liveDocument.setId(liveO.get().getId());
            return new ResponseEntity<LiveDocument>(liveService.save(liveDocument), HttpStatus.OK);
        }
    }
}
