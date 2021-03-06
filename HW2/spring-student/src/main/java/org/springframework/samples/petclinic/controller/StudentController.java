/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.controller;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Student;
import org.springframework.samples.petclinic.repository.StudentRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
//new
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
class StudentController {

	// private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
	private final StudentRepository studentsRepo;

	public StudentController(StudentRepository service){
		studentsRepo=service;
	}

	@GetMapping("/index")
	public String index() {
		System.out.println("index");
		return "students/index";
	}

	@GetMapping("/insert")
	public String insert() {
		System.out.println("get insert");
		return "students/insert";
	}

	@PostMapping("/insert")
	public String insertHandler(Model model, @ModelAttribute("student") Student student){

		System.out.println("post insert:"+student.toStr());
		studentsRepo.save(student);
		return "students/insert";
	}

	@GetMapping("/query")
	public String query() {
		System.out.println("get query");
		return "students/query";
	}

	@PostMapping("/query")//
	public String queryHandler(Model model,@RequestParam(name="id",required = false,defaultValue = "-1") int id,
	@RequestParam(name="name",required = false) String name) {
		System.out.println("post query:"+id+' '+name.length());
		Collection<Student> results;		
		
		if(id>=0){
			System.out.println("id");
			results = studentsRepo.findById(id);
		}
		else if(!name.isEmpty()){
			System.out.println("name");
			results=studentsRepo.findByName(name);
		}
		else{
			System.out.println("all");
			results=studentsRepo.findAll();
		}
		System.out.println("size:"+results.size());
		model.addAttribute("results", results);
		
		return "students/query";
	}

	@GetMapping("/delete")
	public String delete() {
		System.out.println("get delete");
		return "students/delete";
	}

	@PostMapping("/delete")
	// public String deleteHandler(@RequestParam(name="id",required = false,defaultValue = "-1") int id,
	// @RequestParam(name="name",required = false) String name) {
	public String deleteHandler(@RequestParam(name="id",required = false,defaultValue = "-1") int id) {
		System.out.println("post delete");
		studentsRepo.deleteById(id);
		return "students/delete";
	}

	@GetMapping("/update")
	public String update() {
		System.out.println("get update");
		return "students/update";
	}

	@PostMapping("/update")
	public String deleteHandler(@RequestParam(name="name",required = false) String name,
	@RequestParam(name="id",required = false,defaultValue = "-1") int id) {
	// public String updateHandler(@RequestParam(name="id",required = false,defaultValue = "-1") int id) {
		System.out.println("post update");
		studentsRepo.setNameById(name,id);
		return "students/update";
	}

}
