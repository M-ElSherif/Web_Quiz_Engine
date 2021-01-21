package engine;

import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class APIController {

    private QuizService quizService;

    @Autowired
    public APIController(QuizService quizService) {
        this.quizService = quizService;
    }

//    @GetMapping(value = "api/quiz", produces = "application/json")
//    public ResponseEntity<String> retrieveQuiz() {
//        JSONObject quiz = new JSONObject();
//        quiz.put("title", "The Java Logo");
//        quiz.put("text", "What is depicted on the Java logo?");
//        String[] arr = {"Robot", "Tea leaf", "Cup of coffee", "Bug"};
//        quiz.put("options", arr);
//
//        return ResponseEntity.ok()
//                .body(quiz.toString());
//    }
//
//    @PostMapping(value = "/api/quiz", produces = "application/json")
//    public ResponseEntity<String> submitQuizAnswer(@RequestParam int answer) {
//        JSONObject firstPassed = new JSONObject();
//        JSONObject secondPassed = new JSONObject();
//        firstPassed.put("success", true);
//        firstPassed.put("feedback", "Congratulations, you're right!");
//        secondPassed.put("success", false);
//        secondPassed.put("feedback","Wrong answer! Please, try again.");
//
//        String responseString = "";
//        if (answer == 2) {
//            responseString = firstPassed.toString();
//        }else if (answer == 1) {
//            responseString = secondPassed.toString();
//        }
//        return ResponseEntity.ok()
//                .body(responseString);
//    }

    @PostMapping(value = "/api/quizzes", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz) {
        this.quizService.addQuiz(quiz);

        return ResponseEntity.ok()
                .body(quiz);

    }

    @GetMapping(value = "/api/quizzes/{id}", produces = "application/json")
    public ResponseEntity<Quiz> retrieveQuizById(@PathVariable int id) {
        Optional<Quiz> quiz = Optional.ofNullable(this.quizService.getQuizById(id));

        if (quiz.isPresent()) {
            return ResponseEntity.ok()
                    .body(quiz.get());
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "/api/quizzes", produces = "application/json")
    public ResponseEntity<Collection<Quiz>> retrieveAllQuizzes() {
        Collection<Quiz> nullQuizzes = new ArrayList<>();
        Optional<Collection<Quiz>> quizzes = Optional.ofNullable(this.quizService.getQuizzes());
        if (quizzes.isPresent()) {
            return ResponseEntity.ok()
                    .body(quizzes.get());
        } else {
            return ResponseEntity.ok()
                    .body(nullQuizzes);
        }
    }

    @PostMapping(value = "/api/quizzes/{id}/solve")
    public ResponseEntity<String> submitQuizAnswer(@PathVariable int id, @RequestParam int answer) {
        JSONObject firstPassed = new JSONObject();
        JSONObject secondPassed = new JSONObject();
        firstPassed.put("success", true);
        firstPassed.put("feedback", "Congratulations, you're right!");
        secondPassed.put("success", false);
        secondPassed.put("feedback", "Wrong answer! Please, try again.");

        Optional<Quiz> quiz = Optional.ofNullable(this.quizService.getQuizById(id));

        if (quiz.isPresent()) {
            int correctAnswer = quiz.get().getAnswer();

            String responseString = "";
            if (answer == correctAnswer) {
                responseString = firstPassed.toString();
            } else {
                responseString = secondPassed.toString();
            }
            return ResponseEntity.ok()
                    .body(responseString);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }
}

