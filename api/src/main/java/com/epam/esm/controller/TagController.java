//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.epam.esm.controller;

import com.epam.esm.controller.exception.GiftEntityNotFoundException;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"api/tags"})
public class TagController {
    private TagService service;

    @Autowired
    public void setService(TagService service) {
        this.service = service;
    }

    @GetMapping
    public List<Tag> findAll() {
        return this.service.findAll();
    }

    @GetMapping({"/{id}"})
    public Tag findById(@PathVariable long id) {
        return (Tag)this.service.findById(id).orElseThrow(() -> {
            return new GiftEntityNotFoundException("Tag not found", 40402);
        });
    }

    @PostMapping
    public Tag create(@RequestBody Tag tag) {
        return this.service.add(tag);
    }

    @DeleteMapping({"/{id}"})
    public boolean delete(@PathVariable int id) {
        return this.service.delete((long)id);
    }
}
