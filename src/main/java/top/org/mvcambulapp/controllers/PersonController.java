package top.org.mvcambulapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.org.mvcambulapp.model.dao.person.IDaoPerson;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.Person;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private IDaoPerson daoPerson;

    @GetMapping("/")
    public String listAll(Model model,Authentication auth ){
        if (auth.getAuthorities().toString().contains("ROLE_ADMIN")) {
            List<Person> persons = daoPerson.listAll();
            System.out.println("list " + persons.size());
            model.addAttribute("persons", persons);
            model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));

            return "person/person-list";
        }
        return "index";
    }

    @GetMapping("/add/")
    public String getFormAddPerson(Model model,Authentication auth){
        if (auth.getAuthorities().toString().contains("ROLE_ADMIN")) {
            Person person = new Person();
            System.out.println("форма Person отправлена");
            model.addAttribute("person", person);
            model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));

            return "person/person-form";
        }
        return "index";
    }

    @PostMapping("/add/")
    public String addPerson(Person person, RedirectAttributes ra){
        System.out.println("форма Person получена");
        System.out.println("person.getSurname() "+ person.getSurname());
        System.out.println("person.getBirthdate "+ person.getBirthdate());

        Person personAdd = daoPerson.save(person);
        System.out.println("person.getUser "+ person.getUser());
        ra.addFlashAttribute("goodMsg", "Житель " + personAdd + "добавлен");
        return "redirect:/person/";
    }

    @GetMapping("/update/{id}")
    public String getFormUpdatePerson(@PathVariable("id") Integer personId, Model model){
        Optional<Person> person = daoPerson.getById(personId);
        if (person.isPresent()) {
            System.out.println("форма отправлена");
            model.addAttribute("person", person.get());
        }
        return "person/person-update";
    }

    @PostMapping("/update/")
    public String updatePerson(Person person, RedirectAttributes ra){
        System.out.println("форма получена");
        Person personUpd = daoPerson.update(person);
        ra.addFlashAttribute("goodMsg", "Данные о жителе " + personUpd + " обновлены");
        return "redirect:/person/";
    }
    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable ("id") Integer personId, @RequestParam String back, Model model){
        Optional<Person> person = daoPerson.getById(personId);
        if (person.isPresent()) {
            model.addAttribute("person", person.get());
            model.addAttribute("back", back);
        }
        return "person/person-detail";
    }
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable ("id") Integer personId){
        daoPerson.delete(personId);
        return "redirect:/person/";
    }
}
