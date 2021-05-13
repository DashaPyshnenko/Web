package com.example.springwebapp.controller;

import com.example.springwebapp.model.Auto;
import com.example.springwebapp.model.Checkout;

import com.example.springwebapp.repos.CheckRepos;
import com.example.springwebapp.repos.AutoRepos;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Denis on 2/20/2016.
 */

@Controller
public class Home {

    @Autowired //создание переменной, ссылающейся на репозиторий типа интерфейс
    private AutoRepos autoRepos;
    @Autowired
    private CheckRepos checkRepos;

    private  int GetCost(List<Auto> products){
        Integer totalSum=0;
        for (int i=0;i<products.size();i++){
            Auto product=products.get(i);
            totalSum=totalSum+product.getCost();
        }
        return totalSum;
    }

    private  int GetCount(List<Auto> products, Long id){
        Integer Count=0;
        for (int i=0;i<products.size();i++){
            Auto product=products.get(i);
            if (product.getId() == id)
                Count++;
        }
        return Count;
    }

    /*public static ArrayList<Auto> products = new ArrayList<>();*/
    /*List<Auto> products;*/
    List<Auto> flo;

    @RequestMapping("/")
    public String home(Model modell, HttpSession session) {
        List<Auto> autos = (List<Auto>) session.getAttribute(session.getId());
        session.setAttribute(session.getId(), autos);
        modell.addAttribute("products", autos);
        modell.addAttribute("totalCost", GetCost(autos));
        return "checkout";
    }

    @RequestMapping("/home-with-session")
    public String homeWithSession(Model model, HttpSession session, HttpServletRequest request) {
        String sid = session.getId();
        model.addAttribute("sid", sid);
        var cookies = request.getCookies();
        var filters_City = Arrays.stream(cookies)
                .filter(s -> "City".equals(s.getName()) && s.getValue() != null)
                .collect(Collectors.toList());
        List<Auto> c = null;
        flo = (List<Auto>) autoRepos.findAll();
        flo.clear();

        if (flo.size() == 0) {
            AddElement();
        }
        flo = (List<Auto>) autoRepos.findAll();
        /*flo.remove(10);
        flo.remove(9);
        flo.remove(8);
        flo.remove(7);
        flo.remove(6);*/

        if (filters_City.size() != 0) {
            c = flo.stream()
                    .filter(s -> filters_City.get(0).getValue().equals(s.getCity()))
                    .collect(Collectors.toList());
            model.addAttribute("products", c);
        } else {
            model.addAttribute("products", flo);
        }

        return "index";
    }

   private void AddElement() {
        /*products = new ArrayList<>();*/

        autoRepos.save(new Auto("TOYOTA C-HR", "От 11 168 Р/мес.", 2079000, "Toyota", "https://pbs.twimg.com/media/DiN_fJ5W4AYrrk_.jpg", "Moscow", Long.valueOf(1)));
       autoRepos.save(new Auto("LEXUS RX", "Специальная серия Black Vision.", 3980000, "Lexus", "https://pbs.twimg.com/media/D_1tBQtW4AEWMVD.jpg","Moscow", Long.valueOf(2) /*products.size()*/));
       autoRepos.save(new Auto("LEXUS ES", "Ограниченная серия.", 3001500, "Lexus", "https://pbs.twimg.com/media/D6RicfEXsAAMQEk.jpg","Ekaterinburg", Long.valueOf(3) /*products.size()*/));
       autoRepos.save(new Auto("TOYOTA FORTUNER", "От 32 748 Р/мес.", 2706000, "Toyota", "https://pbs.twimg.com/media/EZrgwzWXYAAidy7.jpg","Ekaterinburg", Long.valueOf(4) /*products.size()*/));
       autoRepos.save(new Auto("S-Класс Maybach", "От 95 736 Р/мес.", 7999999, "Mercedes", "https://pbs.twimg.com/media/EEqjAXSW4AAcHNv.jpg","Kazan", Long.valueOf(5) /*products.size()*/));
       autoRepos.save(new Auto("Mercedes-Benz C-Класс", "Автомобиль продается в 5 комплектациях", 3040000, "Mercedes", "https://media.caradvice.com.au/image/private/c_fill,q_auto,f_auto,w_1200,h_1200/d83cbdb335377ee1aa93a8293a4463ff.jpg","Kazan", Long.valueOf(6) /*products.size()*/));
    }

    @PostMapping(value = "/sortUser")
    public String sortBy(@RequestParam String sortP, Model model, HttpServletRequest request) {
        var cookies = request.getCookies();
        var filters_City = Arrays.stream(cookies)
                .filter(s -> "City".equals(s.getName()) && s.getValue() != null)
                .collect(Collectors.toList());
        List<Auto> c = null;
        List<Auto> flo = (List<Auto>) autoRepos.findAll();

        if (sortP.contains("price")) {
            if (filters_City.size() == 0) {
                c = flo.stream()
                        .sorted(Comparator.comparing(Auto::getCost))
                        .collect(Collectors.toList());
            }
            else {
                c = flo.stream()
                        .sorted(Comparator.comparing(Auto::getCost))
                        .filter(s -> filters_City.get(0).getValue().equals(s.getCity()))
                        .collect(Collectors.toList());
            }
        } else {
            if (filters_City.size() == 0) {
                c = flo.stream()
                        .sorted(Comparator.comparing(Auto::getName))
                        .collect(Collectors.toList());
            }
            else{
                c = flo.stream()
                        .sorted(Comparator.comparing(Auto::getName))
                        .filter(s -> filters_City.get(0).getValue().equals(s.getCity()))
                        .collect(Collectors.toList());
            }
        }
        model.addAttribute("products", c);
        return "sorti";
    }

    @PostMapping(value = "/orderBy")
    public String filtrBy(@RequestParam String filtrP, Model model, HttpServletRequest request) {
        var cookies = request.getCookies();
        var filters_City = Arrays.stream(cookies)
                .filter(s -> "City".equals(s.getName()) && s.getValue() != null)
                .collect(Collectors.toList());
        List<Auto> c = null;
        List<Auto> flo = (List<Auto>) autoRepos.findAll();

        if (filters_City.size() == 0) {
            c = flo.stream()
                    .filter(s -> filtrP.equals(s.getCategory()))
                    .collect(Collectors.toList());
        }
        else{
            c = flo.stream()
                    .filter(s -> filtrP.equals(s.getCategory()))
                    .filter(s->filters_City.get(0).getValue().equals(s.getCity()))
                    .collect(Collectors.toList());
        }
        model.addAttribute("products", c);
        return "sorti";
    }

    @PostMapping(value = "/add")
    public ResponseEntity addToCart(@RequestBody String form, HttpSession session) {
        var prodId = form.substring(form.indexOf(':') + 1);
        prodId = prodId.substring(prodId.indexOf('"') + 1, prodId.lastIndexOf('"'));
        var i = Integer.parseInt(prodId);
        List<Auto> flo = (List<Auto>) autoRepos.findAll();
        List<Auto> products1 = (List<Auto>) session.getAttribute(session.getId());
        if (products1 == null) {
            products1 = new ArrayList<>();
        }

        products1.addAll(flo.stream()
                .filter(s -> i == s.getId())
                .collect(Collectors.toList()));

        session.setAttribute(session.getId(), products1);
        ObjectMapper objectMapper = new ObjectMapper();

        return new ResponseEntity(HttpStatus.OK);

    }

    @GetMapping(value = "/getCount")
    public @ResponseBody
    String getCounter(HttpSession session) {
        List<Auto> products = (List<Auto>) session.getAttribute(session.getId());
        Integer count = 0;
        if (products == null) {
            count = 1;
        } else {
            count = products.size();
        }
        String str = count.toString();
        return str;
    }

    @RequestMapping(value = "/deletProduct")
    public String deletProduct(@RequestParam String idProduct, Model model, HttpSession session) {
        List<Auto> autos = (List<Auto>) session.getAttribute(session.getId());
        Long id=Long.parseLong(idProduct);
        for(int i = 0; i< autos.size(); i++){
            Auto product= autos.get(i);
            if(product.getId()==id) {
                autos.remove(product);
                /*autos.remove(product.getId());*/
                break;
            }
        }
        session.setAttribute(session.getId(), autos);
        model.addAttribute("totalCost", GetCost(autos));
        model.addAttribute("products", autos);
        return "checkres";
    }

    @RequestMapping(value = "/addProduct")
    public String addProduct(@RequestParam String idProduct, Model model, HttpSession session) {
        List<Auto> autos = (List<Auto>) session.getAttribute(session.getId());
        Long id=Long.parseLong(idProduct);
        /*Auto prod = autos.get(id.intValue());*/
        /*autos.add(prod);*/
        List<Auto> flo = (List<Auto>) autoRepos.findAll();
        autos.addAll(flo.stream()
                .filter(s -> id == s.getId())
                .collect(Collectors.toList()));
        session.setAttribute(session.getId(), autos);
        model.addAttribute("products", autos);
        model.addAttribute("totalCost", GetCost(autos));
        return "checkres";
    }

    @RequestMapping(value = "/cellProduct")
    public String cellProductSession(Model model, HttpSession session,HttpServletRequest request) {
        var cookies = request.getCookies();
        var filters_City = Arrays.stream(cookies)
                .filter(s -> "City".equals(s.getName()) && s.getValue() != null)
                .collect(Collectors.toList());
        List<Auto> products = (List<Auto>) session.getAttribute(session.getId());
        List<Long> isp = new ArrayList<>();
        Date date=new Date();
        for(int i=0;i<products.size();i++){
            Auto product=products.get(i);

            if (!isp.contains(product.getId()))
            {
            Checkout checkout = new Checkout(filters_City.get(0).getValue(), date, product.getId(), GetCount(products,product.getId()));
            checkRepos.save(checkout);}
            isp.add(product.getId());
        }
        products.clear();

        session.setAttribute(session.getId(), products);
        model.addAttribute("products", products);
        model.addAttribute("totalCost", 0);
        return "checkres";
    }


    /*@RequestMapping("/")
    public String home() {
        return "index";
    }

    @RequestMapping("/home-with-session")
    public String homeWithSession(Model model, HttpSession session) {
        String sid = session.getId();
        model.addAttribute("sid", sid);

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Old product", 1, new Date()));
        products.add(new Product("New product", 2, new Date()));
        model.addAttribute("products", products);
        return "index";
    }

    @RequestMapping("/home")
    public String checkout(Model model, HttpSession session) {
        String sid = session.getId();
        model.addAttribute("sid", sid);

        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Old product", 1, new Date()));
        products.add(new Product("New product", 2, new Date()));
        model.addAttribute("products", products);
        return "checkout";
    }*/
}
