package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class QuizService {

    private Map<Integer, Quiz> quizzes = new HashMap<>();

    public QuizService() {
    }

    public Collection<Quiz> getQuizzes() {
        return this.quizzes.values();
    }

    public boolean addQuiz(Quiz quiz) {
        int random = new Random().nextInt(1000);
        if (!this.quizzes.keySet().contains(random)) {
            quiz.setId(random);
            this.quizzes.put(quiz.getId(), quiz);
            if (this.quizzes.containsValue(quiz)) {
                return true;
            } else {
                this.addQuiz(quiz);
            }
        }
        return false;
    }

    public Quiz getQuizById(int id) {
        return this.quizzes.get(id);
    }
}
