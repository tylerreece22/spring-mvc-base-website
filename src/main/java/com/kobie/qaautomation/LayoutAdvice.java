package com.kobie.qaautomation;

import com.kobie.qaautomation.model.Application;
import com.kobie.qaautomation.model.Menu;
import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

@ControllerAdvice
class LayoutAdvice {
    private final Mustache.Compiler compiler;

    private Application application;

//    @Autowired
//    public LayoutAdvice(Mustache.Compiler compiler) {
//        this.compiler = compiler;
//    }

    @Autowired
    public LayoutAdvice(Mustache.Compiler compiler, Application application) {
        this.compiler = compiler;
        this.application = application;
    }

    @ModelAttribute("layout")
    public Mustache.Lambda layout(Map<String, Object> model) {
        return new Layout(compiler);
    }

    @ModelAttribute("title")
    public Mustache.Lambda defaults(@ModelAttribute Layout layout) {
        return (frag, out) -> {
            layout.title = frag.execute();
        };
    }

    @ModelAttribute("menus")
    public Iterable<Menu> menus() {
        return application.getMenus();
    }
}

class Layout implements Mustache.Lambda {

    String body;

    String title = "Demo Application";

    private Application application;

    private Mustache.Compiler compiler;

    public Layout(Mustache.Compiler compiler) {
        this.compiler = compiler;
    }

    @Override
    public void execute(Template.Fragment frag, Writer out) throws IOException {
        body = frag.execute();
        compiler.compile("{{>layout}}").execute(frag.context(), out);
    }

}
