package Project.Investment.webcontroller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Project.Investment.domain.SignupForm;
import Project.Investment.domain.User;
import Project.Investment.domain.UserRepository;




@Controller
public class UserController {
	
	@Autowired UserRepository repository;
	
	
	@RequestMapping(value="/userlist")
	public String userlist(Model model) {
		model.addAttribute("users", repository.findAll());
		return "userlist";
	}
	
	
	
	@RequestMapping(value="/deleteuser/{id}", method=RequestMethod.GET)
	public String deleteuser(@PathVariable("id") Long userId, Model model) {
		repository.deleteById(userId);
		return "redirect:../userlist";
		
	}
	
	
	
	@RequestMapping(value="signup")
	public String addUser(Model model) {
		model.addAttribute("signupform", new SignupForm());
		return "signup";
	}
	
	
	
	
	
	
	@RequestMapping(value = "saveuser", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
    	if (!bindingResult.hasErrors()) { // validation errors
    		if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) { // check password match		
	    		String pwd = signupForm.getPassword();
		    	BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		    	String hashPwd = bc.encode(pwd);
	
		    	User newUser = new User();
		    	newUser.setPasswordHash(hashPwd);
		    	newUser.setUsername(signupForm.getUsername());
		    	newUser.setEmail(signupForm.getEmail()); //<--käyttäjän sähköposti
		    	newUser.setPhone(signupForm.getPhone()); //<--Käyttäjän puhelin-nro
		    	newUser.setRole("USER");
		    	if (repository.findByUsername(signupForm.getUsername()) == null) { // Check if user exists
		    		repository.save(newUser);
		    	}
		    	else {
	    			bindingResult.rejectValue("username", "err.username", "Username already exists");    	
	    			return "signup";		    		
		    	}
    		}
    		else {
    			bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");    	
    			return "signup";
    		}
    	}
    	else {
    		return "signup";
    	}
    	return "redirect:/login";    	
    }    
   



}
