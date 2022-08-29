package com.example.developercollaboration.Service;

import com.example.developercollaboration.Model.Skill;
import com.example.developercollaboration.Repositories.SkillRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Data
public class SkillService {
    private final SkillRepository skillRepository;
    public Skill addSkill(Skill skill)throws Exception{
        if(skillRepository.existsById(String.valueOf(skill))){
            throw new Exception("skill already exists");
        }
       return skillRepository.save(skill);
    }
    public void removeSkill(String SkillName)throws Exception{
        if(!skillRepository.existsById(SkillName)){
            throw new Exception("skill does not exist");
        }
        else{
             skillRepository.deleteById(SkillName);
        }
    }
    public List<Skill> getSkills(){
         return skillRepository.findAll();
    }
    public Optional<Skill> getSkillbyName(String name)throws Exception{
        if(!skillRepository.existsById(name)){
            throw new Exception("Skill name not found");
        }else{
            return skillRepository.findById(name);
        }

    }
}