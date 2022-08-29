package com.example.developercollaboration.Controller;

import com.example.developercollaboration.Model.Skill;
import com.example.developercollaboration.Service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/skill")
@RequiredArgsConstructor
@Transactional
public class SkillController {
   private final SkillService skillService;
    @PostMapping(path="/add")
    public Skill addSkill(@RequestBody Skill skill) throws Exception {
        return skillService.addSkill(skill);
    }
    @DeleteMapping(path="/delete")
    public void deleteSkill(@RequestParam String skillName ) throws Exception {
        skillService.removeSkill(skillName);
    }
}