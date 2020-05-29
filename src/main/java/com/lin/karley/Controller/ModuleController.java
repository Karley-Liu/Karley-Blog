package com.lin.karley.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ModuleController {

    @GetMapping("back_notes")
    public String back_notes(){return "module/back_notes";}

    @GetMapping("security")
    public String security(){return "module/security";}

    @GetMapping("releasenote")
    public String releaseNote(){return "module/releaseNote";}

    @GetMapping("contactMe")
    public String contactMe(){return "module/contactMe";}

//    @GetMapping("contactCus")
//    public String contactCus(){return "module/contactCus";}
}
