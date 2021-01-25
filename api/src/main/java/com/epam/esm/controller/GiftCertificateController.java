package com.epam.esm.controller;

import com.epam.esm.controller.exception.GiftEntityNotFoundException;
import com.epam.esm.controller.exception.WrongParameterFormatException;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.validation.GiftCertificateValidator;
import com.epam.esm.validation.TagValidator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("certificates")
public class GiftCertificateController {
    private GiftCertificateService service;

    @Autowired
    public void setService(GiftCertificateService giftCertificateService) {
        this.service = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificate> findAll() {
        return this.service.findAll();
    }

    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public GiftCertificate findById(@PathVariable long id) {
        return this.service.findById(id).orElseThrow(() -> new GiftEntityNotFoundException("Certificate not found", 40401));
    }

    @PostMapping
    public GiftCertificate create(@RequestBody GiftCertificate certificate) {
        return this.service.add(certificate);
    }

    @PatchMapping("/{id:^[1-9]\\d{0,18}$}")
    public GiftCertificate update(@RequestBody GiftCertificate certificate, @PathVariable long id) {
        certificate.setId(id);
        return service.update(certificate).orElseThrow(() -> new GiftEntityNotFoundException("Certificate not found", 40401));
    }

    @DeleteMapping("/{id:^[1-9]\\d{0,18}$}")
    public void delete(@PathVariable long id) {
        this.service.delete(id);
    }

    @GetMapping("/find/tag/{tagName}")
    public List<GiftCertificate> findByTagName(@PathVariable String tagName, @RequestParam(value = "sort",required = false) String sortType, @RequestParam(value = "direction",required = false) String direction) {
        if (!TagValidator.isNameCorrect(tagName)) {
            throw new WrongParameterFormatException("Wrong tag name format", 40413);
        } else {
            return this.service.findByTagName(tagName, sortType, direction);
        }
    }

    @GetMapping("/name/{name}")
    public List<GiftCertificate> findByName(@PathVariable String name, @RequestParam(value = "sort",required = false) String sortType, @RequestParam(value = "direction",required = false) String direction) {
        if (!TagValidator.isNameCorrect(name)) {
            throw new WrongParameterFormatException("Wrong certificate name format", 40413);
        } else {
            return this.service.findByName(name, sortType, direction);
        }
    }

    @GetMapping("/description/{description}")
    public List<GiftCertificate> findByDescription(@PathVariable String description, @RequestParam(value = "sort",required = false) String sortType, @RequestParam(value = "direction",required = false) String direction) {
        if (!GiftCertificateValidator.isDescriptionCorrect(description)) {
            throw new WrongParameterFormatException("Wrong certificate name format", 40414);
        } else {
            return this.service.findByDescription(description, sortType, direction);
        }
    }
}
