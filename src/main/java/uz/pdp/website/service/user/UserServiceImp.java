package uz.pdp.website.service.user;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.*;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.website.dto.request.AuthDto;
import uz.pdp.website.dto.request.UserRequestDto;
import uz.pdp.website.entity.UserEntity;
import uz.pdp.website.entity.enums.Role;
import uz.pdp.website.exception.AlreadyExistsException;
import uz.pdp.website.exception.DataNotFoundException;
import uz.pdp.website.repository.UserRepository;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }



    @Override
    public UserEntity create(UserRequestDto userRequestDto) {
        UserEntity userEntity = modelMapper.map(userRequestDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity.setUserRoles(List.of(Role.USER));
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity getbyId(UUID id) {
            if(userRepository.findById(id).isPresent()){
                return userRepository.findById(id).get();
            }
            else {
                throw new DataNotFoundException("user not found");
            }
    }

    @Override
    public void deletebyId(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserEntity login(AuthDto auth) {
        UserEntity userEntity = userRepository.findByUsername(auth.getUsername())
                .orElseThrow(() -> new DataNotFoundException("user or password incorrect"));
        if (passwordEncoder.matches(auth.getPassword(), userEntity.getPassword())) {
            return userEntity;
        }
        throw new DataNotFoundException("user or password incorrect");
    }



    @Override
    public void update(UserRequestDto dto) {
        UserEntity map = modelMapper.map(dto, UserEntity.class);
        userRepository.save(map);
    }

    public void update(String username, String password, UUID id){
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("user not found"));
        try {
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }
        catch (Exception e){
        throw new DataNotFoundException("user does not exist");
        }
    }

    @Override
    public void updateUserInfo(String address, String direction,String password, UUID id, String  jshshir,
                               String placeofBirth, String dateofBirth, String nationality) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new DataNotFoundException("user not found"));
        try {
            user.setAddress(address);
            user.setDirection(direction);
            user.setPassword(passwordEncoder.encode(password));
            user.setJshshir(jshshir);
            user.setDateOfBirth(dateofBirth);
            user.setPlaceOfBirth(placeofBirth);
            user.setNationality(nationality);
            userRepository.save(user);
        }
        catch (Exception e){
            throw new DataNotFoundException("user doest not exist");
        }

    }

    @Override
    public UserEntity myInfo(UUID id) {
        for (UserEntity user: userRepository.findAll()){
        if(user!=null){
            if(user.getId().equals(id)){
                return UserEntity.builder().build();
            }
        }
        }
        throw new DataNotFoundException("user not found");
    }

    @Override
    public void getMyInfoWithWord(String fileName, UserEntity userEntity) {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph paragraph = document.createParagraph();
        XWPFParagraph paragraph1 = document.createParagraph();
        XWPFParagraph paragraph2 = document.createParagraph();
        XWPFParagraph paragraph3 = document.createParagraph();
        XWPFParagraph paragraph4 = document.createParagraph();
        XWPFParagraph paragraph5 = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        XWPFRun run1 = paragraph.createRun();
        run.setText("MA'LUMOTNOMA");
        run.setFontSize(28);
        run.setBold(true);
        run.setFontFamily("Times New Roman");
        run.addBreak();
        run1.setText(userEntity.getName());
        run1.setFontSize(25);
        run1.setFontFamily("Times New Roman");
        run1.addBreak();
        run1.addBreak();
        run1.addBreak();
        paragraph.setAlignment(ParagraphAlignment.CENTER);// -> Ma'lumotnoma uchun
        XWPFRun run2 = paragraph1.createRun();
        XWPFRun run3 = paragraph1.createRun();
        run2.setText("JSHSHIR:");
        run2.setFontSize(15);
        run2.setBold(true);
        run2.addBreak();
        run3.setText(userEntity.getJshshir());
        run3.setFontSize(13);
        paragraph1.setAlignment(ParagraphAlignment.LEFT); // -> Jshshir uchun

        XWPFRun run4 = paragraph2.createRun();
        XWPFRun run5 = paragraph2.createRun();
        run4.setText("Tug'ilgan yili:");
        run4.setFontSize(15);
        run4.setBold(true);
        run4.addBreak();
        run5.setText(userEntity.getDateOfBirth());
        run5.setFontSize(13);
        paragraph2.setAlignment(ParagraphAlignment.LEFT); // -> tug'ilgan yili uchun

        XWPFRun run6 = paragraph3.createRun();
        XWPFRun run7 = paragraph3.createRun();
        run6.setText("Millati:");
        run6.setFontSize(15);
        run6.setBold(true);
        run6.addBreak();
        run7.setText(userEntity.getNationality());
        run7.setFontSize(13);
        paragraph3.setAlignment(ParagraphAlignment.LEFT); // -> millati uchun

        XWPFRun run8 = paragraph4.createRun();
        XWPFRun run9 = paragraph4.createRun();
        run8.setText("Tug'ilgan joyi:");
        run8.setFontSize(15);
        run8.setBold(true);
        run8.addBreak();
        run9.setText(userEntity.getPlaceOfBirth());
        run9.setFontSize(13);
        paragraph4.setAlignment(ParagraphAlignment.RIGHT); // -> tug'ilgan joyi uchun

        XWPFRun run10 = paragraph5.createRun();
        XWPFRun run11 = paragraph5.createRun();
        run10.setText("Yo'nalishi:");
        run10.setFontSize(15);
        run10.setBold(true);
        run10.addBreak();
        run11.setText(userEntity.getDirection());
        run11.setFontSize(13);
        paragraph5.setAlignment(ParagraphAlignment.END); // -> yo'nalishi uchun

        try  {
            String filePath="src\\main\\resources\\static\\"+ fileName +".docx";
            FileOutputStream out=new FileOutputStream(filePath);
            document.write(out);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserEntity> getAllUsersExceptCurrent(UserEntity currentUser) {
        List<UserEntity> allUsers = userRepository.findAll();
        allUsers.remove(currentUser);
        return allUsers;

    }


}
