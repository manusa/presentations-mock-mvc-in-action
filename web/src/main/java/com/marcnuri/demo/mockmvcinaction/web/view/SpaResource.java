package com.marcnuri.demo.mockmvcinaction.web.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Resource to handle requests for typical SPA resources hosted within the web application.
 * Created by marc on 2019-02-01.
 */
@Controller
@RequestMapping("/")
public class SpaResource {

    /**
     * Typical mapping for SPAs where any url not containing points and with an <code>Accept</code>
     * header "text/*" will forward the request to the SPAs index.html
     *
     * @see com.marcnuri.demo.mockmvcinaction.web.Configuration
     */
    @RequestMapping(value = {
            "",
            "/**/{path:[^\\.]*}"
    },
            produces = "text/*")
    public String forwardToIndex(@PathVariable(name = "path", required = false) String path) {
        return "forward:/index.html";
    }
}
