package com.example.developercollaboration.Service;

import com.example.developercollaboration.Model.Skill;
import com.example.developercollaboration.Repositories.SkillRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}