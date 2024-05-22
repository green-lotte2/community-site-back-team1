package kr.co.zeroPie.service;


import kr.co.zeroPie.dto.StfDTO;
import kr.co.zeroPie.entity.Stf;
import kr.co.zeroPie.repository.StfRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class StfService {

    //private final PasswordEncoder passwordEncoder;
    private final StfRepository stfRepository;
    private final ModelMapper modelMapper;


    public String register(StfDTO stfDTO) {//암호화 하지않고 저장
        //String encoded = passwordEncoder.encode(stfDTO.getStfPass());
        //stfDTO.setStfPass(encoded);

        String id = null;

        switch (stfDTO.getDptNo())//아이디를 여기서 만들어서 넣음
        {
            case 1 :  id = "SD"+randomNumber();
                stfDTO.setStfNo(id);
                break;
            case 2 :  id = "IT"+randomNumber();
                stfDTO.setStfNo(id);
                break;
            case 3 :  id = "PD"+randomNumber();
                stfDTO.setStfNo(id);
                break;
            case 4 :  id = "MD"+randomNumber();
                stfDTO.setStfNo(id);
                break;
            case 5 :  id = "PRD"+randomNumber();
                stfDTO.setStfNo(id);
                break;
            case 6 :  id = "RD"+randomNumber();
                stfDTO.setStfNo(id);
                break;
            default:
                stfDTO.setStfNo(null);
                break;
        }

        Stf stf = modelMapper.map(stfDTO, Stf.class);

        log.info("stf : " + stf);

        Stf savedUser = stfRepository.save(stf);

        log.info("save 이후 : " + savedUser.toString());

        return savedUser.getStfNo();
    }


    public String randomNumber(){
        int randomNumber = (int)(Math.random()*900)+100;
        return Integer.toString(randomNumber);

    }
}
