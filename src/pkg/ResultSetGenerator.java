package pkg;

import java.util.ArrayList;
import java.util.List;

public class ResultSetGenerator {

    public List<String> generate(){
        int limit = 100;
        List<String> result = new ArrayList<>(limit);

        for (int i = 0; i < limit; i++) {
            result.add(Integer.toString(i));
        }
        return  result;
    }
}
