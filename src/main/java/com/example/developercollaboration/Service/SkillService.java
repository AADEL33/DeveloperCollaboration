package com.example.developercollaboration.Service;
import com.example.developercollaboration.Exceptions.SkillExceptions.SkillAlreadyExistException;
import com.example.developercollaboration.Exceptions.SkillExceptions.SkillNotFoundException;
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

    public Skill addSkill(Skill skill) {
        if(skillRepository.existsById(String.valueOf(skill))){
            throw new SkillAlreadyExistException();
        }
       return skillRepository.save(skill);
    }
    public void removeSkill(String SkillName){
        if(!skillRepository.existsById(SkillName)){
            throw new SkillNotFoundException();
        }
        else{
             skillRepository.deleteById(SkillName);
        }
    }
    public List<Skill> getSkills(){
         return skillRepository.findAll();
    }

    public Optional<Skill> getSkillbyName(String name){
        if(!skillRepository.existsById(name)){
            throw new SkillNotFoundException();
        }else{
            return skillRepository.findById(name);
        }


    }

}