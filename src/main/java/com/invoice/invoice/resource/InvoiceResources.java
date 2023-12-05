package com.invoice.invoice.resource;import com.invoice.invoice.domain.Invoice;import com.invoice.invoice.dto.InvoiceToListDTO;import com.invoice.invoice.dto.InvoiceToCreateDTO;import com.invoice.invoice.service.InvoiceService;import jakarta.transaction.Transactional;import jakarta.validation.Valid;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.http.HttpStatus;import org.springframework.http.ResponseEntity;import org.springframework.web.bind.annotation.*;import org.springframework.web.util.UriComponentsBuilder;import java.net.URI;import java.util.List;import java.util.Optional;import java.util.UUID;@RestController@RequestMapping("/v1/invoices")@Transactionalpublic class InvoiceResources {    @Autowired    private InvoiceService service;    @PostMapping()    public ResponseEntity<Invoice> createInvoice(@RequestBody @Valid InvoiceToCreateDTO invoiceToCreateDTO, UriComponentsBuilder uriComponentsBuilder) {        Invoice invoiceSaved = service.create(invoiceToCreateDTO);        URI address = uriComponentsBuilder.path("/invoices/{id}").buildAndExpand(invoiceSaved.getId()).toUri();        return ResponseEntity.created(address).body(invoiceSaved);    }    @GetMapping()    public ResponseEntity<List<InvoiceToListDTO>> allInvoice(@RequestParam(required = false) String invoiceNumber) {        return new ResponseEntity<>(service.getAll(invoiceNumber), HttpStatus.OK);    }    @DeleteMapping("/{id}")    public ResponseEntity<Void> deleteInvoice(@PathVariable UUID id) {        service.deleteById(id);        return ResponseEntity.noContent().build();    }    @GetMapping("/{id}")    public ResponseEntity<Invoice> getById(@PathVariable UUID id) {        Optional<Invoice> invoice = service.findById(id);        return invoice.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());    }    @PutMapping()    public ResponseEntity<Invoice> update(            @Valid @RequestBody Invoice invoiceToUpdate) {        if (invoiceToUpdate != null) {            Invoice updatedInvoice = service.update(invoiceToUpdate);            return ResponseEntity.ok(updatedInvoice);        } else {            return ResponseEntity.notFound().build();        }    }}