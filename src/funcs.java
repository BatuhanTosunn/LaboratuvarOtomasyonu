import java.util.ArrayList;

public interface funcs {
    void validateDay(int a) throws ValueError;
    void validateMonth(int b) throws ValueError;
    void validateYear(int c) throws ValueError;
    void validateQuantity(int d) throws ValueError;
    void validatename(String e, ArrayList<String[]> list) throws ValueError;
}
