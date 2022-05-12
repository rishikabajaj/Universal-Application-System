package csci5308.fall21.appHub.controller.university;

import csci5308.fall21.appHub.model.university.Announcement;
import csci5308.fall21.appHub.service.university.IAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class AnnouncementController {

    @Autowired
    IAnnouncementService announcementService;

    /**
     * This will get all the announcement from the database
     * 
     * @return all the announcements with HttpStatus
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    @GetMapping(path = "/api/announcement")
    public ResponseEntity<Object> getAnnouncement() {
        List<Announcement> allAnnouncements = announcementService.getAnnouncementsDetails();
        Map<String, String> response = new HashMap<>();
        if (allAnnouncements.size() == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Announcements not found");
        } else {
            try {
                return ResponseEntity.status(HttpStatus.OK).body(allAnnouncements);
            } catch (Exception ex) {
                response.put("message", "Error while fetching All announcements");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
    }

    /**
     * This will get the announcement of particular id from the database
     * 
     * @param announcementId provide announcementId
     * @return announcement will be returned
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    @GetMapping(path = "/api/announcement/{announcementId}")
    public ResponseEntity<Object> getAnnouncementById(@PathVariable(value = "announcementId") String announcementId) {
        Announcement announcement = announcementService.getAnnouncementDetailsById(announcementId);
        Map<String, String> response = new HashMap<>();
        if (announcement == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Announcement not found for announcementId: " + announcementId);
        } else {
            try {
                return ResponseEntity.status(HttpStatus.OK).body(announcement);
            } catch (Exception ex) {
                response.put("message", "Error while fetching announcement for particular announcementId");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        }
    }

    /**
     * This will store the announcement in the database
     * 
     * @param announcement pass the announcement object body
     * @return HttpStatus will be returned
     * @throws SQLException
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    @PostMapping(path = "/api/announcement")
    public ResponseEntity<Object> postAnnouncement(@RequestBody Announcement announcement) throws SQLException {
        Map<String, String> response = new HashMap<>();
        announcement.setId();
        HttpStatus status = announcementService.addAnnouncementDetails(announcement);

        if (status == HttpStatus.OK) {
            response.put("message", "Announcement added Successfully");
        } else {
            response.put("message", "Error while processing");
        }
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Delete announcement by particular Id
     * 
     * @param announcementId provide announcementId
     * @return HttpStatus will be returned
     * @throws SQLException
     *
     * @author Purvilkumar Sanjaysinh Bharthania
     */
    @DeleteMapping(path = "/api/announcement/{announcementId}")
    public ResponseEntity<Object> deleteAnnouncementById(@PathVariable(value = "announcementId") String announcementId)
            throws SQLException {
        Map<String, String> response = new HashMap<>();
        HttpStatus status = announcementService.deleteAnnouncementDetailsById(announcementId);

        if (status == HttpStatus.OK) {
            response.put("message", "Announcement deleted Successfully");
            return ResponseEntity.status(status).body(response);
        } else if (status == HttpStatus.BAD_REQUEST) {
            response.put("message", "No announcement with this Id exist");
            return ResponseEntity.status(status).body(response);
        } else {
            response.put("message", "Error while processing");
            return ResponseEntity.status(status).body(response);
        }
    }

}
