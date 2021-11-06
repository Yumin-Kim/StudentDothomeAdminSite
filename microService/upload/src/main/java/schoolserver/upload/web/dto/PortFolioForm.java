package schoolserver.upload.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class PortFolioForm {
    private String speechLink;
    private String youtubeLink;
    private String description;
    private MultipartFile profile;
    private MultipartFile backgroundImage;
    private MultipartFile designLeft;
    private MultipartFile designRight;
    private MultipartFile descFir;
    private MultipartFile descSec;
    private MultipartFile descThr;
    private MultipartFile descFo;
    private MultipartFile mainWeb;
    private MultipartFile mainApp;

}
