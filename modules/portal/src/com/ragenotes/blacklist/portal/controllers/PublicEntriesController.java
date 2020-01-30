package com.ragenotes.blacklist.portal.controllers;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.service.SearchService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PublicEntriesController {

    public static final String NAME = "bl_PublicEntriesController";

    private static final Integer LIMIT = 100;

    @Inject
    private SearchService searchService;

    @RequestMapping(value = "/public",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String publicEntries(Model model) {
        List<BlackListEntry> allEntries = searchService.getAllEntries(null).stream()
                .sorted(Comparator.comparing(StandardEntity::getUpdateTs))
                .limit(LIMIT)
                .collect(Collectors.toList());

        model.addAttribute("entries", allEntries);

        return "public_all";
    }

    @RequestMapping(value = "/public/entry",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE)
    public String publicEntry(@RequestParam("number") String number, Model model) {
        BlackListEntry entry = searchService.getEntry(number, null);

        model.addAttribute("entry", entry);

        return "public_entry";
    }
}
