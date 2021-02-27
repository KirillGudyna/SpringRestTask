package com.epam.esm.controller;

import com.epam.esm.util.ErrorCode;
import com.epam.esm.exception.ExceptionProvider;
import com.epam.esm.dto.TagDto;
import com.epam.esm.service.TagService;
import com.epam.esm.util.HateoasData;
import com.epam.esm.validation.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * Tag api controller.
 */
@RestController
@RequestMapping("tags")
public class TagController {
    private TagService service;
    private ExceptionProvider exceptionProvider;

    @Autowired
    public void setService(TagService service) {
        this.service = service;
    }

    @Autowired
    public void setExceptionProvider(ExceptionProvider exceptionProvider) {
        this.exceptionProvider = exceptionProvider;
    }

    /**
     * End point for findAllTags request.
     */
    @GetMapping
    @PermitAll
    public List<TagDto> findAll(@RequestParam(required = false) Integer limit, @RequestParam(required = false) Integer offset) {
        return service.findAll(limit, offset)
                .stream()
                .map(TagController::addLinks)
                .collect(Collectors.toList());
    }

    /**
     * End point for finding tag by id request.
     */
    @GetMapping("/{id}")
    @PermitAll
    public TagDto findById(@PathVariable long id) {
        TagDto tagDto = service.findById(id).orElseThrow(() -> exceptionProvider.giftEntityNotFoundException(ErrorCode.TAG_NOT_FOUND));
        return addLinks(tagDto);
    }

    /**
     * End point for updating tag request.
     */
    @PostMapping
    @PreAuthorize("hasAuthority('tag:write')")
    public TagDto create(@RequestBody TagDto tagDto) {
        if (!TagValidator.isTagCorrect(tagDto)) {
            throw exceptionProvider.wrongParameterFormatException(ErrorCode.TAG_WRONG_PARAMETERS);
        }
        return addLinks(service.add(tagDto));
    }

    /**
     * End point for deleting tag request.
     */
    @PreAuthorize("hasAuthority('tag:delete')")
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable int id) {
        return service.delete(id);
    }

    /**
     * Method adds HATEOAS link to TagDTO entity
     *
     * @param tag the tag
     * @return the tag dto
     */
    public static TagDto addLinks(TagDto tag) {
        return tag
                .add(linkTo(TagController.class).slash(tag.getId()).withSelfRel())
                .add(linkTo(TagController.class)
                        .withRel(HateoasData.POST)
                        .withName(HateoasData.ADD_TAG))
                .add(linkTo(TagController.class).slash(tag.getId())
                        .withRel(HateoasData.DELETE)
                        .withName(HateoasData.DELETE_TAG));
    }
}
