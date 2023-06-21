package Goorm.Introduce.Domain.Board;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

//test용도
@Repository
public class BoardRepository {
    private static Map<Integer, Board> data = new HashMap<>();
    private static int sequence = 0;

    public void save(Board board) {
        board.setId(++sequence);
        data.put(board.getId(), board);
    }

    public Board findById(int id) {
        return data.get(id);
    }
}
