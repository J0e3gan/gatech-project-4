package gatech.course.optimizer.service;

import gatech.course.optimizer.model.Student;
import gatech.course.optimizer.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by 204069126 on 4/9/15.
 */
@RestController
public class StudentService {

    @Autowired
    private StudentRepo studentRepo;

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Student> getAllStudents() {

        return studentRepo.getAllStudents();
    }


}



/*


@RequestMapping(value = "/", method = RequestMethod.GET)
    public
    @ResponseBody
    String index();

    @RequestMapping(value = "/file/{folder}/{filename:.+}", method = RequestMethod.GET)
    public
    @ResponseBody
    FileSystemResource getFile(@PathVariable("folder") String folder,
                               @PathVariable("filename") String filename);

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public
    @ResponseBody
    SearchResult find(@RequestHeader(value = VCAPCredentials.SERVICE_INSTANCE_ID) String instanceId,
                      @RequestParam(value = "searchKey", required = true) String searchKey);

    @RequestMapping(value = "/store", method = RequestMethod.POST)
    public
    @ResponseBody
    ContentInfo store(@RequestHeader(value = VCAPCredentials.SERVICE_INSTANCE_ID) String instanceId,
                      @RequestParam("name") String name,
                      @RequestParam(value = "description") String description,
                      @RequestParam(value = "uploader", required = false) String uploader,
                      @RequestParam(value = "assetType", required = false) String assetType,
                      @RequestParam(value = "assetId", required = false) String assetId,
                      @RequestParam(value = "tag", required = false) List<String> tags,
                      @RequestParam("file") MultipartFile[] files);


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public
    @ResponseBody
    ContentInfo update(@RequestHeader(value = VCAPCredentials.SERVICE_INSTANCE_ID) String instanceId,
                       @RequestBody ContentInfo contentInfo);

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void delete(@RequestHeader(value = VCAPCredentials.SERVICE_INSTANCE_ID) String instanceId,
                       @PathVariable("id") String id);


 */