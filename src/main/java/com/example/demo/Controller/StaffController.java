package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.Entity.Staff;
import com.example.demo.Service.StaffService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
@Controller
public class StaffController {
	  private final StaffService staffService;
	  public StaffController(StaffService staffService) {
	         this.staffService = staffService;
	   }
	  
	  @GetMapping("/staff")    
	  public String showProductSeller(Model model, @RequestParam(defaultValue = "0") int page) {
	         Page<Staff> staffPage = staffService.findAll(PageRequest.of(page, 10));
	         model.addAttribute("staffs", staffPage.getContent());
	         model.addAttribute("currentPage", staffPage.getNumber());
	         model.addAttribute("totalPages", staffPage.getTotalPages());
	         return "manager/staffList";       
	     }
	   
	  @GetMapping("/staff/create-new-staff")
	    public String createNewStaff(Model model) {
	        model.addAttribute("staff", new Staff());
	        return "manager/createNewStaff";
	    }
	  
	    @PostMapping("/staff/create-new-staff/save")
	    public String saveNewStaff(@ModelAttribute Staff staff) {
	        staffService.save(staff);
	        return "manager/createNewStaff";
	    }
	  
	   @GetMapping("staff/edit-staff-profile")
	   public String EditStaffProfile() {
		   return"manager/editStaffProfile";
	   }

}
