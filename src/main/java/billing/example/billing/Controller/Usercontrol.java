package billing.example.billing.Controller;

import billing.example.billing.Repository.UserRepo;
import billing.example.billing.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class Usercontrol
{

    @Autowired
    private UserRepo repo;

    // GET ALL USERS --> /user
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = repo.findAll();
        return ResponseEntity.ok(users);
    }

    // GET USER BY ID --> /user/10
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE USER --> /user
    @PostMapping
    public ResponseEntity<User> newUser(@RequestBody User newUser) {
        User saved = repo.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // UPDATE USER --> /user/10
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser, @PathVariable Long id) {
        return repo.findById(id)
                .map(user -> {

                    user.setUsername(updatedUser.getUsername());
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    User saved = repo.save(user);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE USER --> /user/10
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
